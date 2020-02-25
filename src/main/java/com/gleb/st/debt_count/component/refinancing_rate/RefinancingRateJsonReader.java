package com.gleb.st.debt_count.component.refinancing_rate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.util.logging.Logger;

@Component
public class RefinancingRateJsonReader {

    //todo: inject from property file
    private String refinancingRateUrl = "http://www.nbrb.by/API/RefinancingRate";

    private final static Logger log = Logger.getLogger(RefinancingRateJsonReader.class.getName());

//    public RefinancingRateJsonReader(@Value("${refinancing.rate.url}") String refinancingRateUrl) {
//        this.refinancingRateUrl = refinancingRateUrl;
//    }

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
