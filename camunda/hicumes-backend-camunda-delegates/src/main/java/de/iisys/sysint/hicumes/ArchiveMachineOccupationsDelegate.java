package de.iisys.sysint.hicumes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ArchiveMachineOccupationsDelegate extends DelegateSuperClass implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception
    {
        super.init(execution);

        if(!super.isDisabled)
        {
            System.out.println("--------------------------------------------------");
            System.out.println("+++++++ ArchiveMachineOccupationsDelegate ++++++++");
            System.out.println("--------------------------------------------------");
            ObjectNode event = buildArchiveMOEvent(execution);

            JsonNode newEvent = new EventGenerator().generateEvent(Events.CoreDataTopic.ARCHIVE_FINISHED, event);

            EventController.getInstance().hazelCastSendEvent(newEvent);
        }
        else
        {
            System.out.println("--------------------------------------------------");
            System.out.println("+++++++ ArchiveMachineOccupationsDelegate ++++++++");
            System.out.println("------------ Delegate is disabled!! --------------");
            System.out.println("--------------------------------------------------");
        }
    }


    private ObjectNode buildArchiveMOEvent(DelegateExecution execution) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode event = mapper.createObjectNode();

        if(execution.hasVariable("daysAgo"))
        {
            int daysAgo = (int) execution.getVariable("daysAgo");
            event.put("daysAgo", daysAgo);
        }
        else
        {

            event.put("daysAgo", 7);
        }
        return event;
    }

}
