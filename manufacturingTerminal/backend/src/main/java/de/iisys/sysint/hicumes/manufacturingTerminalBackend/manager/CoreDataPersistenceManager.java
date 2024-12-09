package de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager;

import de.iisys.sysint.hicumes.core.entities.*;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.ExceptionInfo;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.exceptions.reflection.ReflectionUtilsException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.core.utils.reflection.FieldWrapper;
import de.iisys.sysint.hicumes.core.utils.reflection.Reflection;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IStaticDependencies.hazelServer;
import static de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IStaticDependencies.persistService;

@Stateless
public class CoreDataPersistenceManager {

    private final Logger logger = Logger.getInstance("CoreDataPersistenceManager", "coreDataPersistenceManager");
    private Reflection reflection = new Reflection();
    private ExceptionInfo exceptionInfo = new ExceptionInfo();
    @PersistenceContext(unitName = "HicumesMySQLDatasource")
    EntityManager entityManager;

    @Transactional
    public synchronized boolean persistEntitySuperClass(EntitySuperClass entry, boolean forceUpdate) throws DatabasePersistException {
        try
        {
            //System.out.print("SYNC Entity: ");
            //System.out.println(entry);
            var dbEntry = getByExternalId(entry);
            if(!forceUpdate) {
                logger.logMessage("Entry with externalID " + entry.getExternalId() + " already exists in database for " + entry.getClass());
                return false;
            }
            else
            {

                //System.out.println(entry.toString());
                //System.out.println(dbEntry.toString());
                entry = (EntitySuperClass) copyFieldsFromDbObjectToUpdatedObject(dbEntry, entry);
                //System.out.println(entry.toString());
                entry = updateDependenciesManyToOne(entry);
                entry = updateDependenciesManyToMany(entry);
                entry = updateDependenciesOneToMany(entry);
                entry.setId(dbEntry.getId());
                if(entry.getVersionNr() == null)
                {
                    entry.setVersionNr(dbEntry.getVersionNr());
                }
                if(entry instanceof MachineOccupation)
                {
                    ((MachineOccupation) entry).setTotalProductionNumbers(((MachineOccupation) dbEntry).getTotalProductionNumbers());
                    ((MachineOccupation) entry).setTimeDurations(((MachineOccupation) dbEntry).getTimeDurations());

                    //We need to add all the new tools to the previous tools, instead of overwriting it
                    var entryTools = ((MachineOccupation) entry).getAvailableTools();
                    var dbEntryTools = ((MachineOccupation) dbEntry).getAvailableTools();
                    entryTools.addAll(dbEntryTools.stream().filter(tool -> !entryTools.contains(tool)).collect(Collectors.toList()));

                    if(((MachineOccupation) entry).getMachine() == null && ((MachineOccupation) dbEntry).getMachine() != null)
                    {
                        ((MachineOccupation) entry).setMachine(((MachineOccupation) dbEntry).getMachine());
                    }

                    if(((MachineOccupation) dbEntry).getUserOccupation() != null) {
                        ((MachineOccupation) entry).setUserOccupation(((MachineOccupation) dbEntry).getUserOccupation());
                        ((MachineOccupation) entry).getUserOccupation().setMachineOccupation((MachineOccupation) entry);
                    }

                    if(!(((MachineOccupation) entry).getStatus().equals(EMachineOccupationStatus.ARCHIVED)))
                    {
                        ((MachineOccupation) entry).setStatus(((MachineOccupation) dbEntry).getStatus());
                    }
                }
                if(entry instanceof ProductionOrder)
                {
                    ((ProductionOrder) entry).setMachineOccupations(((ProductionOrder) dbEntry).getMachineOccupations());
                    ((ProductionOrder) entry).setMeasurement(((ProductionOrder) dbEntry).getMeasurement());
                    ((ProductionOrder) entry).setNotes(((ProductionOrder) dbEntry).getNotes());
                    for(Map.Entry<String, String> dbMapEntry : ((ProductionOrder) dbEntry).getKeyValueMap().entrySet())
                    {
                        ((ProductionOrder) entry).getKeyValueMap().putIfAbsent(dbMapEntry.getKey(), dbMapEntry.getValue());
                    }

                }
                if(entry instanceof SubProductionStep)
                {
                    ((SubProductionStep) entry).setTimeDurations(((SubProductionStep) dbEntry).getTimeDurations());
                }
                if(entry instanceof CD_MachineType)
                {
                    //If we have a MachineType, we need to add all the new department to the previous departments, instead of overwriting it
                    var entryDepartments = ((CD_MachineType) entry).getDepartments();
                    var dbEntryDepartments = ((CD_MachineType) dbEntry).getDepartments();
                    entryDepartments.addAll(dbEntryDepartments.stream().filter(department -> !entryDepartments.contains(department)).collect(Collectors.toList()));
                }
                if(entry instanceof CD_ProductRelationship)
                {
                    ((CD_ProductRelationship) entry).setMeasurement(((CD_ProductRelationship) dbEntry).getMeasurement());
                }

                merge(entry);
                return false;
            }
        } catch (DatabaseQueryException e) {
            //Entry is not found in db, so we can add it

            //TODO check if implementation is ok like this
            //There is the possibility, that we already have the mo in the db but it got a new number (name) so it is "new" for this impelenation but not in the db
            /*if(entry instanceof MachineOccupation)
            {
                //var dbEntry = findMOinDB() -> check if there is another mo with the same mgr/camunda process but with another number

                ((MachineOccupation) entry).setTotalProductionNumbers(((MachineOccupation) dbEntry).getTotalProductionNumbers());
                ((MachineOccupation) entry).setTimeDurations(((MachineOccupation) dbEntry).getTimeDurations());
                if(!(((MachineOccupation) entry).getStatus().equals(EMachineOccupationStatus.ARCHIVED)))
                {
                    ((MachineOccupation) entry).setStatus(((MachineOccupation) dbEntry).getStatus());
                }
                ((MachineOccupation) entry).setStatus(ABORTED);
            }*/

            entry = updateDependenciesManyToOne(entry);
            entry = updateDependenciesManyToMany(entry);
            entry = updateDependenciesOneToMany(entry);
            entry = updateDependenciesOneToOne(entry);
            persist(entry);
            //idCache.put(entry.getClass() + "_" + entry.getExternalId(), entry.getId());
            //System.out.print(entry.getClass() + "_" + entry.getExternalId());
            //System.out.print(" - ");
            //System.out.println(entry.getId());
            return true;
        }

    }

