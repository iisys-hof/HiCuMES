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

public class ExtendedCamundaTest {

    public static final String BUSINESS_KEY = "TEST_BUSINESS_KEY_02";
    ObjectMapper mapper = new ObjectMapper();
    String CamundaReceiveTopic = Events.CamundaReceiveTopic.getTopic();
    String CamundaSendTopic = Events.CamundaSendTopic.getTopic();
    AsyncManager asyncManager = new AsyncManager(EventMappingCamunda.getMapping(), CamundaReceiveTopic, CamundaSendTopic);
    BpmnProcessDeployService bpmnProcessDeployService = new BpmnProcessDeployService();

    @Before
    public void before() throws IOException {
        bpmnProcessDeployService.undeployAll();
        bpmnProcessDeployService.deployBpmnTestFile("./src/test/resources/defaultCamundaData/schneiden.bpmn");
    }

    @Test
    public void requestAllDeployedProcesses_Ok() {
        var response = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.REQUEST_ALL_DEPLOYED_PROCESSES);
        assertEquals("Prozess Schneiden", response.get("eventContent").get(0).get("name").asText());
    }


    @Test
    public void startProcess_assertOneProcessRunning() {
        ObjectNode eventContent = mapper.createObjectNode()
                .put("key", "Process_Schneiden")
                .put("businessKey", BUSINESS_KEY);
        eventContent.putObject("variables")
                .put("machine", "Schneidemaschine (beliebig)").put("businessKey", BUSINESS_KEY);;

        var responseStartProcess = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.START_PROCESS, eventContent);

        var responseRequestRunningProcesses = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.REQUEST_RUNNING_PROCESSES);
        var runningProcessSize = responseRequestRunningProcesses.get("eventContent").size();
        var businessKey = responseRequestRunningProcesses.get("eventContent").get(0).get("businessKey").asText();

        assertEquals(1, runningProcessSize);
        assertEquals(BUSINESS_KEY, businessKey);
    }

    @Test
    public void requestAllTasks() {

        ObjectNode eventContent = mapper.createObjectNode()
                .put("key", "Process_Schneiden")
                .put("businessKey", BUSINESS_KEY);
        eventContent.putObject("variables")
                .put("machine", "Schneidemaschine (beliebig)").put("businessKey", BUSINESS_KEY);;

        var responseStartProcess = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.START_PROCESS, eventContent);


        var response = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.REQUEST_ALL_TASKS, eventContent);
    }

    @Test
    public void startProcess_withFormFields_assertProcessFinished() throws InterruptedException {

/**** START PROCESS ****/
        ObjectNode eventContent = mapper.createObjectNode()
                .put("key", "Process_Schneiden").put("businessKey", BUSINESS_KEY);
        eventContent.putObject("variables")
                .put("machine", "Schneidemaschine (OST)")
                .put("worker", "weber").put("businessKey", BUSINESS_KEY);;

        var responseStartProcess_Cut = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.START_PROCESS, eventContent);

/**** REQUEST ACTIVE TASK ::: MACHINE_PARAMETERS ****/

        var responseActiveTask_MachineParameters = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK, eventContent);
        var formFieldKey_parameter1 = responseActiveTask_MachineParameters.get("eventContent").get(0).get("formFields").get(0).get("key").asText();
        var businessKey = responseActiveTask_MachineParameters.get("eventContent").get(0).get("businessKey").asText();

        assertEquals("parameter1", formFieldKey_parameter1);
        assertEquals(BUSINESS_KEY, businessKey);

        String processInstanceId = responseActiveTask_MachineParameters.get("eventContent").get(0).get("processInstanceId").asText();
        String taskId_MachineParameters = responseActiveTask_MachineParameters.get("eventContent").get(0).get("taskId").asText();
        ObjectNode eventWithFormFields_MachineParameter = mapper.createObjectNode();
        eventWithFormFields_MachineParameter.put("processInstanceId", processInstanceId);
        eventWithFormFields_MachineParameter.put("taskId", taskId_MachineParameters);
        eventWithFormFields_MachineParameter.putObject("formField")
                .put("parameter1", 5);

        var responseFinished = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.FINISH_WITH_FORMFIELDS, Events.CamundaSendTopic.RESPONSE_TASK_WITH_FORM_FIELDS, eventWithFormFields_MachineParameter);

