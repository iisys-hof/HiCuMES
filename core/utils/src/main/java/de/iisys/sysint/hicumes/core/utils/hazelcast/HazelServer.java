package de.iisys.sysint.hicumes.core.utils.hazelcast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.TopicConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
//import com.hazelcast.core.ITopic;
import de.iisys.sysint.hicumes.core.events.IEvent;
import com.hazelcast.topic.ITopic;
import de.iisys.sysint.hicumes.core.utils.exceptions.hazelcast.HazelCastBaseException;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Random;
//import java.util.UUID;
//import java.util.concurrent.*;

public class HazelServer
{

    private final Logger logger = new Logger(this.getClass().getName());
    private HazelcastInstance hz;
    private List<TopicListenerConfiguration> topicListenerConfigurations;
    private String defaultSendTopic;

    //private static final ConcurrentHashMap<String, CompletableFuture<String>> requestFutures = new ConcurrentHashMap<>();

    public HazelServer(List<TopicListenerConfiguration> topicListenerConfigurations, String defaultSendTopic) {
        this.topicListenerConfigurations = topicListenerConfigurations;
        this.defaultSendTopic = defaultSendTopic;
    }

    @SneakyThrows
    public void startHazelServer() {
        if(hz != null) {
            throw new HazelCastBaseException("Hazelserver was allready started - do not start server twice", null);
        }
        Config config = new Config();

        // TODO: Check if still needed!
        Random r = new Random();
        int low = 5900;
        int high = 8900;
        int result = r.nextInt(high-low) + low;

        // Instance name should be unique, to avoid that hazel forms unintended clusters. If not set, defaults to "hicumes-<PORT>"
        String instanceName = System.getProperty("hicumes.hazelcast.server.name","hicumes-" + result);
        config.setClusterName(instanceName);

        /*config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(true);
        config.getNetworkConfig().setReuseAddress(true);
        config.getNetworkConfig().setPort( result )
                .setPortAutoIncrement( true );*/


//        config.getGroupConfig().setName("Hicumes");

        System.out.println(config.getMemberAttributeConfig().getAttributes());

        for (TopicListenerConfiguration topicConfig: this.topicListenerConfigurations) {
            TopicConfig newTopic = generateTopicConfig(topicConfig.getTopic());
            config.addTopicConfig(newTopic);
        }
        hz = Hazelcast.newHazelcastInstance(config);

        for (TopicListenerConfiguration topicConfig: this.topicListenerConfigurations) {
            ITopic<JsonNode> topicCamunda = hz.getTopic(topicConfig.getTopic());
            topicCamunda.addMessageListener(topicConfig.getMessageListener());
            logger.logMessage("HazelCast Topic Stats", "+", topicCamunda.getName() + " : " + topicCamunda.getLocalTopicStats());
        }

        logger.logMessage("Hazel Lorem");

        logger.logMessage("HazelCast Group Name", "+", instanceName);

        logger.logMessage("HazelCast Group Config", "+", config.getMemberAttributeConfig().getAttributes());

        logger.logMessage("Hazel Server Started", "+");
        logger.logMessage("HazelCast Members", "+", hz.getCluster().getMembers());
    }

    public void sendEvent(JsonNode event) {
        var eventTopicString = event.get("eventTopic");
        if(eventTopicString != null)
        {
            this.sendEvent(eventTopicString.asText(), event);
        }
        else {
            this.sendEvent(this.defaultSendTopic, event);
        }
    }

    @SneakyThrows
    public void sendEvent(String topic, JsonNode event) {
        if(hz == null)
        {
            throw new HazelCastBaseException("Hazelserver was not started - start hazelserver before use", null);
        }

        //logger.logMessage("SEND DATA: ", "<", "Topic:\t" + topic, event.toPrettyString());

        if(hz.getTopic(topic)== null) {
            hz.getConfig().addTopicConfig(generateTopicConfig(topic));
        }
        hz.getTopic(topic).publish(event);
    }

    public void sendMultipleEvents(String topic, ArrayNode events) {
        for (JsonNode event:events) {
            sendEvent(topic,event);
        }
    }

    public void sendMultipleEvents(ArrayNode events) {
        for (JsonNode event:events) {
            sendEvent(event);
        }
    }

    public void stopHazelServer() {
        hz.shutdown();
    }

    private TopicConfig generateTopicConfig(String topicName) {
        TopicConfig topicConfig = new TopicConfig();
        topicConfig.setName(topicName);
        topicConfig.setStatisticsEnabled(true);
        return topicConfig;
    }

   /* public String sendAndWait(String topic, JsonNode event)
    {
        var id = UUID.randomUUID().toString();
        CompletableFuture<String> responseFuture = new CompletableFuture<>();
        requestFutures.put(id, responseFuture);

        ((ObjectNode)event).put("eventId", id);

        sendEvent(topic,event);

        try {

            System.out.println("Waiting for completion " + this);
            var response = responseFuture.get(300, TimeUnit.SECONDS);
            System.out.println("Completed " + response + " " + this );
            return response;
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.err.println("Exception while execution");
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.err.println("WAITED FOR RESPONSE BUT IT NEVER CAME");
            e.printStackTrace();
        }
        return null;
    }

    public void handleResponse(JsonNode event)
    {
        if(event.get("eventContent") != null && event.get("eventContent").get("eventId") != null )
        {
            var id = event.get("eventContent").get("eventId").asText();
            CompletableFuture<String> responseFuture = requestFutures.get(id);
            if (responseFuture != null) {
                responseFuture.complete(event.toPrettyString());
                requestFutures.remove(id);
            } else {
                System.err.println("No corresponding request found for response with ID: " + id);
            }
        }
    }*/
}
