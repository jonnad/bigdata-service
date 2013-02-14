package com.ventyx.bigdata.webservice.rest;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Service implementation for uploading files to a configured location
 */

@Component
@Path("/upload")
public class UploadService {

    private String uploadFilepath;

    @Value("${upload.filepath}")
    public void setUploadFilepath(String uploadFilepath) {
        this.uploadFilepath = uploadFilepath;
    }

    /**
     * Handles updating a file for consumpution by the bigdata platform at some point
     *
     * @param attachments
     * @return HTTP response code
     */
    @POST
    @Path("/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(List<Attachment> attachments) {

        if (attachments == null || attachments.isEmpty()) {
            return Response.ok("No file was attached, please upload a file").build();
        }

        String fileName = null;

        for (Attachment attachment : attachments) {
            DataHandler handler = attachment.getDataHandler();
            try {
                InputStream stream = handler.getInputStream();
                MultivaluedMap map = attachment.getHeaders();
                fileName = System.currentTimeMillis() + "-" + getFileName(map);
                OutputStream out = new FileOutputStream(new File(uploadFilepath + fileName));

                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = stream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                stream.close();
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Response.ok("File successfully uploaded as " + fileName).build();
    }

    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }


}
