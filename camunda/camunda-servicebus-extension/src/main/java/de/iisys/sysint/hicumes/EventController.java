package de.iisys.sysint.hicumes;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.business.handler.*;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.ProcessEngine;

import java.util.function.Consumer;

public class EventController {

    static EventController instance = null;
    Consumer<JsonNode> sendResponseCallback;

    private static ProcessEngine processEngine_;

    public static void setProcessEngine(ProcessEngine processEngine) {
        processEngine_ = processEngine;
    }


    private EventController(Consumer<JsonNode> sendResponseCallback) {
        this.sendResponseCallback = sendResponseCallback;
    }

    public static EventController getInstance() {
        if (instance == null) {
            throw new RuntimeException("The class " + EventController.class.getSimpleName() + " is not initialized.");
        }
        return instance;
    }

    public static EventController getInstance(Consumer<JsonNode> sendResponseCallback) {
        if (instance != null) {
            throw new RuntimeException("It is not allowed to init the class " + EventController.class.getSimpleName() + " twice.");
        }
        instance = new EventController(sendResponseCallback);
        return instance;
    }

    public void eventGetCurrentTask(JsonNode contentNode) {
        JsonNode newEvent = new GetCurrentTaskFormFieldsHandler(processEngine_).handleEvent(contentNode);
        sendEvent(newEvent);
    }

    public void eventEndProcess(JsonNode contentNode) {
        JsonNode newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.END_PROCESS, contentNode);
        sendEvent(newEvent);
    }

    public void eventUndeployAllBpmnProcesses(JsonNode contentNode) {
        JsonNode newEvent = new UndeployAllBpmnProcessesHandler(processEngine_).handleEvent(contentNode);
        sendEvent(newEvent);
    }


    public void eventDeployBpmnProcess(JsonNode contentNode) {
        JsonNode newEvent = new DeployBpmnProcessHandler(processEngine_).handleEvent(contentNode);
        sendEvent(newEvent);
        this.eventRequestAllForms(null);
    }

    public void eventRequestAllDeployedProcesse(JsonNode contentNode) {
        JsonNode newEvent = new RequestAllDeployedProcessesHandler(processEngine_).handleEvent(contentNode);
        sendEvent(newEvent);
    }

    public void evenStartProcess(JsonNode contentNode) {
        JsonNode newEvent = new StartProcessHandler(processEngine_).handleEvent(contentNode);
        sendEvent(newEvent);
    }

    public void eventManualEndProcess(JsonNode contentNode) {
        JsonNode newEvent = new EndProcessHandler(processEngine_).handleEvent(contentNode);
        if(newEvent != null) {
            sendEvent(newEvent);
        }
    }

    public void eventRequestRunningProcesses(JsonNode contentNode) {
        JsonNode newEvent = new RequestRunningProcessesHandler(processEngine_).handleEvent(contentNode);
        sendEvent(newEvent);
    }

    public void eventFinishWithFormFields(JsonNode contentNode) {
        JsonNode newEvent = new FinishWithFormFieldsHandler(processEngine_).handleEvent(contentNode);
        sendEvent(newEvent);
    }

    public void eventRequestActiveTask(JsonNode contentNode) {
        JsonNode newEvent = new RequestActiveTaskHandler(processEngine_).handleEvent(contentNode);
        sendEvent(newEvent);
    }

    public void eventRequestAllTasks(JsonNode contentNode) {
        JsonNode newEvent = new RequestAllTasksHandler(processEngine_).handleEvent(contentNode);
        sendEvent(newEvent);
    }

    public void eventRequestAllForms(JsonNode contentNode) {
        /*System.out.println("oihjoijhoijoihjoijoijpoijoijoijoijoijoij");
        System.out.println("oihjoijhoijoihjoijoijpoijoijoijoijoijoij");
        System.out.println("oihjoijhoijoihjoijoijpoijoijoijoijoijoij");*/
        JsonNode newEvent = new RequestAllFormsHandler(processEngine_).handleEvent(contentNode);
        sendEvent(newEvent);
    }

    public void eventReleasePlannedProcess(JsonNode newEvent) {
        sendEvent(newEvent);
    }
    public void hazelCastSendEvent(JsonNode newEvent) {
        sendEvent(newEvent);
    }

    private void sendEvent(JsonNode newEvent) {
        if (newEvent != null) {
            sendResponseCallback.accept(newEvent);
        }
    }
}