    public Object copyFieldsFromDbObjectToUpdatedObject(Object baseObject, Object updateObject) {
        Class<?> classUpdate = updateObject.getClass();
        Class<?> classBase = baseObject.getClass();

        // Iterate over the fields of the update Object
        for (Field fieldUpdate : classUpdate.getDeclaredFields()) {
            fieldUpdate.setAccessible(true);
            try {
                //Get same field from base object
                Field fieldBase = classBase.getDeclaredField(fieldUpdate.getName());
                fieldBase.setAccessible(true);
                //Only update fields without relation
                if(!fieldUpdate.isAnnotationPresent(ManyToMany.class) && !fieldUpdate.isAnnotationPresent(OneToMany.class))
                {


                    // Get both values from objects
                    Object valueBase = fieldBase.get(baseObject);
                    Object valueUpdate = fieldUpdate.get(updateObject);
                    //If value in the updated object is null, copy the value from the db object
                    if (valueUpdate == null) {
                        // Set the field value in update object with the value from db object
                        fieldUpdate.set(updateObject, valueBase);
                    }
                }
            } catch (NoSuchFieldException e) {
                // Ignore if the field does not exist in object B
            } catch (IllegalAccessException ie) {

            }
        }

        return updateObject;
    }

    @Transactional
    public EntitySuperClass updateDependenciesOneToMany(EntitySuperClass entity) throws DatabasePersistException {

        List<FieldWrapper> fieldsWithAnnotation = reflection.getFieldsWithAnnotation(entity, OneToMany.class);
        for (FieldWrapper field : fieldsWithAnnotation) {
            try {
                List<Object> dependencies = (List<Object>) field.getField();

                for (int i = 0; i < dependencies.size(); i++) {
                    if (dependencies.get(i) != null && ((EntitySuperClass) dependencies.get(i)).getExternalId() != null) {
                        Object dependency = null;
                        try
                        {
                            //dependency = getByExternalId(dependencies.get(i));
                            /*var id = idCache.get(((EntitySuperClass) dependencies.get(i)).getClass() + "_" + ((EntitySuperClass) dependencies.get(i)).getExternalId());
                            if(id == null)
                            {*/
                            var id = getIdByExternalId(dependencies.get(i).getClass().getSimpleName(), ((EntitySuperClass) dependencies.get(i)).getExternalId());
                            //}
                            if(id > 0)
                            {
                                dependency = entityManager.getReference(EntitySuperClass.class, id);
                            }
                        }
                        /*catch (DatabaseQueryException e)
                        {
                            logger.logMessage("Could not find dependency with externalID " + dependencies.get(i).getExternalId() + " in database; Using dependency from event: " + dependencies.get(i).toString());
                            e.printStackTrace();
                            dependency = dependencies.get(i);
                        }*/
                        catch (EntityNotFoundException e)
                        {
                            logger.logMessage("Could not find dependency with externalID " + ((EntitySuperClass) dependencies.get(i)).getExternalId() + " in database; Using dependency from event: " + dependencies.get(i).toString());
                            e.printStackTrace();
                            dependency = dependencies.get(i);
                        }

                        var methods = dependency.getClass().getMethods();
                        var bums = "set" + entity.getClass().getSimpleName();
                        var dings = dependency.getClass().getMethod("set" + entity.getClass().getSimpleName(), entity.getClass());
                        dings.invoke(dependency, entity);
                        dependencies.set(i, dependency);
                    }
                }

                field.setField(dependencies);
            } catch (ReflectionUtilsException e) {
                throw new DatabasePersistException("Exception during updating Dependency with externalId.", e);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return entity;

    }


    @Transactional
    public EntitySuperClass updateDependenciesOneToOne(EntitySuperClass entity) throws DatabasePersistException {

        List<FieldWrapper> fieldsWithAnnotation = reflection.getFieldsWithAnnotation(entity, OneToOne.class);

        for (FieldWrapper field : fieldsWithAnnotation) {
            try {
                Object dependency = field.getField();
                if (dependency != null && ((EntitySuperClass) dependency).getExternalId() != null) {
                    Object result = null;
                    try
                    {
                        /*var id = idCache.get(((EntitySuperClass) dependency).getClass() + "_" + ((EntitySuperClass) dependency).getExternalId());
                        if(id == null)
                        {*/
                        var id = getIdByExternalId(dependency.getClass().getSimpleName(), ((EntitySuperClass) dependency).getExternalId());
                        //}

                        if(id > 0) {
                            result = entityManager.getReference(dependency.getClass(), id);
                        }
                    }
                    /*catch (DatabaseQueryException e)
                    {
                        logger.logMessage("Could not find dependency with externalID " + dependency.getExternalId() + " in database; Using dependency from event: " + dependency.toString());
                        e.printStackTrace();
                        result = dependency;
                    }*/
                    catch (EntityNotFoundException e)
                    {
                        logger.logMessage("Could not find dependency with externalID " + ((EntitySuperClass) dependency).getExternalId() + " in database; Using dependency from event: " + dependency.toString());
                        e.printStackTrace();
                        result = dependency;
                    }
                    field.setField(result);
                }
            } catch (ReflectionUtilsException e) {
                throw new DatabasePersistException("Exception during updating Dependency with externalId.", e);
            }
        }

        return entity;


    }

    @Transactional
    public EntitySuperClass updateDependenciesManyToOne(EntitySuperClass entity) throws DatabasePersistException {

        List<FieldWrapper> fieldsWithAnnotation = reflection.getFieldsWithAnnotation(entity, ManyToOne.class);

        for (FieldWrapper field : fieldsWithAnnotation) {
            try {
                Object dependency = field.getField();
                if (dependency != null && ((EntitySuperClass) dependency).getExternalId() != null) {
                    Object result = null;
                    try
                    {
                        /*var id = idCache.get(((EntitySuperClass) dependency).getClass() + "_" + ((EntitySuperClass) dependency).getExternalId());
                        if(id == null)
                        {*/
                        var id = getIdByExternalId(dependency.getClass().getSimpleName(), ((EntitySuperClass) dependency).getExternalId());
                        //}

                        if(id > 0) {
                            result = entityManager.getReference(dependency.getClass(), id);
                        }
                    }
                    /*catch (DatabaseQueryException e)
                    {
                        logger.logMessage("Could not find dependency with externalID " + dependency.getExternalId() + " in database; Using dependency from event: " + dependency.toString());
                        e.printStackTrace();
                        result = dependency;
                    }*/
                    catch (EntityNotFoundException e)
                    {
                        logger.logMessage("Could not find dependency with externalID " + ((EntitySuperClass) dependency).getExternalId() + " in database; Using dependency from event: " + dependency.toString());
                        e.printStackTrace();
                        result = dependency;
                    }
                    field.setField(result);
                }
            } catch (ReflectionUtilsException e) {
                throw new DatabasePersistException("Exception during updating Dependency with externalId.", e);
            }
        }

        return entity;

    }

    @Transactional
    public EntitySuperClass updateDependenciesManyToMany(EntitySuperClass entity) throws DatabasePersistException {

        List<FieldWrapper> fieldsWithAnnotation = reflection.getFieldsWithAnnotation(entity, ManyToMany.class);

        for (FieldWrapper field : fieldsWithAnnotation) {
            try {
                List<Object> dependencies = (List<Object>) field.getField();

                for (int i = 0; i < dependencies.size(); i++) {
                    if (dependencies.get(i) != null && ((EntitySuperClass) dependencies.get(i)).getExternalId() != null) {
                        Object dependency = null;
                        try
                        {
                            //dependency = getByExternalId(dependencies.get(i));
                            /*var id = idCache.get(((EntitySuperClass) dependencies.get(i)).getClass() + "_" + ((EntitySuperClass) dependencies.get(i)).getExternalId());
                            if(id == null)
                            {*/
                            var id = getIdByExternalId(dependencies.get(i).getClass().getSimpleName(), ((EntitySuperClass) dependencies.get(i)).getExternalId());
                            //}
                            if(id > 0) {
                                dependency = entityManager.getReference(dependencies.get(i).getClass(), id);
                            }
                        }
                        /*catch (DatabaseQueryException e)
                        {
                            logger.logMessage("Could not find dependency with externalID " + dependencies.get(i).getExternalId() + " in database; Using dependency from event: " + dependencies.get(i).toString());
                            e.printStackTrace();
                            dependency = dependencies.get(i);
                        }*/
                        catch (EntityNotFoundException e)
                        {
                            logger.logMessage("Could not find dependency with externalID " + ((EntitySuperClass) dependencies.get(i)).getExternalId() + " in database; Using dependency from event: " + dependencies.get(i).toString());
                            e.printStackTrace();
                            dependency = dependencies.get(i);
                        }
                        dependencies.set(i, dependency);
                    }
                }

                field.setField(dependencies);
            } catch (ReflectionUtilsException e) {
                throw new DatabasePersistException("Exception during updating Dependency with externalId.", e);
            }
        }

        return entity;

    }

    @Transactional
    private synchronized EntitySuperClass getByExternalId(EntitySuperClass dependency) throws DatabaseQueryException {
        TypedQuery<EntitySuperClass> query = entityManager.createQuery("SELECT a FROM " + dependency.getClass().getSimpleName() + " a WHERE a.externalId = :externalId", EntitySuperClass.class);
        query.setParameter("externalId", dependency.getExternalId());
        EntitySuperClass result;
        try {
            result = query.getSingleResult();
            //getEntityManager().refresh(result);
        } catch (Exception e) {
            throw new DatabaseQueryException(query, e, dependency);
        }
        return result;
    }

    @Transactional
    private synchronized long getIdByExternalId(String tableName, String externalId) {

        String sqlQuery = "SELECT id FROM " + tableName + " WHERE externalId = :externalId";

        try {
            Object idObject = entityManager
                    .createNativeQuery(sqlQuery)
                    .setParameter("externalId", externalId)
                    .getSingleResult();

            if (idObject != null) {
                Long id = ((Number) idObject).longValue(); // Convert to Long if needed
                return id;
                // Now 'id' contains the ID corresponding to the external ID
            } else {
                // Handle the case where no matching record was found
                logger.logMessage("Could not find entry in db-table " + tableName + " with externalID " + externalId);
                return -1;
            }
        } catch (NoResultException e) {
            // Handle the case where no matching record was found
            logger.logMessage("Could not find entry in db-table " + tableName + " with externalID " + externalId);
            return -1;
        }
    }

    @Transactional
    public synchronized void merge(Object entry) {

        //entityManager.getTransaction().begin();
        entityManager.merge(entry);
        //entityManager.flush();
        //entityManager.getTransaction().commit();
    }


    @Transactional
    public synchronized void persist(Object entry) throws DatabasePersistException {

        try {
            //entityManager.getTransaction().begin();
            entityManager.persist(entry);
            //entityManager.flush();
            //entityManager.getTransaction().commit();

        } catch (RollbackException e) {
            String exceptionMessage = exceptionInfo.getFullMessage(e);

            boolean isDuplicateEntry = exceptionMessage.contains("java.sql.SQLIntegrityConstraintViolationException: Duplicate entry");
            if (isDuplicateEntry) {
                throw new DatabasePersistException("Duplicate Entry Exception during persist of " + entry, e);
            } else {
                throw e;
            }
        } catch (PersistenceException e) {
            boolean isDetachedEntity = e.getMessage().contains("detached entity passed to persist");
            e.printStackTrace();
            if (isDetachedEntity) {
                throw new DatabasePersistException("Detached Entry Exception during persist of " + entry, e);
            } else {
                throw e;
            }
        } /*finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }*/
    }


}
