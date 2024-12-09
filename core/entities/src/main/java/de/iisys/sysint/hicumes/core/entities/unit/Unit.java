package de.iisys.sysint.hicumes.core.entities.unit;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.iisys.sysint.hicumes.core.entities.exceptions.UnitEntityException;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Eine Unit repräsentiert eine Menge inklusive ihrer dazugehörigen Einheit. Dazu wird automatisch
 * mit der Basiseinheit der Basisgröße der übergebenen Einheit gerechnet
 *
 * @author Holzester
 */
@Embeddable
@MappedSuperclass
public class Unit {

    private double value;

    private String unitString;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private EUnitType type;

    //Konstruktor für Hibernate
    public Unit() {
        type = EUnitType.PIECE;
        value = 0;
        unitString = "stk";
    }

    //Ermittelt anhand des unit String den Einheitentyp (Masse, Länge, Fläche, Stück) und passt value an die jeweilige Standardeinheit an (g -> kg, mm -> m, ...)
    @JsonCreator
    public Unit(@JsonProperty("value") double value, @JsonProperty("unitString") String unitString) throws UnitEntityException {
        generateFields(value, unitString);
    }

    //Erstellt eine neue Unit mit dem Wert value und dem Typ type. Dabei muss value bereits in der jeweiligen Standardeinheit sein (Mass -> kg, Length -> m, ...)
    public Unit(double value, EUnitType type) throws UnitEntityException {
        if (value < 0)
            throw new UnitEntityException("Unit darf nicht kleiner 0 sein");

        this.type = type;
        this.value = value;
        switch (type) {
            case MASS:
                unitString = "kg";
                break;
            case LENGTH:
                unitString = "m";
                break;
            case AREA:
                unitString = "qm";
                break;
            case PIECE:
                unitString = "stk";
                /*if (value - (int) value != 0)
                    throw new UnitEntityException("Für STK sind nur Ganzzahlen erlaubt");*/
                break;
            case DURATION:
                unitString = "s";
                break;
            default:
                throw new UnitEntityException("Ungueltiger Einheitentyp: " + type);
        }

        //Rundungsfehler von Double korrigieren
        roundThisValue();

//		System.out.println("New unit: "+this.value+this.unit+" -> "+this.type.toString());

    }

    public static Unit parseStringToUnit(String arg) throws UnitEntityException {
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
                return new Unit(Double.parseDouble(arg.substring(0, matcher.end())), arg.substring(matcher.end()));
            } catch (UnitEntityException e) {
                throw new UnitEntityException(arg + " ist nicht verarbeitbar!");
            }

