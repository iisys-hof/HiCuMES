package de.iisys.sysint.hicumes.core.entities.adapters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

@Converter(autoApply = true)
public class JPADurationAdapter  implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration attribute) {
        return attribute != null ? attribute.toSeconds() : null;
    }

    @Override
    public Duration convertToEntityAttribute(Long dbData) {
        return dbData != null ? Duration.ofSeconds(dbData) : null;
    }
}
