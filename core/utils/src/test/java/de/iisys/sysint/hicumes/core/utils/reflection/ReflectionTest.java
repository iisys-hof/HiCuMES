package de.iisys.sysint.hicumes.core.utils.reflection;

import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import org.junit.jupiter.api.Test;

import javax.persistence.ManyToOne;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReflectionTest {

    @Test
    void getFieldsWithAnnotation() throws UtilsBaseException {
        String machineOccupationJson = "{\"externalId\":\"machineOccupation\",\"productionStep\":{\"externalId\":\"productionStep\"},\"machine\":{\"externalId\":\"machine1\"},\"tool\":{\"externalId\":\"tool1\"},\"productionOrder\":{\"externalId\":\"productionOrder1\"}}";
        var machineOccupation = new JsonTransformer(JsonViews.ViewMachineOccupation.class).readValue(machineOccupationJson, MachineOccupation.class);
        System.out.println(machineOccupation);
        //TODO Check if this is correct
        assertEquals("productionStep", machineOccupation.getProductionSteps().get(0).getExternalId());

        List<FieldWrapper> fieldsWithAnnotation = new Reflection().getFieldsWithAnnotation(machineOccupation, ManyToOne.class);

        for (FieldWrapper field : fieldsWithAnnotation) {
            EntitySuperClass dependency = (EntitySuperClass) field.getField();
            System.out.println(dependency.getClass().getSimpleName() + "with external id: " + dependency.getExternalId());
        }

    }
}