        else
            throw new UnitEntityException("Ungueltiges Zahl-Einheiten-Format: " + arg + "\n");

    }

    private void generateFields(double value, String unit) throws UnitEntityException {
        if (value < 0)
            throw new UnitEntityException("Unit darf nicht kleiner 0 sein");

        switch (unit) {
            case "kg":
                type = EUnitType.MASS;
                this.value = value;
                this.unitString = "kg";
                break;
            case "g":
                type = EUnitType.MASS;
                this.value = 0.001 * value;
                this.unitString = "kg";
                break;
            case "m":
                type = EUnitType.LENGTH;
                this.value = value;
                this.unitString = "m";
                break;
            case "cm":
                type = EUnitType.LENGTH;
                this.value = 0.01 * value;
                this.unitString = "m";
                break;
            case "mm":
                type = EUnitType.LENGTH;
                this.value = 0.001 * value;
                break;
            case "qm":
                type = EUnitType.AREA;
                this.value = value;
                this.unitString = "qm";
                break;
            case "qcm":
                type = EUnitType.AREA;
                this.value = 0.0001 * value;
                this.unitString = "qm";
                break;
            case "qmm":
                type = EUnitType.AREA;
                this.value = 0.000001 * value;
                this.unitString = "qm";
                break;
            case "stk":
                type = EUnitType.PIECE;
                /*if (value - (int) value != 0)
                    throw new UnitEntityException("Für STK sind nur Ganzzahlen erlaubt");*/
                this.value = value;
                this.unitString = "stk";
                break;
            case "s":
                type = EUnitType.DURATION;
                this.value = value;
                this.unitString = "s";
                break;
            case "min":
                type = EUnitType.DURATION;
                this.value = 60 * value;
                this.unitString = "s";
                break;
            case "h":
                type = EUnitType.DURATION;
                this.value = 3600 * value;
                this.unitString = "s";
                break;
            default:
                throw new UnitEntityException("Ungueltige bzw. unbekannte Einheit: " + unit);
        }

//		System.out.println("New unit: "+this.value+this.unit+" -> "+this.type.toString());

//		if(this.type==null)
//			throw new UnitEntityException("Ungueltige bzw. unbekannte Einheit: "+unit);

        //Rundungsfehler von Double korrigieren
        roundThisValue();
    }

    public boolean isCompatible(Unit unit) {
        return getType() == unit.getType();
    }


    //Wert mit Einheit als String -> wenn es sich anbietet werden Werte auf passendere Einheiten umgerechnet: 0.005kg -> 5g, 2300mm -> 2.3m
    public String toString() {
        if (type == null) {
            return "unit is not defined";
        }
        switch (type) {
            case MASS:
                return value == 0 ? value + " kg" : (value < 0.1 ? value * 1000 + " g" : value + " kg");
            case LENGTH:
                return value == 0 ? value + " m" : (value > 0.1 ? value + " m" : (value < 0.001 ? value * 1000 + " mm" : value * 100 + " cm"));
            case AREA:
                return value == 0 ? value + " qm" : (value > 0.1 ? value + " qm" : (value < 0.00001 ? value * 1000000 + " qmm" : value * 10000 + " qcm"));
            case DURATION:
                return value + " s";
            default:
                return value + " " + unitString;
        }
    }

    public String getUnitString() {
        return unitString;
    }

    public double getValue() {
        roundThisValue();
        return value;
    }

    public void setValue(double value) throws UnitEntityException {
        if (unitString != null) {
            generateFields(value, unitString);
        } else {
            this.value = value;
        }
    }

    public void setUnitString(String unit) throws UnitEntityException {
        if (value != 0) {
            generateFields(value, unit);
        } else {
            this.unitString = unit;
        }
    }

    //Nach Setzen eines neuen Wertes diesen wieder auf den gültigen Wertebreich runden, um Rundungsfehler von Double auszugleichen. Rechnet auf kleinstmögliche Einheit und rundet dann.
    //Hier könnte man Genauigkeit einstellen: Jetzt: 0.1 von kleinster Einheit Genauigkeit
    private void roundThisValue() {
        switch (type) {
            case MASS:
                value = (double) Math.round(value * 10000) / 10000;
                break;
            case LENGTH:
                value = (double) Math.round(value * 10000) / 10000;
                break;
            case AREA:
                value = (double) Math.round(value * 10000000) / 10000000;
                break;
            case PIECE:
                value = (double) Math.round(value);
            case DURATION:
                value = (double) Math.round(value);
        }
    }

    /**
     * Addiert den Wert einer übergebenen Unit zu dem Wert dieser Unit, falls kompatibel
     *
     * @param unit - Type of Unit
     * @return - Returns added Unit
     * @throws UnitEntityException - Throws an exception if type is incompatible
     */
    public Unit add(Unit unit) throws UnitEntityException {
        if (isCompatible(unit)) {
            value += unit.getValue();
            return this;
        } else
            throw new UnitEntityException("Inkompatible Units können nicht addiert werden");
    }


    /**
     * Subtrahiert den Wert einer übergebenen Unit von Wert dieser Unit, falls kompatibel und Ergebnis > 0
     *
     * @param unit - Type of Unit
     * @return - Returns substracted Unit
     * @throws UnitEntityException - Throws an exception if type is incompatible
     */
    public Unit sub(Unit unit) throws UnitEntityException {
        if (!isCompatible(unit))
            throw new UnitEntityException("Inkompatible Unit\n");

        if (value - unit.value < 0)
            throw new UnitEntityException("Unit darf nicht kleiner 0 werden\n");

        value = value - unit.value;

        return this;
    }

    public EUnitType getType() {
        return type;
    }
}
