package de.iisys.sysint.hicumes.core.events;

import org.junit.jupiter.api.Test;

class EventsTest
{
    @Test
    void testEvents()
    {
        String undeployAllBpmnProcesses = Events.CamundaReceiveTopic.UNDEPLOY_ALL_PROCESSES.toString();
        System.out.println(undeployAllBpmnProcesses);
        System.out.println(Events.CamundaReceiveTopic.DEPLOY_PROCESSES);
        System.out.println(Events.CamundaReceiveTopic.getTopic());

        IEvent event = Events.CamundaSendTopic.END_PROCESS;
        System.out.println(event.toString());

        switch (Events.CamundaReceiveTopic.valueOf("UNDEPLOY_ALL_PROCESSES"))
        {
            case UNDEPLOY_ALL_PROCESSES:
            {
                System.out.println(Events.CamundaReceiveTopic.UNDEPLOY_ALL_PROCESSES);
                break;
            }
            case DEPLOY_PROCESSES:
            {
                System.out.println(Events.CamundaReceiveTopic.DEPLOY_PROCESSES);
                break;
            }
        }
    }
}
