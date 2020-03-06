package com.gleb.st.debt_count.component.refinancing.rate.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gleb.st.debt_count.component.refinancing.rate.RefinancingRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.util.logging.Logger;

@Component
public class RefinancingRateJsonReader implements RefinancingRateReader {

    @Value("${refinancing.rate.url}")
    private String refinancingRateUrl;

    private final static Logger log = Logger.getLogger(RefinancingRateJsonReader.class.getName());

    @Override
    public RefinancingRate getRefinancingRareOnDate(Date date) {

        RefinancingRate rate = null;
        RefinancingRate[] refinancingRates;
        ObjectMapper mapper = new ObjectMapper();

        String targetUrl = refinancingRateUrl + "?onDate=" + date.toString();
        log.info("Url for retrieve JSON data = " + targetUrl);

        // reading JSON data from target URL
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new URL(targetUrl).openStream()))) {

            String jsonText = reader.readLine();
            // toLowercase because transmitted parameters starts from capital letter
            refinancingRates = mapper.readValue(jsonText.toLowerCase(), RefinancingRate[].class);

            rate = refinancingRates[0];
            log.info("Retrieved JSON data = " + rate);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return rate;
    }
}
