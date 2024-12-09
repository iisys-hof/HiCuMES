package de.iisys.sysint.hicumes.hicumeslite.startup;

import de.iisys.sysint.hicumes.hicumeslite.manager.PersistService;

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

    public static PersistService persistService;

    @EJB
    PersistService persistServiceInject;

    @PostConstruct
    private void startup() {
        persistService = persistServiceInject;
    }

    @PreDestroy
    @jakarta.annotation.PreDestroy
    private void shutdown() {
    }
}
