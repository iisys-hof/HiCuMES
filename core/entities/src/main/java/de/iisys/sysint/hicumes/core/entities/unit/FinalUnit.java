package de.iisys.sysint.hicumes.core.entities.unit;

import de.iisys.sysint.hicumes.core.entities.exceptions.UnitEntityException;

import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gleiches Verhalten wie @see unit.Unit, bis auf die Möglichkeit des Änderns der Werte dieser Unit.
 *
 * @author Holzester
 */
@Embeddable
public class FinalUnit extends Unit {

    /**
     * @param value - Value of the unit
     * @param unit  - Type of unit
     * @see Unit Constructor(double, String)
     */
    public FinalUnit(double value, String unit) throws UnitEntityException {
        super(value, unit);
    }

    //Hibernate-Konstruktor
    protected FinalUnit() {
    }

    public static FinalUnit parseStringToUnit(String arg) throws UnitEntityException {
        arg = arg.toLowerCase();
        if (arg.startsWith("\"") && arg.endsWith("\""))
            arg = arg.replace("\"", "");

        //Alle Leerzeichen entfernen
        arg = arg.replace(" ", "");
        //"," ersetzen durch "." um es für Double parse-bar zu machen
        arg = arg.replace(",", ".");

        if (arg.startsWith("-") || arg.startsWith("+"))
            throw new UnitEntityException("Es sind keine Vorzeichen erlaubt");

        Matcher matcher = Pattern.compile("[0-9]+([.][0-9]*)?|[.][0-9]").matcher(arg);
        if (matcher.find())
            try {
                return new FinalUnit(Double.parseDouble(arg.substring(0, matcher.end())), arg.substring(matcher.end()));
            } catch (UnitEntityException e) {
                throw new UnitEntityException(arg + " ist nicht verarbeitbar!");
            }

        else
            throw new UnitEntityException("Ungueltiges Zahl-Einheiten-Format: " + arg + "\n");

    }

    /**
     * Ein verändern der Werte einer FinalUnit ist nicht erlaubt und löst eine IllegalWriteUnitEntityException aus
     *
     * @param value Nicht erlaubt
     */
    @Override
    public void setValue(double value) throws UnitEntityException {
        throw new UnitEntityException("Die Werte dieser Unit (FinalUnit) können nicht verändert werden");
    }

    @Override
    public Unit add(Unit unit) throws UnitEntityException {
        throw new UnitEntityException("Die Werte dieser Unit (FinalUnit) können nicht verändert werden");

    }

    @Override
    public Unit sub(Unit unit) throws UnitEntityException {
        throw new UnitEntityException("Die Werte dieser Unit (FinalUnit) können nicht verändert werden");

    }


}
