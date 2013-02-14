package com.ventyx.bigdata.webservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Query service integration test with spring enabled etc.
 *
 */

@ContextConfiguration("classpath:spring/bigdata-service-test-context.xml")
public class QueryServiceIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    QueryService queryService;

    @Test
    public void testQueryMeterData() throws Exception {

        assertNotNull(queryService);

        //edge cases / error handling
        assertEquals(queryService.queryMeterData(null, null), "Error - Meter ID is a required parameter");
        assertEquals(queryService.queryMeterData("", ""), "Error - Meter ID is a required parameter");
        assertEquals(queryService.queryMeterData(null, ""), "Error - Meter ID is a required parameter");

        assertEquals(queryService.queryMeterData("123456", null), "Error - Timestamp is a required parameter");
        assertEquals(queryService.queryMeterData("123456", ""), "Error - Timestamp is a required parameter");

        assertEquals(queryService.queryMeterData("123456", "56789"), "Error - Timestamp is improperly formatted, please use 'yyyy-MM-dd hh:mm:ss'");
        assertEquals(queryService.queryMeterData("123456", "ANBCCDCD"), "Error - Timestamp is improperly formatted, please use 'yyyy-MM-dd hh:mm:ss'");
        assertEquals(queryService.queryMeterData("123456", "2012-11-13"), "Error - Timestamp is improperly formatted, please use 'yyyy-MM-dd hh:mm:ss'");

        //proper cases
        assertEquals(queryService.queryMeterData("123456", "2012-11-13 12:00:00"), "Meter Information Not Found");
        assertEquals(queryService.queryMeterData("1000000", "2012-11-13 12:00:00"), "2012-11-13 12:00:00,1000000,36.0,12.363625,98.57989,");
        assertEquals(queryService.queryMeterData("1999999", "2012-11-13 12:00:00"), "2012-11-13 12:00:00,1999999,53.0,94.80914,13.406491,");

    }
}
