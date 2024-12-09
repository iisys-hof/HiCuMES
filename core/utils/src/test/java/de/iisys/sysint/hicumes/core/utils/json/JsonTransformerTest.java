package de.iisys.sysint.hicumes.core.utils.json;

import de.iisys.sysint.hicumes.core.entities.*;
import de.iisys.sysint.hicumes.core.entities.CD_Machine;
import de.iisys.sysint.hicumes.core.entities.CD_MachineType;
import de.iisys.sysint.hicumes.core.entities.CD_ProductionStep;
import de.iisys.sysint.hicumes.core.entities.CD_Tool;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;

class JsonTransformerTest {


    @Test
    void testView() throws JsonParsingUtilsException {


        var machineType = new CD_MachineType("type");
        var machine = new CD_Machine("Bohrer", machineType);
        var productionSteps = new ArrayList<CD_ProductionStep>();
        productionSteps.add(new CD_ProductionStep());
//TODO change ""
        var machineOccupation = new MachineOccupation(productionSteps,
                new CD_Tool(),
                new ProductionOrder(),
                ""
                );
        machineOccupation.updateMachine(machine);

        var schemaMapperJsonTransformer = new JsonTransformer(JsonViews.SchemaMapperDefault.class);
        var MachineOccupationJsonTransformer = new JsonTransformer(JsonViews.ViewMachineOccupation.class);

        var json = schemaMapperJsonTransformer.writeValueAsString(machineOccupation);
        System.out.println(json);

        json = MachineOccupationJsonTransformer.writeValueAsString(machineOccupation);
        System.out.println(json);


        json = MachineOccupationJsonTransformer.writeValueAsString(machineType);
        System.out.println(json);

    }

}
