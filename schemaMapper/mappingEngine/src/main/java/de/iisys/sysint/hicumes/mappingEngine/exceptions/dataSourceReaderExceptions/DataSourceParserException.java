package de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions;

import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;

public class DataSourceParserException extends MappingBaseException {
    public DataSourceParserException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}
