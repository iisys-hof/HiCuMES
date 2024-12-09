package de.iisys.sysint.hicumes.core.entities.jsonViews;

public class JsonViews {


    public static class ViewCoreData extends IJsonView {
        //Eingeschränke Menge von SchemaMapperDefault (z.B. für Produkt)
    }

    public static class SchemaMapperDefault extends ViewCoreData{
        //über jedes @OneToMany eine Annotation
    }

    public static class ViewMachineOccupationChild extends ViewMachineOccupation {
        //Zur Darstellung der MachineOccupations mit Parent und ohne Childs

    }

    public static class ViewMachineOccupationParent extends ViewMachineOccupation {
        //Zur Darstellung der MachineOccupations mit Childs und ohne Parent

    }

    public static class ViewMachineOccupation extends IJsonView {
        //Bei allen verbindungen in MachineOccupation und so weit runter wie nötig

    }

    public static class ViewHideEverywhere extends IJsonView {
        //Überall dort verwenden, wo ein Feld nicht auftauchen soll aber die JSON View leer wäre

    }
    public static class IJsonView{

    }

}
