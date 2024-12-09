package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;

public class DefaultHandler {
    protected final Logger logger = new Logger(this.getClass().getName());
    protected final ObjectMapper mapper;

    protected ProcessEngine processEngine;

    public DefaultHandler(ProcessEngine processEngine) {
        this.processEngine = processEngine;
        mapper = new ObjectMapper();
    }
}
