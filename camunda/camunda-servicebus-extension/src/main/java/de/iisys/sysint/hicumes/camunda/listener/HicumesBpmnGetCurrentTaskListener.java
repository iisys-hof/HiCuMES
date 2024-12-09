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
package de.iisys.sysint.hicumes.camunda.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.EventController;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;


public class HicumesBpmnGetCurrentTaskListener implements TaskListener {

    private static HicumesBpmnGetCurrentTaskListener instance = null;
    private final Logger logger = new Logger(this.getClass().getName());
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected HicumesBpmnGetCurrentTaskListener() {
        logger.logMessage("HicumesBpmnGeneralTaskListener");
    }

    public static HicumesBpmnGetCurrentTaskListener getInstance() {
        if (instance == null) {
            instance = new HicumesBpmnGetCurrentTaskListener();
        }
        return instance;
    }

    public void notify(DelegateTask delegateTask) {
        logger.logMessage("HicumesBpmnGeneralTaskListener notified");

        var eventController = EventController.getInstance();

        ObjectNode event = objectMapper.createObjectNode();
        event.put("camundaFormKey", delegateTask.getBpmnModelElementInstance().getCamundaFormKey());
        event.put("processInstanceId", delegateTask.getProcessInstanceId());
        event.put("taskId", delegateTask.getId());
        event.put("name", delegateTask.getName());
        event.put("businessKey",delegateTask.getVariable("businessKey").toString());
        if(delegateTask.getVariable("userName") != null) {
            event.put("userName", delegateTask.getVariable("userName").toString());
        }
        if(delegateTask.getVariable("suspensionType") != null) {
            event.put("suspensionType", delegateTask.getVariable("suspensionType").toString());
        }
        eventController.eventGetCurrentTask(event);
    }
}
