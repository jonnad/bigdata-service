package com.ventyx.bigdata.webservice.rest;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Query service unit test
 *
 */
public class QueryServiceTest {

    @Test
    public void testQueryMeterData() throws Exception {

        QueryService queryService = new QueryService();

        assertEquals(queryService.queryMeterData(null, null), "Error - Meter ID is a required parameter");
        assertEquals(queryService.queryMeterData("", ""), "Error - Meter ID is a required parameter");
        assertEquals(queryService.queryMeterData(null, ""), "Error - Meter ID is a required parameter");

        assertEquals(queryService.queryMeterData("123456", null), "Error - Timestamp is a required parameter");
        assertEquals(queryService.queryMeterData("123456", ""), "Error - Timestamp is a required parameter");

        assertEquals(queryService.queryMeterData("123456", "56789"), "Error - Timestamp is improperly formatted, please use 'yyyy-MM-dd hh:mm:ss'");
        assertEquals(queryService.queryMeterData("123456", "ANBCCDCD"), "Error - Timestamp is improperly formatted, please use 'yyyy-MM-dd hh:mm:ss'");
        assertEquals(queryService.queryMeterData("123456", "2012-11-13"), "Error - Timestamp is improperly formatted, please use 'yyyy-MM-dd hh:mm:ss'");

        assertEquals(queryService.queryMeterData("123456", "2012-11-13 12:00:00"), "Meter Information Not Found");

    }
}
