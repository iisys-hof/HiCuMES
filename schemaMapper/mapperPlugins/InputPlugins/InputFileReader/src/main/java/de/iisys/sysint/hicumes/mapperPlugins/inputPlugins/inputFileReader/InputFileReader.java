package de.iisys.sysint.hicumes.mapperPlugins.inputPlugins.inputFileReader;

import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mapperPlugins.inputPlugins.inputFileReader.dataReader.FileReader;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.plugins.EPluginType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginInformation;
import de.iisys.sysint.hicumes.mappingEngine.plugins.inputPlugin.inputReader.IInputReader;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.EFormfieldType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.FormField;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Extension
public class InputFileReader implements IInputReader {

    private PersistenceManager persistenceManager;
    Logger logger;

    @Override
    public ReaderResult readDatasource(MappingAndDataSource mappingAndDataSource) throws MappingBaseException {

        try {
            var readerResult = readDataSource(mappingAndDataSource);
            return readerResult;
        } catch (DataSourceReaderException e)
        {
            throw new DataSourceReaderException("Failed to read mappingAndDataSource!", e);
        }
    }

    @Override
    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    private List<FormField> getFormfields() {
        List<FormField> formFields = new ArrayList<>();
        formFields.add(new FormField(EFormfieldType.INPUT, "FILE_PATH", "Dateipfad", true, null, null));
        return formFields;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.INPUT_READER, "inputPlugin-FileReader","Aus Datei einlesen");
        return information;
    }


    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }


    private ReaderResult readDataSource(MappingAndDataSource mappingAndDataSource) throws DataSourceReaderException {
        FileReader fileReader = null;
        fileReader = new FileReader(mappingAndDataSource.getDataReader().getReaderConfigMap());

        return fileReader.read();
    }
}
