package de.iisys.sysint.hicumes.mapperPlugins.inputPlugins.inputDatabaseReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.OverheadCost;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.models.KeyValueConfig;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.plugins.EPluginType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginInformation;
import de.iisys.sysint.hicumes.mappingEngine.plugins.inputPlugin.inputReader.IInputReader;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.EFormfieldType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.FormField;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.SelectOptions;
import org.hibernate.Hibernate;
import org.hibernate.annotations.QueryHints;
import org.pf4j.Extension;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Extension
public class InputDatabaseReader implements IInputReader {

    private PersistenceManager persistenceManager;
    private JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.ViewMachineOccupationParent.class);
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
        formFields.add(new FormField(EFormfieldType.INPUT, "CLASSNAME", "Klassenname", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "SEARCH_COLUMN", "Spaltenname", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "SEARCH_VALUE", "Suchwert", true, null, null));
        formFields.add(new FormField(EFormfieldType.TOGGLE, "LOOP_IF_NOT_FINISHED", "Wiederholen, solange der Arbeitsschritt keine Endzeit hat", true, null, null));
        formFields.add(new FormField(EFormfieldType.TEXTAREA, "ADDITIONAL", "Zusätzliche Informationen", false, null, null));
        return formFields;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.INPUT_READER, "inputPlugin-DatabaseReader","Aus Datenbank einlesen");
        return information;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private KeyValueConfig[] getParameterList(String params) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            KeyValueConfig[] parameters = mapper.readValue(params, KeyValueConfig[].class);
            return parameters;
        }
        catch (JsonProcessingException exception)
        {
            return null;
        }
    }

    private ReaderResult readDataSource(MappingAndDataSource mappingAndDataSource) throws DataSourceReaderException {

        var configMap = mappingAndDataSource.getDataReader().getReaderConfigMap();

        String classname = (String) configMap.get("CLASSNAME");
        String searchColumn = (String) configMap.get("SEARCH_COLUMN");
        String searchValue = (String) configMap.get("SEARCH_VALUE");
        //TODO CHANGE TO FALSE
        boolean loopIfNotFinished = true;
        if(configMap.containsKey("LOOP_IF_NOT_FINISHED") && configMap.get("LOOP_IF_NOT_FINISHED") != null)
        {
            loopIfNotFinished = (boolean) configMap.get("LOOP_IF_NOT_FINISHED").equals("true");
        }

        Object object = null;
        try {
            //Object object1 = this.persistenceManager.getByClassnameAndField(classname, searchValue, searchColumn);
            //System.out.println(object1);
            if(Objects.equals(searchColumn, "id"))
            {
                if(loopIfNotFinished)
                {
                    //var methods = object.getClass().getMethods();
                    int loopCount = 0;
                    boolean isFinished = false;
                    while(!isFinished && loopCount < 3)
                    {
                        object = this.persistenceManager.getByIdAndClassNameWithRefresh(classname, Long.parseLong(searchValue));
                        //Method objMethod = object.getClass().getMethod("isCurrentSubProductionStepFinished");
                        if (object instanceof MachineOccupation)
                        {
                            isFinished = ((MachineOccupation) object).isCurrentSubProductionStepFinished();
                        }
                        else if (object instanceof OverheadCost)
                        {
                            isFinished = ((OverheadCost) object).isOverheadCostFinished();
                        }
                        else
                        {
                            isFinished = true;
                        }
                        if(!isFinished)
                        {
                            //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA wait 5s");
                            ++loopCount;
                            Thread.sleep(5000);
                        }
                    }
                }
                else
                {
                    //var firstObject = this.persistenceManager.getByIdAndClassNameWithRefresh(classname, Long.parseLong(searchValue));

                    //TODO QUICK FIX - JPA HAS A PROBLEM, THAT THE FIRST QUERY SOMETIMES QUERIES OLD DATA. THE SECOND QUERY UPDATES THE ENTITY
                    object = this.persistenceManager.getByIdAndClassNameWithRefresh(classname, Long.parseLong(searchValue));
                }
            }
            else {
                if(loopIfNotFinished)
                {
                    //var methods = object.getClass().getMethods();
                    int loopCount = 0;
                    boolean isFinished = false;
                    while(!isFinished && loopCount < 3)
                    {
                        object = this.persistenceManager.getByClassnameAndFieldWithRefresh(classname, searchValue, searchColumn);
                        if (object instanceof MachineOccupation)
                        {
                            isFinished = ((MachineOccupation) object).isCurrentSubProductionStepFinished();
                        }
                        else if (object instanceof OverheadCost)
                        {
                            isFinished = ((OverheadCost) object).isOverheadCostFinished();
                        }
                        else
                        {
                            isFinished = true;
                        }
                        if(!isFinished)
                        {
                            //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA wait 5s");
                            ++loopCount;
                            Thread.sleep(5000);
                        }
                    }
                }
                else {
                    //var firstObject = this.persistenceManager.getByClassnameAndFieldWithRefresh(classname, searchValue, searchColumn);

                    //System.out.println("JPA Object: " + jsonTransformer.writeValueAsString(object));

                    //TODO QUICK FIX - JPA HAS A PROBLEM, THAT THE FIRST QUERY SOMETIMES QUERIES OLD DATA. THE SECOND QUERY UPDATES THE ENTITY
                    object = this.persistenceManager.getByClassnameAndFieldWithRefresh(classname, searchValue, searchColumn);

                    //System.out.println("JPA Object2: " + jsonTransformer.writeValueAsString(object));
                }
            }

            if(configMap.containsKey("ADDITIONAL") && configMap.get("ADDITIONAL") != null)
            {
                return new ReaderResult(jsonTransformer.writeValueAsString(object), configMap.get("ADDITIONAL"));
            }
            return new ReaderResult(jsonTransformer.writeValueAsString(object), null);
        } catch (DatabaseQueryException e) {
            throw new DataSourceReaderException("Failed to query data from database: ", e);
        } catch (JsonParsingUtilsException e) {

            throw new DataSourceReaderException("Failed to parse object to json: ", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
