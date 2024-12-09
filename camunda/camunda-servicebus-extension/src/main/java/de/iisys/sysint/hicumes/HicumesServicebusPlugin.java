/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.iisys.sysint.hicumes;

import de.iisys.sysint.hicumes.camunda.HicumesBpmnParser;
import de.iisys.sysint.hicumes.camunda.formFieldType.DoubleFormFieldType;
import de.iisys.sysint.hicumes.camunda.formFieldType.DynamicEnumFormFieldType;
import de.iisys.sysint.hicumes.camunda.validator.RegexValidator;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.core.utils.hazelcast.TopicListenerConfiguration;
import de.iisys.sysint.hicumes.hazelcast.CamundaHazelListener;

import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>{@link ProcessEnginePlugin} enabling the assignee informing parse listener.</p>
 */
public class HicumesServicebusPlugin extends AbstractProcessEnginePlugin {
    private final Logger logger = new Logger(this.getClass().getName());
    private HazelServer hazelServer;
    private HicumesBpmnParser hicumesBpmnParser;


    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        startHazelcastPlugin();
        registerCustomFormFieldTypes(processEngineConfiguration);
        registerCustomFormFieldValidators(processEngineConfiguration);
        startCamundaParsePlugin(processEngineConfiguration);
    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {
        logger.logMessage("Called postProcessEngineBuild", ":");
        EventController.setProcessEngine(processEngine);
        EventController.getInstance().eventRequestAllForms(null);
    }

    private void startCamundaParsePlugin(ProcessEngineConfigurationImpl processEngineConfiguration) {
        logger.logMessage("Starting HicumesBpmnParsePlugin", ":");
        hicumesBpmnParser = new HicumesBpmnParser(processEngineConfiguration);
        hicumesBpmnParser.startBpmnParser();

    }

    private void registerCustomFormFieldValidators(ProcessEngineConfigurationImpl processEngineConfiguration)
    {
        Map<String, Class<? extends FormFieldValidator>> customValidators = new HashMap<>();
        customValidators.put("pattern", RegexValidator.class);
        processEngineConfiguration.setCustomFormFieldValidators(customValidators);
    }

    private void registerCustomFormFieldTypes(ProcessEngineConfigurationImpl processEngineConfiguration){
        if (processEngineConfiguration.getCustomFormTypes() == null) {
            processEngineConfiguration.setCustomFormTypes(new ArrayList<AbstractFormFieldType>());
        }

        List<AbstractFormFieldType> formTypes = processEngineConfiguration.getCustomFormTypes();
        formTypes.add(new DoubleFormFieldType());
        formTypes.add(new DynamicEnumFormFieldType());
    }

    private void startHazelcastPlugin() {
        logger.logMessage("Starting HazelcastPlugin", ":");

        var eventController = EventController.getInstance((newEvent) ->
        {
            logger.logMessage("Event Callback", ".", newEvent);
            hazelServer.sendEvent(newEvent);
        });

        CamundaHazelListener camundaHazelListener = new CamundaHazelListener(eventController);
        String CamundaReceiveTopic = Events.CamundaReceiveTopic.getTopic();
        List<TopicListenerConfiguration> topicListenerConfigurationList = new ArrayList<>();
        topicListenerConfigurationList.add(
                new TopicListenerConfiguration(CamundaReceiveTopic, camundaHazelListener));

        String defaultSendTopic = Events.CamundaSendTopic.getTopic();
        hazelServer = new HazelServer(topicListenerConfigurationList, defaultSendTopic);
        hazelServer.startHazelServer();
    }
}
