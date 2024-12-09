package de.iisys.sysint.hicumes.mappingEngine.dataSource.reader;

import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.SingleFileReaderConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SingleFileReader{

    public ReaderResult read(SingleFileReaderConfig dataSource) throws DataSourceReaderException {
        Path path = getPath(dataSource);
        String reader = getString(dataSource, path);
        return  new ReaderResult(reader);
    }

    private Path getPath(SingleFileReaderConfig dataSource) throws DataSourceReaderException {
        if (dataSource == null) {
            throw new DataSourceReaderException("SingleFileReaderConfig is null", null);
        }
        try {
            return Paths.get(dataSource.getPath());
        } catch (Exception e) {
            throw new DataSourceReaderException("Failed parse link to file: " + dataSource.getPath(), e);
        }
    }

    private String getString(SingleFileReaderConfig dataSource, Path path) throws DataSourceReaderException {
        try {
            var reader = Files.readString(path);
            return reader;
        } catch (Exception e) {
            throw new DataSourceReaderException("Failed load file: " + dataSource.getPath(), e);
        }
    }
}
