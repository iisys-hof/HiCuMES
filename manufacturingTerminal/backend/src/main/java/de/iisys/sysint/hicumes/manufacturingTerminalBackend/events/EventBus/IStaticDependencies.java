package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.CoreDataPersistenceManager;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.PersistService;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.startup.StartupController;

public interface IStaticDependencies
{
    HazelServer hazelServer = StartupController.hazelServer;
    PersistService persistService = StartupController.persistService;
    CoreDataPersistenceManager coreDataPersistenceManager = StartupController.coreDataPersistenceManager;
    IEmitEvent events = StartupController.events;
    ObjectMapper mapper = new ObjectMapper();
}
