package de.iisys.sysint.hicumes;

import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.model.bpmn.instance.IntermediateThrowEvent;
import org.camunda.bpm.model.bpmn.instance.Message;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;

public class StartInstanceByMessageDelegate implements JavaDelegate
{
	
	public StartInstanceByMessageDelegate(){}

	public void execute(DelegateExecution exec) throws Exception
	{
		IntermediateThrowEvent throwEvent = (IntermediateThrowEvent) exec.getBpmnModelElementInstance();
		MessageEventDefinition eventDefinition = (MessageEventDefinition) throwEvent.getEventDefinitions().iterator().next();
		Message message = eventDefinition.getMessage();
		String messageName = message.getName();
		
		RuntimeService runtimeService = ProcessEngines.getDefaultProcessEngine().getRuntimeService();
		runtimeService.startProcessInstanceByMessage(messageName);
		System.out.println("Message sent: " + messageName);
	}
}
