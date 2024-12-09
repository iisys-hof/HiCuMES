package de.iisys.sysint.hicumes.camunda;

import de.iisys.sysint.hicumes.camunda.listener.HicumesBpmnParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;

import java.util.ArrayList;
import java.util.List;

public class HicumesBpmnParser {

    private ProcessEngineConfigurationImpl processEngineConfiguration;

    public HicumesBpmnParser(ProcessEngineConfigurationImpl processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
    }

    public void startBpmnParser() {
        List<BpmnParseListener> preParseListeners = processEngineConfiguration.getCustomPreBPMNParseListeners();
        if (preParseListeners == null) {
            preParseListeners = new ArrayList<>();
            processEngineConfiguration.setCustomPreBPMNParseListeners(preParseListeners);
        }
        preParseListeners.add(new HicumesBpmnParseListener());
    }
}
