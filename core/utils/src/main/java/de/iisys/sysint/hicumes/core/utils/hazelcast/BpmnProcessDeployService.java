package de.iisys.sysint.hicumes.core.utils.hazelcast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.events.Events;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BpmnProcessDeployService
{
    String CamundaReceiveTopic = Events.CamundaReceiveTopic.getTopic();
    String CamundaSendTopic = Events.CamundaSendTopic.getTopic();
    AsyncManager asyncManager = new AsyncManager(EventMappingCamunda.getMapping(), CamundaReceiveTopic, CamundaSendTopic);
    ObjectMapper mapper = new ObjectMapper();

    public void undeployAll() {
        asyncManager.sendAndReceive(Events.CamundaReceiveTopic.UNDEPLOY_ALL_PROCESSES);
    }

    public void deployBpmnTestFile(String s) throws IOException
    {
        deployBpmnFile(s);
    }

    public void stop() {
        asyncManager.stop();
    }

    private void deployBpmnFile(String name) throws IOException {
        File bmpnFileBig = new File(name);
        String bpmnContent = FileUtils.readFileToString(bmpnFileBig, StandardCharsets.UTF_8);
        ObjectNode bpmnBig = mapper.createObjectNode().put("bpmn", bpmnContent).put("name", name);
        var response = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.DEPLOY_PROCESSES, bpmnBig);
    }
}
