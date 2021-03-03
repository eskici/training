package com.example.hackerrank;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

/**
 * @author Taner YILDIRIM
 */
public class FileMimeType {

    public static String getMimeType(String filePath) throws IOException {

        File file = new File(filePath);
        return Files.probeContentType(file.toPath());
    }

    public static String getMimeTypeViaUrl(String filePath) throws IOException {
        try{
            URL url = new URL(filePath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            InputStream content = (InputStream)connection.getInputStream();
            connection.getHeaderField("Content-Type");

            System.out.println("Content-Type "+ connection.getHeaderField("Content-Type"));
            BufferedReader in = new BufferedReader (new InputStreamReader(content));

        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }


    public static void main(String[] args) {
        try {
            String mimeType = getMimeTypeViaUrl("C://temp//TEST.pdf");
            System.out.println(mimeType);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
