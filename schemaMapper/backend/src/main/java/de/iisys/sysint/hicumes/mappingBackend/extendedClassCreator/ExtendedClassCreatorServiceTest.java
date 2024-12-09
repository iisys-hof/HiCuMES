package de.iisys.sysint.hicumes.mappingBackend.extendedClassCreator;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtendedClassCreatorServiceTest {

    @Test
    void doesObjectContainField() throws ClassNotFoundException {
        Class<?> entityType = Class.forName("de.iisys.sysint.hicumes.core.entities.CD_Machine");
        var result = new ExtendedClassCreatorService().doesObjectContainField(entityType, "name");
        assertTrue(result);

        result = new ExtendedClassCreatorService().doesObjectContainField(entityType, "notAField");
        assertFalse(result);
    }
    @Test
    void test() {
        Duration.ofSeconds(123);
    }
}
