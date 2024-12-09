package de.iisys.sysint.hicumes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class RunMappingDelegate extends DelegateSuperClass implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception
    {
        super.init(execution);

        if(!super.isDisabled)
        {
            System.out.println("--------------------------------------------------");
            System.out.println("+++++++++++++ RunMappingDelegate +++++++++++++++++");
            System.out.println("--------------------------------------------------");
            ObjectNode event = super.buildHazelcastRunMappingEvent(execution);

            JsonNode newEvent = new EventGenerator().generateEvent(Events.RunMappingTopic.RUN_MAPPING, event);

            EventController.getInstance().hazelCastSendEvent(newEvent);
        }
        else
        {
            System.out.println("--------------------------------------------------");
            System.out.println("+++++++++++++ RunMappingDelegate +++++++++++++++++");
            System.out.println("------------ Delegate is disabled!! --------------");
            System.out.println("--------------------------------------------------");
        }
    }
}
