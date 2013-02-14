package com.ventyx.bigdata.webservice.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * REST interface to access BigData information via queries
 */

@Component
@Path("/query")
public class QueryService {

    private static final Logger log = LoggerFactory.getLogger(QueryService.class);
    private static final SimpleDateFormat timeStampFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private List<List<String>> meterData = new ArrayList<List<String>>();

    @PostConstruct
    public void initialize() {

        InputStream inputStream = QueryService.class.getClassLoader().getResourceAsStream("meterdata.csv");
        if (inputStream == null) {
            log.error("Error loading sample meter data, file not found.");
        } else {
            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {

                    //2012-11-13 12:00:00,1999999,53.0,94.80914,13.406491
                    List<String> meterLineData = Arrays.asList(line.split(","));
                    meterData.add(meterLineData);

                }

            } catch (IOException ex) {
                log.error("Error loading sample meter data", ex);
            }
        }


    }

    /**
     * Queries meter data from the BigData integration
     *
     * @param meterId
     * @param timeStamp
     * @return output
     */

    @GET
    @Path("/meter/{meterid}/{timestamp}")
    @Produces("text/plain")
    public String queryMeterData(
            @PathParam("meterid") String meterId,
            @PathParam("timestamp") String timeStamp) {

        if (meterId == null || meterId.isEmpty()) {
            return "Error - Meter ID is a required parameter";
        }

        if (timeStamp == null || timeStamp.isEmpty()) {
            return "Error - Timestamp is a required parameter";
        }

        try {
            timeStampFormatter.parse(timeStamp);
        } catch (ParseException ex) {
            return "Error - Timestamp is improperly formatted, please use 'yyyy-MM-dd hh:mm:ss'";
        }

        //TODO implement real Hadoop integration component

        //loop the meter data and try and find a "record"
        for (List<String> meterLine : meterData) {
            if (meterLine.contains(meterId)) {

                String returnValue = "";
                for (String data : meterLine) {
                    returnValue += data + ",";
                }
                return returnValue;
            }
        }

        return "Meter Information Not Found";
    }

}
