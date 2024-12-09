package de.iisys.sysint.hicumes.manufacturingTerminalBackend.startup;

import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.core.utils.hazelcast.TopicListenerConfiguration;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IEmitEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.hazelcast.CamundaHazelListener;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.hazelcast.CoreDataHazelListener;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.hazelcast.MappingHazelListener;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.hazelcast.RunMappingHazelListener;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.CoreDataPersistenceManager;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.PersistService;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

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
public class StartupController {

    private final Logger logger = new Logger(this.getClass().getName());

    public static HazelServer hazelServer;
    public static PersistService persistService;
    public static CoreDataPersistenceManager coreDataPersistenceManager;
    public static IEmitEvent events;
    public static SSESessionManager sessionManager = SSESessionManager.getInstance();

    @EJB
    PersistService persistServiceInject;
    @Inject
    private IEmitEvent eventsInject;

    @EJB
    CoreDataPersistenceManager coreDataPersistenceManagerInject;

    @PostConstruct
    private void startup() {
        logger.logMessage("-", "-","START Microservice Manufacturing Terminal Backend");

        persistService = persistServiceInject;
        coreDataPersistenceManager = coreDataPersistenceManagerInject;
        events = eventsInject;
        List<TopicListenerConfiguration> topicListenerConfigurationList = new ArrayList<>();
        topicListenerConfigurationList.add(
                new TopicListenerConfiguration(Events.CamundaSendTopic.getTopic(), new CamundaHazelListener())
        );
        topicListenerConfigurationList.add(
            new TopicListenerConfiguration(Events.CoreDataTopic.getTopic(), new CoreDataHazelListener())
        );
        topicListenerConfigurationList.add(
                new TopicListenerConfiguration(Events.MappingSendTopic.getTopic(), new MappingHazelListener())
        );
        topicListenerConfigurationList.add(
                new TopicListenerConfiguration(Events.RunMappingTopic.getTopic(), new RunMappingHazelListener())
        );

        String defaultSendTopic = Events.CamundaReceiveTopic.getTopic();

        hazelServer = new HazelServer(topicListenerConfigurationList, defaultSendTopic);
        hazelServer.startHazelServer();

        var event = new EventGenerator().generateEvent(Events.MappingReceiveTopic.REQUEST_CAMUNDA_MAPPINGS);
        hazelServer.sendEvent(Events.MappingReceiveTopic.getTopic(),event);
    }

    @PreDestroy
    @jakarta.annotation.PreDestroy
    private void shutdown() {
        sessionManager.destroy();
        logger.logMessage("-", "-", "STOP Microservice Manufacturing Terminal Backend");


        hazelServer.stopHazelServer();
    }
}
