package de.iisys.sysint.hicumes.manufaturingTerminal.testData.scripts;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DemoCompanyDeployMappings
{

    public static String mappingEndpoint = "http://127.0.0.1:8080/mappingBackend/data/mappingEndpoint/saveMapping";

    public static void main(String[] args)
    {
        try
        {

            try (Stream<Path> paths = Files.walk(Paths.get("manufacturingTerminal/testData/demoCompany/Mappings"))) {
                paths
                        .filter(Files::isRegularFile)
                        .forEach(path -> {
                            try {
                                System.out.println(path.toString());
                                sendMappingRequest(path.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void sendMappingRequest(String pathToJson) throws IOException
    {
        URL url = new URL(mappingEndpoint);
        URLConnection connection = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)connection;
        http.setRequestMethod("POST"); // PUT is another valid option
        http.setDoOutput(true);

        File file = new File(pathToJson);

        byte[] out = FileUtils.readFileToByteArray(file);
        int length = out.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        System.out.println(http.getURL());
        http.connect();
        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
        }

        int status = http.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(http.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
    }

}
