package de.iisys.sysint.hicumes.core.entities.adapters;

import de.iisys.sysint.hicumes.core.entities.unit.Measurement;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MeasurementAdapter
        extends XmlAdapter<Measurement, Measurement>
{

    public Measurement unmarshal(Measurement measurement) {
        measurement.generateQuantity();
        return measurement;
    }

    public Measurement marshal(Measurement measurement) {
        return measurement;
    }

}
