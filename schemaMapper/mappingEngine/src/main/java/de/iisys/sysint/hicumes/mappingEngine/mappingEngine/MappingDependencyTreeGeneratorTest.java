package de.iisys.sysint.hicumes.mappingEngine.mappingEngine;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingDependencyTree;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingDependencyTreeElement;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;


class MappingDependencyTreeGeneratorTest {

    @Test
    void generate() throws JsonProcessingException {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("testDatabase");
        var entityManager = emFactory.createEntityManager();
        var metaModel = entityManager.getMetamodel();
        var tree = new MappingDependencyTreeGenerator().generate(metaModel, "de.iisys.sysint.hicumes.core.entities");
        System.out.println(tree.toJson());
    }
    @Test
    void testTreeToJson() throws JsonProcessingException {
        var innerElements2 = new HashMap<String, MappingDependencyTreeElement>();
        innerElements2.put("test", new MappingDependencyTreeElement("id.123.432"));
        var innerElements = new HashMap<String, MappingDependencyTreeElement>();
        var tree = new MappingDependencyTree(innerElements);
        System.out.println(tree.toJson());

    }
}
