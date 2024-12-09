package de.iisys.sysint.hicumes.manufaturingTerminal.testData.scripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.AsyncManager;
import de.iisys.sysint.hicumes.core.utils.hazelcast.BpmnProcessDeployService;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventMappingCamunda;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CleanUp
{

    private static String coreDataTopic = Events.CoreDataTopic.getTopic();;
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException
    {

        System.setProperty("hicumes.hazelcast.server.name","hicumes_dev");

        BpmnProcessDeployService bpmnProcessDeployService = new BpmnProcessDeployService();

        try {

            bpmnProcessDeployService.undeployAll();


        }
        catch (Exception ex){

            System.out.println("Error in CleanUp" + ex.getMessage());
        }
        finally {
            bpmnProcessDeployService.stop();
        }
    }
}
