package de.iisys.sysint.hicumes;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientSecurityConfig;
import com.hazelcast.config.Config;
//import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.TopicConfig;
import com.hazelcast.core.*;
import com.hazelcast.topic.ITopic;
import de.iisys.sysint.hicumes.HazelTaskListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HazelController implements Runnable {

    public static final String processStarted = "processStarted";
    public static final String taskStarted = "taskStarted";
    public static final String processEnded = "processEnded";
    public static final String taskCompleted = "taskCompleted";
    public static final String serviceTaskStarted = "serviceTaskStarted";

    static HazelcastInstance hzInstance;
    //static ITopic<String> topic;

    // Achtung, aktuell wird scheinbar nur der TaskListener benutzt, nicht der HazelController
    public HazelController() {
    }

    private static void addTopicConfig(Config config, String topicName){
        TopicConfig topicConfig = new TopicConfig();
        topicConfig.setName(topicName);
        topicConfig.setStatisticsEnabled(true);
        config.addTopicConfig(topicConfig);
    }

    private static void setGroupConfig(Config config){
        //GroupConfig grpConfig = config.getGroupConfig();
        //grpConfig.setName("dev");
        //grpConfig.setPassword("test");
    }

    private static void setMcConfig(Config config){
        ManagementCenterConfig mcc = config.getManagementCenterConfig();
        //mcc.set(true);
        //mcc.setUrl("http://localhost:8080/hazelcast-mancenter");
        config.setManagementCenterConfig(mcc);
    }

    public static HazelcastInstance getHzInstance(){
        if (hzInstance == null){
            Config config = new Config();
            //setGroupConfig(config);
            addTopicConfig(config, taskStarted);
            addTopicConfig(config, taskCompleted);
            addTopicConfig(config, "messageEvent");
            addTopicConfig(config, processStarted);
            addTopicConfig(config, processEnded);
            addTopicConfig(config, serviceTaskStarted);
            setMcConfig(config);
            hzInstance = Hazelcast.newHazelcastInstance(config);
            System.out.println("Hazel Controller started");
        }
        //listener1 = new TaskCompleteMessageListener();
        //topic.addMessageListener(listener1);

        return hzInstance;
    }

    public static ITopic<String> getTopic(String topic){
        return getHzInstance().getTopic(topic);
    }

    @Override
    public void run() {
        //ClientService hzClient  = getHzInstance().getClientService();
    }

}
