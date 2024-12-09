package de.iisys.sysint.hicumes.mappingBackend.mapping.services;

import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.EReaderType;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MappingPersistService {

    @EJB
    PersistenceManager persistenceManager;

    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }

    public void persistMappingAndDatasourceAndRemoveOld(MappingAndDataSource mappingAndDataSource) throws DatabasePersistException {

        //removeOldMappingAndDataSource(mappingAndDataSource);
        persistenceManager.merge(mappingAndDataSource);

    }

    public void removeOldMappingAndDataSource(MappingAndDataSource mappingAndDataSource) throws DatabasePersistException {

        MappingAndDataSource oldMappingAndDataSource = null;

        try {
            oldMappingAndDataSource = persistenceManager.getById(MappingAndDataSource.class, mappingAndDataSource.getId());
            if (oldMappingAndDataSource != null) {
                persistenceManager.remove(oldMappingAndDataSource);
            }
        } catch (DatabaseQueryException e) {
            System.out.println("No old mapping was found in db for id " + mappingAndDataSource.getId());
        }
    }

    public List<MappingAndDataSource> getCamundaMappings(){
       var allMappings =  persistenceManager.getAllByClass(MappingAndDataSource.class);
       var camundaMappings = allMappings.collect(Collectors.toList());
        return camundaMappings;
    }

    public MappingAndDataSource getMappingAndDataSourceByName(String name) throws DatabaseQueryException {
        return persistenceManager.getByField(MappingAndDataSource.class, name, "name");
    }
}
