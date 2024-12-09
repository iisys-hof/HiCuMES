package de.iisys.sysint.hicumes;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONArray;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

public class DecisionStartListener implements JavaDelegate
{
	
	public DecisionStartListener(){}

	public void execute(DelegateExecution exec) throws Exception	{

		String invoiceCategoriesString = (String) exec.getVariable("invoiceCategories");
		System.out.println("JSON from Variable: " + invoiceCategoriesString);
		
		JSONObject jsnobject = new JSONObject(invoiceCategoriesString);
		JSONArray jsonArray = jsnobject.getJSONArray("rules");

		for (int i = 0; i < jsonArray.length(); i++) {
		    System.out.println(jsonArray.getJSONObject(i).toString());
		}
	}
}
