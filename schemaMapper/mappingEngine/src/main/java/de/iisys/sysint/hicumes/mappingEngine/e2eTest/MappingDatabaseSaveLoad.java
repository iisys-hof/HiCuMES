package de.iisys.sysint.hicumes.mappingEngine.e2eTest;

import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.EReaderType;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class MappingDatabaseSaveLoad {
    //TODO think about test startup
    @Test
    void testWriteRead() throws IOException, DataSourceReaderException, URISyntaxException {
        MappingAndDataSource mappingAndDataSource = new TestData().getXmlSingleFileMapping();

        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("testDatabase");
        var entityManager = emFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(mappingAndDataSource);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var query = entityManager.createNamedQuery("MappingAndDataSource.findAll", MappingAndDataSource.class);
        var resultList = query.getResultList();

        assertEquals(1, resultList.size());
        assertEquals( "inputPlugin-FileReader",resultList.get(0).getDataReader().getReaderID());
        assertEquals( "parserPlugin-XML",resultList.get(0).getDataReader().getParserID());
        assertEquals(2, resultList.get(0).getMappingConfiguration().getMappings().size());
        assertEquals(3, resultList.get(0).getMappingConfiguration().getLoops().get(0).getMappings().size());
    }

}
