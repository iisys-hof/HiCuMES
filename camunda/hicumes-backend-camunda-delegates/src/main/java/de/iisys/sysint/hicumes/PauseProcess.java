package de.iisys.sysint.hicumes;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Map;

public class PauseProcess extends DelegateSuperClass  implements JavaDelegate {

    private final String VAR_KEY_ENABLED = "VAR_PAUSE_PROCESS_ENABLED";
    private final String VAR_KEY_SUSPSENSION_TYPE = "VAR_PAUSE_PROCESS_SUSPENSION_TYPE";
    private final String CAMUNDA_VAR_SUSPENSION_TYPE = "suspensionType";

    public PauseProcess(){

    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        super.init(execution);

        if(!super.isDisabled) {
            boolean enabled = true;
            String suspensionType = "";

            Map<String, Object> vars = execution.getVariables();

            if (vars.containsKey(VAR_KEY_ENABLED)) {

                enabled = Boolean.parseBoolean(vars.get(VAR_KEY_ENABLED).toString());
            }

            if (enabled) {

                suspensionType = vars.containsKey(VAR_KEY_SUSPSENSION_TYPE) ? vars.get(VAR_KEY_SUSPSENSION_TYPE).toString() : "BREAK_OrderChange";
                execution.setVariable(CAMUNDA_VAR_SUSPENSION_TYPE, suspensionType);
            }
        }
        else
        {
            System.out.println("--------------------------------------------------");
            System.out.println("++++++++++++++++++ PauseProcess ++++++++++++++++++");
            System.out.println("------------ Delegate is disabled!! --------------");
            System.out.println("--------------------------------------------------");
        }
    }
}
