package com.ventyx.bigdata.webservice.rest;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Query service integration test with spring enabled etc.
 *
 */

@ContextConfiguration("classpath:spring/bigdata-service-test-context.xml")
public class UploadServiceIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    UploadService uploadService;

    @Test
    public void testUploadFile() throws Exception {

        assertNotNull(uploadService);

        //edge cases / error handling
        Response response = uploadService.uploadFile(null);
        assertEquals(response.getEntity().toString(), "No file was attached, please upload a file");

        response = uploadService.uploadFile(new ArrayList<Attachment>());
        assertEquals(response.getEntity().toString(), "No file was attached, please upload a file");

    }
}
