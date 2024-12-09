package de.iisys.sysint.hicumes.mappingEngine;

import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingEngine.dataSource.reader.SingleFileReader;
import de.iisys.sysint.hicumes.mappingEngine.dataSource.readerParser.ReaderParserGenerator;
import de.iisys.sysint.hicumes.mappingEngine.dataSource.writer.InternalSchemaWriter;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.ExtensionNotFoundException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceParserException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.mappingException.MappingException;
import de.iisys.sysint.hicumes.mappingEngine.mappingEngine.MapperEngine;
import de.iisys.sysint.hicumes.mappingEngine.mappingEngine.MappingDependencyTreeGenerator;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingWorkflowResult;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.DataReader;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.writer.DataWriter;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import de.iisys.sysint.hicumes.mappingEngine.plugins.MapperPluginManager;
import de.iisys.sysint.hicumes.mappingEngine.plugins.inputPlugin.inputReader.IInputReader;
import de.iisys.sysint.hicumes.mappingEngine.plugins.outputPlugin.outputWriter.IOutputWriter;
import de.iisys.sysint.hicumes.mappingEngine.plugins.parserPlugin.dataParser.IDataParser;

public class MappingWorkflow {

    private static final MapperEngine mappingEngine = new MapperEngine();
    private static final ReaderParserGenerator readerParserGenerator = new ReaderParserGenerator();
    private static final SingleFileReader singleFileReader = new SingleFileReader();
    private static final MappingDependencyTreeGenerator mappingDependencyTreeGenerator = new MappingDependencyTreeGenerator();
    private static final InternalSchemaWriter internalSchemaWriter = new InternalSchemaWriter();
    private static HazelServer hazelServer;
    private MapperPluginManager pluginManager = MapperPluginManager.getInstance();
    private final Logger inputPluginLogger = Logger.getInstance("InputPluginLog", "inputPlugins");
    private final Logger outputPluginLogger = Logger.getInstance("OutputPluginLog", "outputPlugins");

    public MappingWorkflow(HazelServer hazelServer) {
        MappingWorkflow.hazelServer = hazelServer;
    }

    public MappingWorkflowResult run(PersistenceManager persistenceManager, MappingAndDataSource mappingAndDataSource, Boolean useSavedData, Boolean simulate) throws MappingBaseException {

        //ReaderResult readerResult = getReaderResult(mappingAndDataSource, useSavedData);
        //MappingInput mappingInput = parseReaderResult(mappingAndDataSource, readerResult);

        try
        {
            ReaderResult readerResult = getReaderResult(persistenceManager, mappingAndDataSource, useSavedData);
            MappingInput mappingInput = getParserResult(mappingAndDataSource, readerResult);

            MappingWorkflowResult mappingWorkflowResult = doMapping(persistenceManager, mappingAndDataSource, simulate, mappingInput);
            if(mappingWorkflowResult != null && !useSavedData)
            {
                mappingWorkflowResult.setReaderResult(readerResult);
            }

            //parseMappingResult();
            if(simulate == false)
            {
                writeMappingResult(persistenceManager, hazelServer, mappingWorkflowResult, mappingAndDataSource);
            }

//            MappingWorkflowResult mappingWorkflowResult1 = getMappingWorkflowResult(persistenceManager, mappingAndDataSource, simulate, mappingInput);
//            mappingWorkflowResult1.setReaderResult(readerResult);

            return mappingWorkflowResult;

            //TODO Better error handling
        } catch (Exception e)
        {
            System.out.println("Exception" + " " + e);
            e.printStackTrace();

            return null;
        }
    }


