package de.iisys.sysint.hicumes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.camunda.bpm.engine.delegate.DelegateExecution;

public class DelegateSuperClass {

    boolean isDisabled = false;

    public void init(DelegateExecution execution) throws Exception
    {
        if(execution.hasVariable("disabled"))
        {
            isDisabled = (boolean) execution.getVariable("disabled");
        }
    }

    public ObjectNode buildHazelcastRunMappingEvent(DelegateExecution execution) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode event = mapper.createObjectNode();

        if(execution.hasVariable("mappingName"))
        {
            String mappingName = (String) execution.getVariable("mappingName");
            event.put("mappingName", mappingName);
        }
        if(execution.hasVariable("useSavedData"))
        {
            String useSavedData = (String) execution.getVariable("useSavedData");
            event.put("useSavedData", useSavedData);
        }
        if(execution.hasVariable("isSimulate"))
        {
            String isSimulate = (String) execution.getVariable("isSimulate");
            event.put("isSimulate", isSimulate);
        }
        if(execution.hasVariable("readerConfig"))
        {
            String readerConfig = (String) execution.getVariable("readerConfig");
            event.put("readerConfig", readerConfig);
        }
        if(execution.hasVariable("writerConfig"))
        {
            String writerConfig = (String) execution.getVariable("writerConfig");
            event.put("writerConfig", writerConfig);
        }
        if(execution.hasVariable("writerParserConfig"))
        {
            String writerParserConfig = (String) execution.getVariable("writerParserConfig");
            event.put("writerParserConfig", writerParserConfig);
        }
        if(execution.hasVariable("readerParserConfig"))
        {
            String readerParserConfig = (String) execution.getVariable("readerParserConfig");
            event.put("readerParserConfig", readerParserConfig);
        }
        return event;
    }

}
