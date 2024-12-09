package de.iisys.sysint.hicumes.core.utils.hazelcast;

import com.hazelcast.topic.MessageListener;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicListenerConfiguration
{
    private String topic;
    private MessageListener messageListener;
}
