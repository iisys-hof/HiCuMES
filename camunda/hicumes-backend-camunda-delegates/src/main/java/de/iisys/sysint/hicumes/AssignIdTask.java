package de.iisys.sysint.hicumes;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AssignIdTask implements JavaDelegate
{
	
	public AssignIdTask(){}

	public void execute(DelegateExecution exec) throws Exception
	{
		String idPrefix = "pid_";
		int idInt = (int) (Math.random() * 10000);
		String id = idPrefix + Integer.toString(idInt);		
		
		exec.setVariable("paneId", id);

	}
}
