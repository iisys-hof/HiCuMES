package de.iisys.sysint.hicumes.core.utils.hazelcast;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.events.IEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.awaitility.Awaitility.await;

public class AsyncManager {
    private JsonNode response;
    private IEvent waitingType;
    private EventGenerator eventGenerator;
    private HazelServer hazelServer;


    private HashMap<IEvent, IEvent> requestToResponseMap;

    public AsyncManager(HashMap<IEvent, IEvent> requestToResponseMap, String receiveTopic, String sendTopic) {
        this.requestToResponseMap = requestToResponseMap;
        response = null;
        eventGenerator = new EventGenerator();



        var listener = new MessageListener<JsonNode>() {
            @Override
            public void onMessage(Message<JsonNode> message) {
                JsonNode jsonNode = message.getMessageObject();
                if (waitingType != null && jsonNode.get("eventType").asText().equals(waitingType.toString())) {
                    response = jsonNode;
                    System.out.println("\nResponse:\n\t\t " + response + "\n");
                } else {
                    System.err.println("\nNOT HANDLED RESPONSE!!!:\n\t\t " + jsonNode + "\n");
                }
            }
        };

        List<TopicListenerConfiguration> topicListenerConfigurationList = new ArrayList<>();
        topicListenerConfigurationList.add(
                new TopicListenerConfiguration(sendTopic, listener));

        hazelServer = new HazelServer(topicListenerConfigurationList, receiveTopic);
        hazelServer.startHazelServer();

    }
    public void stop() {
        hazelServer.stopHazelServer();
    }

    public JsonNode sendAndReceive(IEvent requestType, JsonNode request) {
        var event = eventGenerator.generateEvent(requestType, request);
        return sendEvent(requestType, event);
    }

    public JsonNode sendAndReceive(IEvent requestType, IEvent responseType, JsonNode request) {
        var event = eventGenerator.generateEvent(requestType, request);
        return sendEvent(requestType, responseType, event);
    }

    public JsonNode sendAndReceive(IEvent requestType) {
        var event = eventGenerator.generateEvent(requestType);
        return sendEvent(requestType, event);
    }

    private JsonNode sendEvent(IEvent requestType, JsonNode event) {
        IEvent responseType = requestToResponseMap.get(requestType);
        return sendEvent(requestType, responseType, event);
    }

    private JsonNode sendEvent(IEvent requestType, IEvent responseType, JsonNode event) {
        hazelServer.sendEvent(event);
        JsonNode responseData = null;
        if (responseType.toString().length() > 0) {
            responseData = waitForResponse(responseType);
        }
        return responseData;
    }

    public void sendSingleEvent (String topic, JsonNode event){
        hazelServer.sendEvent(topic, event);
    }

    public void sendMultipleEvents (String topic, ArrayNode events){
        hazelServer.sendMultipleEvents(topic, events);
    }


    private JsonNode waitForResponse(IEvent waitingType) {
        response = null;
        this.waitingType = waitingType;
        try {
            await().until(() -> response != null);
        } catch (Exception e) {
            throw new RuntimeException("Waited for the type, but it never came -.- " + waitingType);
        }

        return response;
    }
}
