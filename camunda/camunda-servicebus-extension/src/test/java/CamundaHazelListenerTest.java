import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.AsyncManager;
import de.iisys.sysint.hicumes.core.utils.hazelcast.BpmnProcessDeployService;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventMappingCamunda;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CamundaHazelListenerTest {

    public static final String BUSINESS_KEY = "TEST_BUSINESS_KEY_01";
    ObjectMapper mapper = new ObjectMapper();
    String CamundaReceiveTopic = Events.CamundaReceiveTopic.getTopic();
    String CamundaSendTopic = Events.CamundaSendTopic.getTopic();
    AsyncManager asyncManager = new AsyncManager(EventMappingCamunda.getMapping(), CamundaReceiveTopic, CamundaSendTopic);
    BpmnProcessDeployService bpmnProcessDeployService = new BpmnProcessDeployService();

    @Before
    public void before() throws IOException, InterruptedException {

        bpmnProcessDeployService.undeployAll();
        bpmnProcessDeployService.deployBpmnTestFile("./src/test/resources/defaultCamundaData/simple.bpmn");
    }

    @Test
    public void requestAllDeployedProcesses_Ok() throws InterruptedException {
        var response = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.REQUEST_ALL_DEPLOYED_PROCESSES);

        assertEquals("Prozess Brennofen", response.get("eventContent").get(0).get("name").asText());
    }

    @Test
    public void startProcess_assertOneProcessRunning() {
        ObjectNode eventContent = mapper.createObjectNode()
                .put("key", "Process_Brennofen")
                .put("businessKey", BUSINESS_KEY);
        eventContent.putObject("variables")
                .put("machine", "Drehmaschine").put("businessKey", BUSINESS_KEY);;

        var responseStartProcess = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.START_PROCESS, eventContent);

        var responseRequestRunningProcesses = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.REQUEST_RUNNING_PROCESSES);
        var runningProcessSize = responseRequestRunningProcesses.get("eventContent").size();
        var businessKey = responseRequestRunningProcesses.get("eventContent").get(0).get("businessKey").asText();

        assertEquals(1, runningProcessSize);
        assertEquals(BUSINESS_KEY, businessKey);
    }

    @Test
    public void startProcess_withFormFields_assertProcessFinished() {
        ObjectNode eventContent = mapper.createObjectNode()
                .put("key", "Process_Brennofen").put("businessKey", BUSINESS_KEY);
        eventContent.putObject("variables")
                .put("machine", "Drehmaschine").put("businessKey", BUSINESS_KEY);;

        var responseStart = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.START_PROCESS, eventContent);

        var responseActiveTask = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK, eventContent);
        var testKey = responseActiveTask.get("eventContent").get(0).get("formFields").get(0).get("key").asText();
        var businessKey = responseActiveTask.get("eventContent").get(0).get("businessKey").asText();

        assertEquals("testString", testKey);
        assertEquals(BUSINESS_KEY, businessKey);

        String processInstanceId = responseActiveTask.get("eventContent").get(0).get("processInstanceId").asText();
        String taskId = responseActiveTask.get("eventContent").get(0).get("taskId").asText();
        ObjectNode eventWithFormFields = mapper.createObjectNode();
        eventWithFormFields.put("processInstanceId", processInstanceId);
        eventWithFormFields.put("taskId", taskId);
        eventWithFormFields.putObject("formField")
                .put("testString", "testStringValueFrontend")
                .put("testBool", Boolean.TRUE)
                .put("testLong", 5);

        var responseFinished = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.FINISH_WITH_FORMFIELDS, eventWithFormFields);
        var businessKey2 = responseFinished.get("eventContent").get("businessKey").asText();

        assertEquals(BUSINESS_KEY, businessKey2);
    }
}
