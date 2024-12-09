package de.iisys.sysint.hicumes.core.entities;

public class PersistenceOrder {

    public static String[] getPersistenceOrder()
    {
        String[] persistenceOrderKeys = {
                "user",
                "suspensionType",
                "timeRecordType",
                "productionStepInfo",
                "cD_Department",
                "cD_Product",
                "cD_ProductRelationship",
                "cD_QualityControlFeature",
                "cD_MachineType",
                "cD_Machine",
                "cD_ToolType",
                "cD_Tool",
                "cD_ToolSettingParameter",
                "cD_ProductionStep",
                "customerOrder",
                "productionOrder",
                "toolSetting",
                "machineOccupation",
                "subProductionStep",
                "auxiliaryMaterials",
                "productionNumbers",
                "qualityManagement",
                "setUp",
                "timeRecord",
                "machineStatus",
                "machineStatusHistory",
                "note",
                "bookingEntry",
                "cD_OverheadCostCenter",
                "overheadCost"
        };

        return persistenceOrderKeys;
    }
}
