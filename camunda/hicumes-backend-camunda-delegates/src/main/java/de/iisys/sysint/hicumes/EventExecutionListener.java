package de.iisys.sysint.hicumes;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.xml.type.ModelElementType;

public class EventExecutionListener implements ExecutionListener {

    public EventExecutionListener(){

    }

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        ProcessDefinition processDef = execution.getProcessEngineServices().getRepositoryService().getProcessDefinition(
                execution.getProcessDefinitionId());
        System.out.println("############# Execution Listener ###############");
        System.out.println("Current Process: " + processDef.getKey() + processDef.getName());
        System.out.println("Event occurred: " +execution.getEventName());
        String currentActivityId = execution.getCurrentActivityId();
        ModelElementType elementType = execution.getBpmnModelInstance().getDefinitions().getElementType();
        System.out.println("ModelElementType: " + elementType.getTypeName());
        String activityType = getTaskType(currentActivityId);
        System.out.println("CurrentActivity: " + currentActivityId);
    }

    String getTaskType(String taskId){
        int i = taskId.indexOf("_");
        if(i>0)
            return taskId.substring(0, i);
        else
            return taskId;
    }
}
