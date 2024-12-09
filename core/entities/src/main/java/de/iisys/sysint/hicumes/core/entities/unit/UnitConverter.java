package de.iisys.sysint.hicumes.core.entities.unit;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.MeasurementParseException;
import javax.measure.format.QuantityFormat;
import javax.measure.format.UnitFormat;
import javax.measure.spi.ServiceProvider;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static tech.units.indriya.AbstractUnit.ONE;

@Converter(autoApply = true)
public class UnitConverter implements AttributeConverter<Unit, String> {

    private static final UnitFormat FORMAT = ServiceProvider.current().getFormatService().getUnitFormat();

    @Override
    public String convertToDatabaseColumn(Unit unit) {
        if (unit == null) {
            return null;
        }
        return FORMAT.format(unit);
    }

    @Override
    public Unit convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.equals("")) {
            return ONE;
        }
        try
        {
            return (Unit) FORMAT.parse(dbData);
        } catch (MeasurementParseException e) {
            return ExtendedUnits.parseUnit(dbData);
        }

    }
}
