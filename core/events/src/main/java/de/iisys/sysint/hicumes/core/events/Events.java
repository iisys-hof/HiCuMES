package de.iisys.sysint.hicumes.core.events;

public class Events
{
    public enum CamundaReceiveTopic implements IEvent
    {
        DEPLOY_PROCESSES,
        UNDEPLOY_ALL_PROCESSES,
        REQUEST_ALL_DEPLOYED_PROCESSES,
        START_PROCESS,
        END_PROCESS,
        REQUEST_RUNNING_PROCESSES,
        REQUEST_ACTIVE_TASK,
        FINISH_WITH_FORMFIELDS,
        REQUEST_ALL_FORMS,
        REQUEST_ALL_TASKS;

        public static String getTopic() {
            return CamundaReceiveTopic.class.getSimpleName();
        }
    }

    public enum MappingReceiveTopic implements IEvent {
        REQUEST_CAMUNDA_MAPPINGS,
        RUN_SYNC;
        //RUN_SYNC_DONE,
        //NEW_MULTIPLE_DONE;

        public static String getTopic() {
            return MappingReceiveTopic.class.getSimpleName();
        }
    }

    public enum MappingSendTopic implements IEvent {
        CAMUNDA_MAPPINGS;

        public static String getTopic() {
            return MappingSendTopic.class.getSimpleName();
        }
    }

    public enum CamundaSendTopic implements IEvent
    {
        RESPONSE_DEPLOYED_PROCESS,
        RESPONSE_UNDEPLOYED_PROCESSES,
        RESPONSE_ALL_DEPLOYED_PROCESSES,
        RESPONSE_START_PROCESS,
        RESPONSE_RUNNING_PROCESSES,
        RESPONSE_ACTIVE_TASK,
        END_PROCESS,
        RELEASE_PLANNED_PROCESS,
        AUTO_TIMER_FINISH,
        RESPONSE_ALL_FORMS,
        RESPONSE_ALL_TASKS,
        RESPONSE_TASK_WITH_FORM_FIELDS,
        SEND_BOOKINGS;

        public static String getTopic() {
            return CamundaSendTopic.class.getSimpleName();
        }
    }

    public enum RunMappingTopic implements IEvent
    {
        RUN_MAPPING;

        public static String getTopic() {
            return RunMappingTopic.class.getSimpleName();
        }
    }

    public enum CoreDataTopic implements IEvent
    {
        NEW,
        NEW_MULTIPLE,
        ARCHIVE_FINISHED;

        public static String getTopic() {
            return CoreDataTopic.class.getSimpleName();
        }
    }
}
