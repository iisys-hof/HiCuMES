package de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions;


import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;

public class DataSourceReaderException extends MappingBaseException {
    public DataSourceReaderException(String message, Exception raisedException) {
        super(message, raisedException);
    }
}