/**** WAIT FOR MACHINE TASK IS FINISHED ****/

        Thread.sleep(5000);

/**** REQUEST ACTIVE TASK ::: CUT ****/
        var responseActiveTask_Cut = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK, eventContent);
        var formFieldKey_cutform = responseActiveTask_Cut.get("eventContent").get(0).get("formFields").get(0).get("key").asText();
        var businessKey_Cut = responseActiveTask_Cut.get("eventContent").get(0).get("businessKey").asText();

        assertEquals("cutform", formFieldKey_cutform);
        assertEquals(BUSINESS_KEY, businessKey_Cut);

        String processInstanceId_Cut = responseActiveTask_Cut.get("eventContent").get(0).get("processInstanceId").asText();
        String taskId_cut = responseActiveTask_Cut.get("eventContent").get(0).get("taskId").asText();
        ObjectNode eventWithFormFields_Cut = mapper.createObjectNode();
        eventWithFormFields_Cut.put("processInstanceId", processInstanceId_Cut);
        eventWithFormFields_Cut.put("taskId", taskId_cut);
        eventWithFormFields_Cut.putObject("formField")
                .put("cutform", "Mickey Mouse");

        var responseFinished_Cut = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.FINISH_WITH_FORMFIELDS, Events.CamundaSendTopic.RESPONSE_TASK_WITH_FORM_FIELDS, eventWithFormFields_Cut);


/**** REQUEST ACTIVE TASK ::: TEST ****/
        var responseActiveTask_Test = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK, eventContent);
        var formFieldKey_QualityOK = responseActiveTask_Test.get("eventContent").get(0).get("formFields").get(0).get("key").asText();
        var businessKey3 = responseActiveTask_Test.get("eventContent").get(0).get("businessKey").asText();

        assertEquals("qualityOK", formFieldKey_QualityOK);
        assertEquals(BUSINESS_KEY, businessKey3);

        String processInstanceId3 = responseActiveTask_Test.get("eventContent").get(0).get("processInstanceId").asText();
        String taskId_Test = responseActiveTask_Test.get("eventContent").get(0).get("taskId").asText();
        ObjectNode eventWithFormFields_Test = mapper.createObjectNode();
        eventWithFormFields_Test.put("processInstanceId", processInstanceId3);
        eventWithFormFields_Test.put("taskId", taskId_Test);
        eventWithFormFields_Test.putObject("formField")
                .put("qualityOK", true);

        var responseFinished_Test = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.FINISH_WITH_FORMFIELDS, Events.CamundaSendTopic.RESPONSE_TASK_WITH_FORM_FIELDS, eventWithFormFields_Test);


/**** WAIT FOR MACHINE TASK IS FINISHED ****/

        Thread.sleep(5000);
/**** REQUEST ACTIVE TASK ::: DISARM ****/
        var responseActiveTask_Disarm = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK, eventContent);
        var formFieldKey_finishedWork = responseActiveTask_Disarm.get("eventContent").get(0).get("formFields").get(0).get("key").asText();
        var businessKey4 = responseActiveTask_Disarm.get("eventContent").get(0).get("businessKey").asText();

        assertEquals("finishedWork", formFieldKey_finishedWork);
        assertEquals(BUSINESS_KEY, businessKey4);

        String processInstanceId_Disarm = responseActiveTask_Disarm.get("eventContent").get(0).get("processInstanceId").asText();
        String taskId_Disarm = responseActiveTask_Disarm.get("eventContent").get(0).get("taskId").asText();
        ObjectNode eventWithFormFields_Disarm = mapper.createObjectNode();
        eventWithFormFields_Disarm.put("processInstanceId", processInstanceId_Disarm);
        eventWithFormFields_Disarm.put("taskId", taskId_Disarm);
        eventWithFormFields_Disarm.putObject("formField")
                .put("finishedWork", true);

        var responseFinished_Disarm = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.FINISH_WITH_FORMFIELDS, eventWithFormFields_Disarm);

        var businessKey_Disarm = responseFinished_Disarm.get("eventContent").get("businessKey").asText();
        assertEquals(BUSINESS_KEY, businessKey_Disarm);
    }
}
