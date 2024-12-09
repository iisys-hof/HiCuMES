package de.iisys.sysint.hicumes.core.entities.unit;

import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.BaseUnit;
import tech.units.indriya.unit.UnitDimension;

import javax.measure.Quantity;
import javax.measure.format.MeasurementParseException;
import javax.measure.format.QuantityFormat;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Length;
import javax.measure.spi.ServiceProvider;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static de.iisys.sysint.hicumes.core.entities.unit.ExtendedUnits.PIECE;

@Converter(autoApply = true)
public class QuantityConverter implements AttributeConverter<Quantity, String> {

    private static final QuantityFormat FORMAT = ServiceProvider.current().getFormatService().getQuantityFormat();

    @Override
    public String convertToDatabaseColumn(Quantity quantity) {
        if (quantity == null) {
            return null;
        }
        return FORMAT.format(quantity);
    }

    @Override
    public Quantity convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try
        {
            return (Quantity) FORMAT.parse(dbData);
        } catch (MeasurementParseException e) {
            return ExtendedUnits.parseQuantity(dbData);
        }

    }
}
