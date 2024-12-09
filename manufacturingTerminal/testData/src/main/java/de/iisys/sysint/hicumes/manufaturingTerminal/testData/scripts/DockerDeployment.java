package de.iisys.sysint.hicumes.manufaturingTerminal.testData.scripts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.iisys.sysint.hicumes.core.entities.PersistenceOrder;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.AsyncManager;
import de.iisys.sysint.hicumes.core.utils.hazelcast.BpmnProcessDeployService;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventMappingCamunda;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelClient;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

public class DockerDeployment {


    private static String coreDataTopic = Events.CoreDataTopic.getTopic();
    //private static AsyncManager asyncManager; //= new AsyncManager(EventMappingCamunda.getMapping(), "nothing", coreDataTopic);
    private static ObjectMapper mapper = new ObjectMapper();
    //private static BpmnProcessDeployService bpmnProcessDeployService = new BpmnProcessDeployService();

    private static String mappingEndpoint = "http://hicumes:8080/mappingBackend/data/mappingEndpoint/saveMapping";



    public static void main(String[] args) throws IOException {

        System.out.println("########################################################################");
        System.out.println("~~~                      Deploying Data                              ~~~");
        System.out.println("########################################################################");

        String hazelClusterName = "";
        String hazelClusterAddress = "";
        String basePath = "";

        if (args != null && args.length >= 2) {
            hazelClusterName = args[1];
            basePath = args[0];

            if(args.length == 3) {
                hazelClusterAddress = args[2];
            }

            if(args.length == 4) {
                mappingEndpoint = args[3];
            }

            System.out.println("###### ~~~~~~~~~~ ###### Base Path is " + basePath);

            System.out.println("###### ~~~~~~~~~~ ###### Setting Hazelcast Server name to " + hazelClusterName);
            System.setProperty("hicumes.hazelcast.server.name", hazelClusterName);


            if(!Objects.equals(hazelClusterAddress, "")) {
                System.out.println("###### ~~~~~~~~~~ ###### Setting Hazelcast ClusterAddress to " + hazelClusterName);
                System.setProperty("hicumes.hazelcast.server.address", hazelClusterAddress);
            }


            // BPMN
            System.out.println("###### ~~~~~~~~~~ ###### Deploying BPMNs");
            BpmnProcessDeployService bpmnProcessDeployService = new BpmnProcessDeployService();
            //bpmnProcessDeployService.undeployAll();

            File folder = new File(basePath + "/BPMN");
            System.out.println("###### ~~~~~~~~~~ " + folder.getAbsolutePath());
            File[] files = folder.listFiles();

            if(files != null) {

                for (int i = 0; i < files.length; i++) {

                    if (files[i].getName().endsWith("bpmn")) {
                        System.out.println(files[i].getAbsolutePath());
                        bpmnProcessDeployService.deployBpmnTestFile(files[i].getAbsolutePath());
                    }
                }
            }
            else {

                System.out.println("###### ~~~~~~~~~~ ###### No BPMN files");
            }

            bpmnProcessDeployService.stop();


            // Data JSONs
            System.out.println("###### ~~~~~~~~~~ ###### Deploying Data");
            HazelClient hazelClient = new HazelClient(coreDataTopic);
            //AsyncManager asyncManager = new AsyncManager(EventMappingCamunda.getMapping(), "nothing", coreDataTopic);

            HashMap<String, ArrayNode> jsons = new HashMap<>();

            String[] eventClasses = PersistenceOrder.getPersistenceOrder();

            folder = new File(basePath + "/Data");
            System.out.println("###### ~~~~~~~~~~ " + folder.getAbsolutePath());
            files = folder.listFiles();

            if(files != null) {

                for(int i = 0; i < files.length; i++){

                    if(files[i].getName().endsWith("json")){
                        ArrayNode arrayNode = mapper.readValue(files[i], ArrayNode.class);

                        if(arrayNode.size() > 0) {
                            JsonNode firstNode = arrayNode.get(0);
                            String eventClass = firstNode.get("eventContent").get("eventClass").textValue();
                            jsons.put(eventClass.toUpperCase(), arrayNode);
                        }
                    }
                }

                if(jsons.size() > 0) {

                    for (String eventclass : eventClasses) {

                        if(jsons.containsKey(eventclass.toUpperCase())) {

                            System.out.println("###### ~~~~~~~~~~ Deploying class " + eventclass);

                            System.out.println(jsons.get(eventclass.toUpperCase()).size());

                            sendFileViaHazelcast(jsons.get(eventclass.toUpperCase()), hazelClient);
                        }
                    }
                }

            }
            else {

                System.out.println("###### ~~~~~~~~~~ ###### No Data files");
            }


            hazelClient.stopHazelClient();


            // Mappings
            System.out.println("###### ~~~~~~~~~~ ###### Deploying Mappings");
            folder = new File(basePath + "/Mapping");
            System.out.println("###### ~~~~~~~~~~ " + folder.getAbsolutePath());
            files = folder.listFiles();

            if(files != null) {

                for(int i = 0; i < files.length; i++){

                    if(files[i].getName().endsWith("json")){
                        System.out.println(files[i].getAbsolutePath());
                        sendMappingRequest(files[i].getAbsolutePath());
                    }
                }
            }
            else {

                System.out.println("###### ~~~~~~~~~~ ###### No Mapping files");
            }


            System.out.println("########################################################################");
            System.out.println("~~~                      Deployment DONE                             ~~~");
            System.out.println("########################################################################");
        }
        else {

            System.out.println("###### ~~~~~~~~~~ ###### Couldn't deploy. Wrong number of arguments....");
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

    private static void sendFileViaHazelcast(String path, HazelClient hazelClient/*, AsyncManager asyncManager*/) throws IOException {
        File file = new File(path);

        hazelClient.sendMultipleEvents(mapper.readValue(file, ArrayNode.class));
        //asyncManager.sendMultipleEvents(coreDataTopic, mapper.readValue(file, ArrayNode.class));
    }

    private static void sendFileViaHazelcast(ArrayNode jsonArray, HazelClient hazelClient/*, AsyncManager asyncManager*/) throws IOException {

        hazelClient.sendMultipleEvents(jsonArray);
        //asyncManager.sendMultipleEvents(coreDataTopic, jsonArray);
    }
}
