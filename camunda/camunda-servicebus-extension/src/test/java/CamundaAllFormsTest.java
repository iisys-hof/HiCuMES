import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.AsyncManager;
import de.iisys.sysint.hicumes.core.utils.hazelcast.BpmnProcessDeployService;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventMappingCamunda;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CamundaAllFormsTest {
    String CamundaReceiveTopic = Events.CamundaReceiveTopic.getTopic();
    String CamundaSendTopic = Events.CamundaSendTopic.getTopic();
    AsyncManager asyncManager = new AsyncManager(EventMappingCamunda.getMapping(), CamundaReceiveTopic, CamundaSendTopic);
    BpmnProcessDeployService bpmnProcessDeployService = new BpmnProcessDeployService();


    @Before
    public void before() throws IOException {
        bpmnProcessDeployService.undeployAll();
       bpmnProcessDeployService.deployBpmnTestFile("./src/test/resources/defaultCamundaData/simple.bpmn");
    }


    @Test
    public void requestAllForms_Ok() {
        var response = asyncManager.sendAndReceive(Events.CamundaReceiveTopic.REQUEST_ALL_FORMS);

        System.out.println(response);

        assertEquals(46, response.get("eventContent").size());
    }
}