    private IInputReader initInputReader(PersistenceManager persistenceManager, DataReader dataReader)
    {
        if(dataReader == null)
        {
            return null;
        }

        try {
            IInputReader inputReader = pluginManager.getInputReader(dataReader.getReaderID());
            inputReader.setPersistenceManager(persistenceManager);
            inputReader.setLogger(inputPluginLogger);
            return inputReader;
        } catch (ExtensionNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ReaderResult getReaderResult(PersistenceManager persistenceManager, MappingAndDataSource mappingAndDataSource, Boolean useSavedData) {
        var dataReader = mappingAndDataSource.getDataReader();

        if (useSavedData && mappingAndDataSource.getReaderResult() != null ) {
            return mappingAndDataSource.getReaderResult();
        }

        IInputReader inputReader = initInputReader(persistenceManager, dataReader);

        if(inputReader != null)
        {
            try {
                return inputReader.readDatasource(mappingAndDataSource);
            } catch (MappingBaseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private IDataParser initDataParser(DataReader dataReader)
    {
        if(dataReader != null)
        {
            try {
                return pluginManager.getDataParser(dataReader.getParserID());
            } catch (ExtensionNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private MappingInput getParserResult(MappingAndDataSource mappingAndDataSource, ReaderResult readerResult) {
        var dataReader = mappingAndDataSource.getDataReader();
        IDataParser inputParser = initDataParser(dataReader);

        if(inputParser != null && readerResult != null)
        {
            try {
                return inputParser.parse(readerResult, mappingAndDataSource);
            } catch (DataSourceParserException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public MappingWorkflowResult doMapping(PersistenceManager persistenceManager, MappingAndDataSource mappingAndDataSource, Boolean simulate, MappingInput mappingInput) {
        if(mappingAndDataSource.getDataWriter() == null) {
            return new MappingWorkflowResult(mappingInput,null, null,null, null);
        }

        var dataReader = mappingAndDataSource.getDataReader();
        var dataWriter = mappingAndDataSource.getDataWriter();

        var mappingConfiguration = mappingAndDataSource.getMappingConfiguration();
        var entityFilter = "de.iisys.sysint.hicumes.core.entities";

        if(dataWriter.getWriterConfigByKey("ENTITY_FILTER") != null)
        {
            entityFilter = dataWriter.getWriterConfigByKey("ENTITY_FILTER").getConfigValue();
        }
        else if(dataReader != null && dataReader.getReaderConfigByKey("ENTITY_FILTER") != null)
        {
            entityFilter = dataReader.getReaderConfigByKey("ENTITY_FILTER").getConfigValue();
        }

        var tree = mappingDependencyTreeGenerator.generate(persistenceManager.getMetamodel(), entityFilter);

        //Removed this condition - not sure why it was there anyways
        /*if(dataWriter.getWriterID().equalsIgnoreCase("outputPlugin-DatabaseWriter") || dataWriter.getWriterID().equalsIgnoreCase("outputPlugin-NewEntityEventWriter") )
        {*/
            if (mappingInput == null || mappingConfiguration == null) {
                return new MappingWorkflowResult(mappingInput, null, null, tree, null);
            }
            try {
                MappingWorkflowResult mappingWorkflowResult = null;
                if(mappingAndDataSource.getMappingConfiguration().getXsltRules() != null)
                {
                    mappingWorkflowResult = mappingEngine.doMappingXSLT(tree, mappingInput, mappingAndDataSource.getMappingConfiguration());
                }
                else
                {
                    mappingWorkflowResult = mappingEngine.doMappingJS(tree, mappingInput, mappingAndDataSource.getMappingConfiguration());
                }

                 return mappingWorkflowResult;
            } catch (MappingException e) {
                e.printStackTrace();
                //TODO ERROR HANDLING
            }
        //}

        //Deprecated
        /*if(dataReader != null && dataReader.getReaderID().equalsIgnoreCase("inputPlugin-DatabaseReader"))
        {
            if (mappingInput == null || mappingConfiguration == null) {
                return new MappingWorkflowResult(mappingInput, null, null, tree, null);
            }

            //TODO IMPLEMENTATION
        }*/

        return null;
    }

    private IOutputWriter initOutputWriter(PersistenceManager persistenceManager, HazelServer hazelServer, DataWriter dataWriter)
    {
        if(dataWriter == null)
        {
            return null;
        }

        try {
            IOutputWriter outputWriter = pluginManager.getOutputWriter(dataWriter.getWriterID());
            outputWriter.setPersistenceManager(persistenceManager);
            outputWriter.setHazelServer(hazelServer);
            outputWriter.setLogger(outputPluginLogger);
            return outputWriter;
        } catch (ExtensionNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeMappingResult(PersistenceManager persistenceManager, HazelServer hazelServer, MappingWorkflowResult mappingWorkflowResult, MappingAndDataSource mappingAndDataSource) {

        var dataWriter = mappingAndDataSource.getDataWriter();
        IOutputWriter outputWriter = initOutputWriter(persistenceManager, hazelServer, dataWriter);

        if(outputWriter != null && mappingWorkflowResult != null)
        {
            try {
                outputWriter.writeMappingResult(mappingWorkflowResult, mappingAndDataSource);
            } catch (MappingException e) {
                e.printStackTrace();
            }
        }
    }

/*
    private MappingWorkflowResult getMappingWorkflowResult(PersistenceManager persistenceManager,MappingAndDataSource mappingAndDataSource, Boolean simulate, MappingInput mappingInput) throws MappingException {
        if(mappingAndDataSource.getDataWriter() == null) {
            return new MappingWorkflowResult(mappingInput,null, null,null, null);
        }
        var dataWriter = mappingAndDataSource.getDataWriter().getWriterType();
        switch (dataWriter) {
            case INTERNALDATASCHEMA:
                var mappingOutput = internalWriter(persistenceManager,mappingAndDataSource, mappingInput, simulate);
                return mappingOutput;
            default:
                throw new NotImplementedException("Datasource not implemented: " +dataWriter);
        }
    }

    public MappingWorkflowResult internalWriter(PersistenceManager persistenceManager,MappingAndDataSource mappingAndDataSource, MappingInput mappingInput, Boolean simulate) throws MappingException {
        var dataWriter = mappingAndDataSource.getDataWriter();
        var mappingConfiguration = mappingAndDataSource.getMappingConfiguration();
        var entityFilter = dataWriter.getInternalSchemaWriterConfig().getEntityFilter();
        var tree = mappingDependencyTreeGenerator.generate(persistenceManager.getMetamodel(), entityFilter);
        if (mappingInput ==null || mappingConfiguration == null) {
            return new MappingWorkflowResult(mappingInput, null, null, tree, null);
        }
        var mappingWorkflowResult = mappingEngine.doMappingToDatabase(tree, mappingInput, mappingAndDataSource.getMappingConfiguration());
        if (simulate == false) {
            try {
                internalSchemaWriter.write(mappingWorkflowResult.getMappingOutput(), persistenceManager);
            } catch (DatabasePersistException e) {
                throw new MappingException("Failed to persist mapping results to internal Datasource", e);
            }
        }

        return mappingWorkflowResult;
    }

    private MappingInput parseReaderResult(MappingAndDataSource mappingAndDataSource, ReaderResult readerResult) throws DataSourceParserException {
        if (readerResult == null) {
            return null;
        }
        var dataReader = mappingAndDataSource.getDataReader();
        var parser = readerParserGenerator.generate(dataReader);
        var result = parser.parse(readerResult);
        return result;
    }

    private ReaderResult getReaderResult(MappingAndDataSource mappingAndDataSource, Boolean useSavedData) throws DataSourceReaderException {
        if (useSavedData && mappingAndDataSource.getReaderResult() !=null ) {
            return mappingAndDataSource.getReaderResult();
        }
        if(mappingAndDataSource.getDataReader() == null) {
            return null;
        }
        var dataReader = mappingAndDataSource.getDataReader().getSourceType();

        switch (dataReader) {
            case SINGLEFILE:
                return singleFileReader.read(mappingAndDataSource.getDataReader().getSingleFileReaderConfig());
            default:
                throw new NotImplementedException("DataReader not implemented: " + dataReader);
        }
    }

 */
}
