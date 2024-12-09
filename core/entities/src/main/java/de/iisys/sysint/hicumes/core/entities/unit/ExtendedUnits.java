package de.iisys.sysint.hicumes.core.entities.unit;

import tech.units.indriya.AbstractSystemOfUnits;
import tech.units.indriya.AbstractUnit;
import tech.units.indriya.function.AddConverter;
import tech.units.indriya.function.MultiplyConverter;
import tech.units.indriya.function.RationalNumber;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.*;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.*;
import java.math.BigDecimal;

public class ExtendedUnits {
    public static final javax.measure.Unit<Dimensionless> PIECE = new BaseUnit("pcs", "Pieces", UnitDimension.NONE);


    public static Quantity parseQuantity(String dbData) {
        String[] split = dbData.split(" ");
        if(split.length > 1)
        {
            BigDecimal value = new BigDecimal(split[0]);
            String symbol = split[1];
            switch (symbol)
            {
                case "pcs":
                    return Quantities.getQuantity(value, PIECE);
                default:
                    return null;
            }
        }
        return null;
    }

    public static Unit parseUnit(String dbData) {
        switch (dbData)
        {
            case "pcs":
                return PIECE;
            default:
                return null;
        }
    }
}
