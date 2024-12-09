package de.iisys.sysint.hicumes.camunda.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.EventController;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;


public class HicumesProcessEndListener implements ExecutionListener {
    private final Logger logger = new Logger(this.getClass().getName());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        logger.logMessage("HicumesProcessParseListener notified");

        var eventController = EventController.getInstance();

        ObjectNode event = objectMapper.createObjectNode();
        event.put("id", delegateExecution.getProcessInstanceId());
        event.put("businessKey", delegateExecution.getProcessBusinessKey());
        if(((ExecutionEntity) delegateExecution).isExternallyTerminated())
        {
            event.put("aborted", true);
        }

        eventController.eventEndProcess(event);
    }
}
