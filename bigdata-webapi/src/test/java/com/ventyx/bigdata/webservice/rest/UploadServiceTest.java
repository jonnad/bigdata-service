package com.ventyx.bigdata.webservice.rest;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.testng.annotations.Test;

import javax.activation.DataHandler;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import java.util.*;

import static org.testng.Assert.assertEquals;

import static org.mockito.Mockito.*;

/**
 *
 * Upload service unit test
 *
 */

public class UploadServiceTest {

    @Test
    public void testUploadFile() throws Exception {

        UploadService uploadService = new UploadService();
        Response response = uploadService.uploadFile(null);
        assertEquals(response.getEntity().toString(), "No file was attached, please upload a file");

        response = uploadService.uploadFile(new ArrayList<Attachment>());
        assertEquals(response.getEntity().toString(), "No file was attached, please upload a file");

        uploadService = mock(UploadService.class);

        when(uploadService.uploadFile(null)).thenReturn(response);

        /*
        TODO enhance mocking
        List<Attachment> attachments = mock(ArrayList.class);

        Attachment attachment = new Attachment("", "", "");
        when(attachments.get(0)).thenReturn(attachment);
        attachments.add(attachment);

        DataHandler dataHandler = mock(DataHandler.class);
        when(attachment.getDataHandler()).thenReturn(dataHandler);

        MultivaluedMap multivaluedMap = mock(MultivaluedMap.class);
        when(attachment.getHeaders()).thenReturn(multivaluedMap);

        when(multivaluedMap.getFirst("Content-Disposition")).thenReturn("sometextfile.txt;");

        uploadService.uploadFile(attachments);
        verify(uploadService).uploadFile(attachments);
        */

    }
}
