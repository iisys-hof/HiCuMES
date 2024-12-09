package de.iisys.sysint.hicumes.core.utils.hazelcast;

import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.events.IEvent;

import java.util.HashMap;

public class EventMappingCamunda
{
    public static HashMap<IEvent, IEvent> getMapping() {
        var requestToResponseMap = new HashMap<IEvent,IEvent>();
        requestToResponseMap.put(Events.CamundaReceiveTopic.DEPLOY_PROCESSES, Events.CamundaSendTopic.RESPONSE_DEPLOYED_PROCESS);
        requestToResponseMap.put(Events.CamundaReceiveTopic.UNDEPLOY_ALL_PROCESSES, Events.CamundaSendTopic.RESPONSE_UNDEPLOYED_PROCESSES);
        requestToResponseMap.put(Events.CamundaReceiveTopic.REQUEST_ALL_DEPLOYED_PROCESSES, Events.CamundaSendTopic.RESPONSE_ALL_DEPLOYED_PROCESSES);
        requestToResponseMap.put(Events.CamundaReceiveTopic.START_PROCESS, Events.CamundaSendTopic.RESPONSE_START_PROCESS);
        requestToResponseMap.put(Events.CamundaReceiveTopic.REQUEST_RUNNING_PROCESSES, Events.CamundaSendTopic.RESPONSE_RUNNING_PROCESSES);
        requestToResponseMap.put(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK, Events.CamundaSendTopic.RESPONSE_ACTIVE_TASK);
        requestToResponseMap.put(Events.CamundaReceiveTopic.FINISH_WITH_FORMFIELDS, Events.CamundaSendTopic.END_PROCESS);
        requestToResponseMap.put(Events.CamundaReceiveTopic.REQUEST_ALL_FORMS, Events.CamundaSendTopic.RESPONSE_ALL_FORMS);
        requestToResponseMap.put(Events.CamundaReceiveTopic.REQUEST_ALL_TASKS, Events.CamundaSendTopic.RESPONSE_ALL_TASKS);
        return requestToResponseMap;
    }
}
