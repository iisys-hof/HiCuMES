package de.iisys.sysint.hicumes.manufacturingTerminalBackend.services;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.TerminalMappingException;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;

import javax.ejb.Singleton;
import java.util.HashMap;

@Singleton
public class CamundaMappingStateService {

    private HashMap<String, MappingAndDataSource> mappings;

    public void update(MappingAndDataSource[] mappings)
    {
        this.mappings = new HashMap<>();

        for (MappingAndDataSource mapping: mappings) {
            this.mappings.put(mapping.getExternalId(), mapping);
        }
    }

    public MappingAndDataSource getMappingById(String id) throws TerminalMappingException
    {
        if(this.mappings == null)
        {
            throw new TerminalMappingException( "No camunda mappings contained, please request data from mapping backend first");
        }
        if(!this.mappings.containsKey(id))
        {
            throw new TerminalMappingException( "No camunda mapping was found with id: " +  id + " inside the available ids: " + String.join(", ", mappings.keySet()));
        }
        return this.mappings.get(id);
    }
}
