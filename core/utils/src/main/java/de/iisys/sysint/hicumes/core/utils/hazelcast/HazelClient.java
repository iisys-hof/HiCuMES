package de.iisys.sysint.hicumes.core.utils.hazelcast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.AutoDetectionConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.TopicConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.hazelcast.HazelCastBaseException;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class HazelClient
{

    private final Logger logger = new Logger(this.getClass().getName());
    private HazelcastInstance hz;
    private String defaultSendTopic;

    public HazelClient(String sendTopic) {
        defaultSendTopic = sendTopic;
        startHazelClient();
    }

    @SneakyThrows
    private void startHazelClient() {
        if(hz != null) {
            throw new HazelCastBaseException("Hazelclient was already started - do not start client twice", null);
        }

        // TODO: Check if still needed!
        Random r = new Random();
        int low = 5900;
        int high = 8900;
        int result = r.nextInt(high-low) + low;

        // Instance name should be unique, to avoid that hazel forms unintended clusters. If not set, defaults to "hicumes-<PORT>"
        String instanceName = System.getProperty("hicumes.hazelcast.server.name","hicumes-" + result);
        String clusterAddress = System.getProperty("hicumes.hazelcast.server.address","");

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(instanceName);

        if(!Objects.equals(clusterAddress, ""))
        {
            clientConfig.setNetworkConfig(new ClientNetworkConfig().addAddress(clusterAddress));
        }
        else
        {
            clientConfig.setNetworkConfig(new ClientNetworkConfig().setAutoDetectionConfig(new AutoDetectionConfig().setEnabled(true)));
        }

        hz = HazelcastClient.newHazelcastClient(clientConfig);
    }

    @SneakyThrows
    public void sendEvent(JsonNode event) {
        if(hz == null)
        {
            throw new HazelCastBaseException("Hazelclient was not started - start hazelclient before use", null);
        }

        logger.logMessage("SEND DATA: ", "<", "Topic:\t" + defaultSendTopic, event.toPrettyString());

        hz.getTopic(defaultSendTopic).publish(event);
    }

    public void sendMultipleEvents(ArrayNode events) {
        for (JsonNode event:events) {
            sendEvent(event);
        }
    }

    public void stopHazelClient() {
        hz.shutdown();
    }

}
