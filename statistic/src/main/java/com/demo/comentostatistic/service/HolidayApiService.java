package com.demo.comentostatistic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayApiService {

    private final RestClient restClient;

    @Value("${api.public-data.service-key}")
    private String serviceKey;

    @Value("${api.public-data.url}")
    private String endPoint;

    public List<String> getHolidays(String year, String month) {
        try {
            String keyToSend = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);

            URI uri = UriComponentsBuilder.fromUriString(endPoint)
                    .queryParam("ServiceKey", keyToSend)
                    .queryParam("solYear", year)
                    .queryParam("solMonth", String.format("%02d", Integer.parseInt(month)))
                    .build(true)
                    .toUri();

            String response = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .body(String.class);

            if (response == null) return Collections.emptyList();

            List<String> holidays = parseHolidays(response);


            log.info("공휴일 API 호출 성공 - year: {}, month: {}, holidays: {}",
                    year, month, holidays.size());

            return holidays;

        } catch (Exception e) {
            log.error("공휴일 API 호출 실패 - year: {}, month: {}", year, month, e);
            return Collections.emptyList();
        }
    }

    private List<String> parseHolidays(String xmlResponse) throws Exception {
        List<String> holidays = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlResponse)));

        NodeList nodeList = doc.getElementsByTagName("locdate");
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyyMMdd");

        for (int i = 0; i < nodeList.getLength(); i++) {
            String rawDate = nodeList.item(i).getTextContent();
            LocalDate date = LocalDate.parse(rawDate, parser);
            holidays.add(date.toString());
        }
        return holidays;
    }
}