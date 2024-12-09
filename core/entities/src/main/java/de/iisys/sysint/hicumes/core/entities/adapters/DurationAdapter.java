package de.iisys.sysint.hicumes.core.entities.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DurationAdapter
        extends XmlAdapter<String, Duration>
{

    public Duration unmarshal(String inputDuration) {
        if (inputDuration == null)
        {return null;}
        Double inputDoubleMilliSeconds = Double.parseDouble(inputDuration) * 1000;
        return Duration.ofMillis(inputDoubleMilliSeconds.longValue());
    }

    public String marshal(Duration inputDuration) {
        return inputDuration != null ? inputDuration.toString() : null;
    }

}

