package de.iisys.sysint.hicumes.mappingBackend.startup;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.hazelcast.TopicListenerConfiguration;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.mappingBackend.events.EventController;
import de.iisys.sysint.hicumes.mappingBackend.hazelcast.listener.CamundaHazelListener;
import de.iisys.sysint.hicumes.mappingBackend.hazelcast.listener.MappingHazelListener;
import de.iisys.sysint.hicumes.mappingBackend.mapping.services.MappingPersistService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
public class StartupController
{
    private final Logger logger = new Logger(this.getClass().getName());

    public static HazelServer hazelServer;
    @EJB
    MappingPersistService mappingPersistService;
    @Inject
    CamundaHazelListener camundaHazelListener;
    EventGenerator eventGenerator = new EventGenerator();
    @Inject
    private MappingHazelListener mappingHazelListener;

    @EJB
    EventController eventController;


    @PostConstruct
    private void startup() {
        logger.logMessage("-", "-","START Microservice SchemaMapper Backend");


        List<TopicListenerConfiguration> topicListenerConfigurationList = new ArrayList<>();

        String CamundaSendTopic = Events.CamundaSendTopic.getTopic();
        topicListenerConfigurationList.add(
                new TopicListenerConfiguration(CamundaSendTopic, camundaHazelListener));


        String MappingReceivedTopic = Events.MappingReceiveTopic.getTopic();
        topicListenerConfigurationList.add(
                new TopicListenerConfiguration(MappingReceivedTopic, mappingHazelListener));

        String defaultSendTopic = Events.CamundaReceiveTopic.getTopic();

        hazelServer = new HazelServer(topicListenerConfigurationList, defaultSendTopic);
        hazelServer.startHazelServer();
        JsonNode event = eventGenerator.generateEvent(Events.CamundaReceiveTopic.REQUEST_ALL_FORMS);
        hazelServer.sendEvent(event);

        eventController.sendCamundaMappings();

    }

    @PreDestroy
    private void shutdown() {
        logger.logMessage("-", "-", "STOP Microservice SchemaMapper Backend");


        hazelServer.stopHazelServer();
    }
}
