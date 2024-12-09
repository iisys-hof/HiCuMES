package de.iisys.sysint.hicumes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.business.handler.mapper.FormFieldMapper;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import java.util.HashMap;
import java.util.Map;

public class AutoTimerFinish extends DelegateSuperClass implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception
    {
        super.init(execution);

        if(!super.isDisabled)
        {
            System.out.println("--------------------------------------------------");
            System.out.println("++++++++++++++++ AutoTimerFinish +++++++++++++++++");
            System.out.println("--------------------------------------------------");

            //System.out.println(execution.getVariables());

            ObjectMapper mapper = new ObjectMapper();

            String businessKey =  (String) execution.getVariable("businessKey");
            String processInstanceId =  (String) execution.getProcessInstanceId();
            String userName =  (String) execution.getVariable("userName");
            var processEngine = execution.getProcessEngine();
            //var activeTasksList = processEngine.getTaskService().createTaskQuery().active().processInstanceBusinessKey(businessKey).initializeFormKeys().list();
            //var activeTasksList = processEngine.getTaskService().createTaskQuery().active().processInstanceId(processInstanceId).list();
            var activeTasksList = processEngine.getTaskService().createTaskQuery().processInstanceId(execution.getProcessInstanceId()).list();

            ObjectNode event = mapper.createObjectNode();

            event.put("taskId", execution.getCurrentActivityId());
            event.put("processInstanceId", execution.getProcessInstanceId());
            event.put("processDefinitionId", execution.getProcessDefinitionId());
            event.put("activeProcess", execution.getActivityInstanceId());
            event.put("businessKey", businessKey);
            event.put("name", execution.getCurrentActivityName());
            event.put("formKey", "");
            event.putArray("formFields");

            event.put("taskDefinitionKey", execution.getCurrentActivityId());
            event.put("userName", userName);
            if(execution.hasVariable("timerOffset"))
            {
                int timerOffset = (int) execution.getVariable("timerOffset");
                event.put("timerOffset", timerOffset);
            }

            JsonNode newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.AUTO_TIMER_FINISH, event);

            EventController.getInstance().hazelCastSendEvent(newEvent);
        }

        else
        {
            System.out.println("--------------------------------------------------");
            System.out.println("++++++++++++++++ AutoTimerFinish +++++++++++++++++");
            System.out.println("------------ Delegate is disabled!! --------------");
            System.out.println("--------------------------------------------------");
        }
    }

}
