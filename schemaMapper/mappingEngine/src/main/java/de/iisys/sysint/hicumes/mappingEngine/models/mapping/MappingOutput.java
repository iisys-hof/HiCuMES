package de.iisys.sysint.hicumes.mappingEngine.models.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class MappingOutput {

    private HashMap<String, Object> result;

    public<T> List<T> get(Class<T> getObject) {
        var className = WordUtils.uncapitalize(getObject.getSimpleName());
        var getElement = (List<T>) result.get(className);
        return getElement;
    }

    public List<Object> resultAsList() {
        return new ArrayList(result.values());
    }

    public Object getByKey(String key)
    {
        return result.get(key);
    }

    public MappingOutput() {
    }
}
