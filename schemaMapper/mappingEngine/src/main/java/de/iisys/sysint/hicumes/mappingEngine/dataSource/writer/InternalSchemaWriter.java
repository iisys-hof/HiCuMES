package de.iisys.sysint.hicumes.mappingEngine.dataSource.writer;

import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingOutput;

public class InternalSchemaWriter {

    public void write(MappingOutput output, PersistenceManager persistenceManager) throws DatabasePersistException {

        //output itterieren und persist aufrufen
        for (var singleObject: output.resultAsList()) {
            persistenceManager.persistEntitySuperClass((EntitySuperClass) singleObject);
        }


    }

}
