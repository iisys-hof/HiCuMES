package de.iisys.sysint.hicumes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendBookingsDelegate extends DelegateSuperClass implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception
    {
        super.init(execution);

        if(!super.isDisabled)
        {
            System.out.println("--------------------------------------------------");
            System.out.println("+++++++++++++ SendBookingsDelegate +++++++++++++++");
            System.out.println("--------------------------------------------------");
            ObjectNode event = buildEvent(execution);

            JsonNode newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.SEND_BOOKINGS, event);

            EventController.getInstance().hazelCastSendEvent(newEvent);
        }
        else
        {
            System.out.println("--------------------------------------------------");
            System.out.println("+++++++++++++ SendBookingsDelegate +++++++++++++++");
            System.out.println("------------ Delegate is disabled!! --------------");
            System.out.println("--------------------------------------------------");
        }
    }

    private ObjectNode buildEvent(DelegateExecution execution) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode event = mapper.createObjectNode();

        if(execution.hasVariable("requestAddress"))
        {
            String requestAddress  = (String) execution.getVariable("requestAddress");
            event.put("requestAddress", requestAddress);
        }

        if(execution.hasVariable("requestType"))
        {
            String requestType = (String) execution.getVariable("requestType");
            event.put("requestType", requestType);
        }

        if(execution.hasVariable("headerParameters"))
        {
            String headerParameters = (String) execution.getVariable("headerParameters");
            event.put("headerParameters", headerParameters);
        }

        if(execution.hasVariable("queryParameters"))
        {
            String queryParameters = (String) execution.getVariable("queryParameters");
            event.put("queryParameters", queryParameters);
        }

        return event;
    }

}
