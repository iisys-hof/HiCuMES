package de.iisys.sysint.hicumes.manufaturingTerminal.testData.scripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.BpmnProcessDeployService;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelClient;

import java.io.File;
import java.io.IOException;

public class DemoCompanyDeployData
{


    private static String coreDataTopic = Events.CoreDataTopic.getTopic();
    //private static AsyncManager asyncManager; //= new AsyncManager(EventMappingCamunda.getMapping(), "nothing", coreDataTopic);
    private static ObjectMapper mapper = new ObjectMapper();
    //private static BpmnProcessDeployService bpmnProcessDeployService = new BpmnProcessDeployService();

    public static void main(String[] args) throws IOException {

        System.setProperty("hicumes.hazelcast.server.name","hicumes_dev");

        //AsyncManager asyncManager = new AsyncManager(EventMappingCamunda.getMapping(), "nothing", coreDataTopic);

        BpmnProcessDeployService bpmnProcessDeployService = new BpmnProcessDeployService();
        //bpmnProcessDeployService.undeployAll();
        bpmnProcessDeployService.deployBpmnTestFile("manufacturingTerminal/testData/demoCompany/BPMN/DemoCompany.bpmn");

        //sendFileViaHazelcast("nothing", coreDataTopic, "manufacturingTerminal/testData/demoCompany/Data/Departments_Prod.json");
        //sendFileViaHazelcast("nothing", coreDataTopic, "manufacturingTerminal/testData/demoCompany/Data/Machines_Prod.json");
        //sendFileViaHazelcast("nothing", coreDataTopic, "manufacturingTerminal/testData/demoCompany/Data/MachineTypes_Prod.json");
        //sendFileViaHazelcast("nothing", coreDataTopic, "manufacturingTerminal/testData/demoCompany/Data/User_Prod.json");

        bpmnProcessDeployService.stop();
    }

    private static void sendFileViaHazelcast(String receiveTopic, String sendTopic, String path) throws IOException {
        File file = new File(path);

        /*AsyncManager asyncManager = new AsyncManager(EventMappingCamunda.getMapping(), receiveTopic, sendTopic);
        asyncManager.sendMultipleEvents(sendTopic, mapper.readValue(file, ArrayNode.class));
        asyncManager.stop();*/
        HazelClient hazelClient = new HazelClient(sendTopic);
        hazelClient.sendMultipleEvents(mapper.readValue(file, ArrayNode.class));
        hazelClient.stopHazelClient();
    }
}
