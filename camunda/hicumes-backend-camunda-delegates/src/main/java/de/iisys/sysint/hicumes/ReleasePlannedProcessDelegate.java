package de.iisys.sysint.hicumes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.hazelcast.core.ITopic;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ReleasePlannedProcessDelegate extends DelegateSuperClass implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception
    {
        super.init(execution);

        if(!super.isDisabled)
        {
            System.out.println("--------------------------------------------------");
            System.out.println("+++++++++++++ ReleasePlannedProcess ++++++++++++++");
            System.out.println("--------------------------------------------------");

            //System.out.println(execution.getVariables());

            String businessKey =  (String) execution.getVariable("businessKey");
            String processInstanceId =  (String) execution.getVariable("processInstanceId");



            ObjectMapper mapper = new ObjectMapper();
            ObjectNode event = mapper.createObjectNode();
            event.put("businessKey", businessKey);
            event.put("processInstanceId", processInstanceId);
            if(execution.hasVariable("toolExtId"))
            {
                String mappingName = (String) execution.getVariable("toolExtId");
                event.put("toolExtId", mappingName);
            }

            JsonNode newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.RELEASE_PLANNED_PROCESS, event);

            EventController.getInstance().eventReleasePlannedProcess(newEvent);
        }
        else
        {
            System.out.println("--------------------------------------------------");
            System.out.println("+++++++++++++ ReleasePlannedProcess ++++++++++++++");
            System.out.println("------------ Delegate is disabled!! --------------");
            System.out.println("--------------------------------------------------");
        }
    }

}
