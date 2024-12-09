package de.iisys.sysint.hicumes.mapperPlugins.inputPlugins.inputFileReader.dataReader;

import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FileReader {

    private Map<String, String> configMap;

    public FileReader(Map<String, String> configMap)
    {
        this.configMap = configMap;
    }

    public ReaderResult read() throws DataSourceReaderException {
        Path path = getPath();
        String reader = getString(path);
        return  new ReaderResult(reader);
    }

    private Path getPath() throws DataSourceReaderException {
        if (configMap.get("FILE_PATH") == null) {
            throw new DataSourceReaderException("FileReaderConfig has no key FILE_PATH", null);
        }
        try {
            return Paths.get(configMap.get("FILE_PATH"));
        } catch (Exception e) {
            throw new DataSourceReaderException("Failed parse link to file: " + configMap.get("FILE_PATH"), e);
        }
    }

    private String getString(Path path) throws DataSourceReaderException {
        try {
            var reader = Files.readString(path);
            return reader;
        } catch (Exception e) {
            throw new DataSourceReaderException("Failed load file: " + configMap.get("FILE_PATH") , e);
        }
    }
}
