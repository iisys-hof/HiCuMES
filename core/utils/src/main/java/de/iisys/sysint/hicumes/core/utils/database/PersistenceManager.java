package de.iisys.sysint.hicumes.core.utils.database;

import de.iisys.sysint.hicumes.core.entities.*;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.utils.exceptions.ExceptionInfo;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.exceptions.reflection.ReflectionUtilsException;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.core.utils.reflection.FieldWrapper;
import de.iisys.sysint.hicumes.core.utils.reflection.Reflection;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.annotations.QueryHints;

import javax.crypto.Mac;
import javax.ejb.AccessTimeout;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.persistence.*;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
@AccessTimeout(value=60000)
public class PersistenceManager {

    @PersistenceContext(unitName = "HicumesMySQLDatasource")
    EntityManager entityManager;

    private EntityManagerFactory emFactory;

    private final Logger logger = new Logger(this.getClass().getName());


    private ExceptionInfo exceptionInfo = new ExceptionInfo();

    private Reflection reflection = new Reflection();
    //EntityManager entityManager;

    //private static PersistenceManager INSTANCE;

    public PersistenceManager() {
        logger.logMessage("New PersistenceManager");
        //emFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        //entityManager = emFactory.createEntityManager();
    }

    public PersistenceManager(String persistenceUnitName) {
        logger.logMessage("New EntityManagerFactory with name: " + persistenceUnitName);
        //emFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        //entityManager = emFactory.createEntityManager();
    }

    /*public static PersistenceManager getInstance(String persistenceUnitName) {
        if (INSTANCE == null) {
            INSTANCE = new PersistenceManager(persistenceUnitName);
        }

        return INSTANCE;
    }*/

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Lock(LockType.READ)
    public synchronized <T> Stream<T> getAllByClass(Class<T> dependency) {
        return (Stream<T>) getAllByClassName(dependency.getSimpleName());
    }

    @Lock(LockType.READ)
    public synchronized <T> T getById(Class<T> dependency, long id) throws DatabaseQueryException {
        return (T) getByIdAndClassName(dependency.getSimpleName(), id);
    }

    @Lock(LockType.READ)
    public synchronized <T> T getByExternalIdString(Class<T> dependency, String externalId) throws DatabaseQueryException {
        return (T) getByClassnameAndField(dependency.getSimpleName(), externalId, "externalId");
    }

    @Lock(LockType.READ)
    public synchronized <T> T getByField(Class<T> dependency, String searchValue, String columnName) throws DatabaseQueryException {
        var typedQuery = getEntityManager().createQuery("SELECT a FROM " + dependency.getSimpleName() + " a WHERE a." + columnName + " = :id", dependency).setHint("org.hibernate.cacheable", true);
        typedQuery.setParameter("id", searchValue);
        try {
            var result = typedQuery.getSingleResult();
            //getEntityManager().refresh(result);
            return result;
        } catch (Exception e) {
            throw new DatabaseQueryException(typedQuery, e, null);
        }
    }

    @Lock(LockType.READ)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public synchronized Object getByClassnameAndField(String classname, String searchValue, String columnName) throws DatabaseQueryException {
        if(classname.equals("MachineOccupation"))
        {
            var typedQuery = getEntityManager().createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.parentMachineOccupation LEFT JOIN FETCH m.productionOrder  LEFT JOIN FETCH m.tool LEFT JOIN FETCH m.machine LEFT JOIN FETCH m.productionSteps WHERE m." + columnName + " = :id")
                    .setParameter("id", searchValue).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).setHint("org.hibernate.cacheable", true);
            /*var typedQuery = getEntityManager().createQuery("SELECT a FROM " + classname + " a WHERE a." + columnName + " = :id");
            typedQuery.setParameter("id", searchValue);*/
            try {
                var result = typedQuery.getSingleResult();

                result = getEntityManager().createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.subMachineOccupations WHERE m in :machineOccupations")
                        .setParameter("machineOccupations", result)
                        .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getSingleResult();

                result = getEntityManager().createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.subProductionSteps WHERE m in :machineOccupations")
                        .setParameter("machineOccupations", result)
                        .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getSingleResult();
                result = getEntityManager().createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.activeToolSettings WHERE m in :machineOccupations")
                        .setParameter("machineOccupations", result)
                        .setHint("org.hibernate.cacheable", true).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getSingleResult();

                //getEntityManager().refresh(result);
                initSubListsMachineOccupation((MachineOccupation) result);
                return result;
            } catch (Exception e) {
                throw new DatabaseQueryException(typedQuery, e, null);
            }
        }
        else
        {
            var typedQuery = getEntityManager().createQuery("SELECT a FROM " + classname + " a WHERE a." + columnName + " = :id");
            typedQuery.setParameter("id", searchValue);
            typedQuery.setHint("org.hibernate.cacheable", true);
            try {
                var result = typedQuery.getSingleResult();
                //getEntityManager().refresh(result);
                initSubLists(result);
                return result;
            } catch (Exception e) {
                throw new DatabaseQueryException(typedQuery, e, null);
            }
        }


    }

    @Lock(LockType.READ)
    public synchronized Object getByClassnameAndFieldWithRefresh(String classname, String searchValue, String columnName) throws DatabaseQueryException {
        if(classname.equals("MachineOccupation"))
        {
            var typedQuery = getEntityManager().createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.parentMachineOccupation LEFT JOIN FETCH m.productionOrder  LEFT JOIN FETCH m.tool LEFT JOIN FETCH m.machine LEFT JOIN FETCH m.productionSteps WHERE m." + columnName + " = :id")
                    .setParameter("id", searchValue).setHint(QueryHints.PASS_DISTINCT_THROUGH, false);
            /*var typedQuery = getEntityManager().createQuery("SELECT a FROM " + classname + " a WHERE a." + columnName + " = :id");
            typedQuery.setParameter("id", searchValue);*/
            try {
                var result = typedQuery.getSingleResult();

                result = getEntityManager().createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.subMachineOccupations WHERE m in :machineOccupations")
                        .setParameter("machineOccupations", result)
                        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getSingleResult();

                result = getEntityManager().createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.subProductionSteps WHERE m in :machineOccupations")
                        .setParameter("machineOccupations", result)
                        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getSingleResult();
                result = getEntityManager().createQuery("SELECT DISTINCT m FROM MachineOccupation m LEFT JOIN FETCH m.activeToolSettings WHERE m in :machineOccupations")
                        .setParameter("machineOccupations", result)
                        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getSingleResult();

                getEntityManager().refresh(result);
                initSubListsMachineOccupation((MachineOccupation) result);
                return result;
            } catch (Exception e) {
                throw new DatabaseQueryException(typedQuery, e, null);
            }
        }
        else
        {
            var typedQuery = getEntityManager().createQuery("SELECT a FROM " + classname + " a WHERE a." + columnName + " = :id");
            typedQuery.setParameter("id", searchValue);
            try {
                var result = typedQuery.getSingleResult();
                getEntityManager().refresh(result);
                initSubLists(result);
                return result;
            } catch (Exception e) {
                throw new DatabaseQueryException(typedQuery, e, null);
            }
        }


    }

    public synchronized <T> List<T> getAllByField(Class<T> dependency, String searchValue, String columnName) {
        var typedQuery = getEntityManager().createQuery("SELECT a FROM " + dependency.getSimpleName() + " a WHERE a." + columnName + " = :id", dependency).setHint("org.hibernate.cacheable", true);
        typedQuery.setParameter("id", searchValue);
        var result = typedQuery.getResultList();
        return result;
    }


    @Lock(LockType.READ)
    public synchronized Stream<Object> getAllByClassName(String className) {
        // TODO: Parameterized queries to avoid SQL-Injection!
        var query = getEntityManager().createQuery("SELECT a FROM " + className + " a").setHint("org.hibernate.cacheable", true);
        var resultList = query.getResultList();
        return resultList.stream();
    }

    public synchronized <T> Stream<T> getAllByClassNameFilterByMachineAndToolAndProductionStep(Class<T> dependency, String machineId, String toolId, String productionStepId) {
        var typedQuery = getEntityManager().createQuery("SELECT a FROM " + dependency.getSimpleName() + " a WHERE a.machine_id = :machine_id and a.tool_id = :tool_id and a.productionStep_id = :productionStep_id");
        typedQuery.setParameter("machine_id", machineId);
        typedQuery.setParameter("tool_id", toolId);
        typedQuery.setParameter("productionStep_id", productionStepId);
        typedQuery.setHint("org.hibernate.cacheable", true);
        return typedQuery.getResultList().stream();
    }

    @Lock(LockType.READ)
    public synchronized Object getByIdAndClassName(String className, long id) throws DatabaseQueryException {
        var typedQuery = getEntityManager().createQuery("SELECT a FROM " + className + " a WHERE a.id = :id");
        typedQuery.setParameter("id", id);
        typedQuery.setHint("org.hibernate.cacheable", true);
        try {
            var result = typedQuery.getSingleResult();
            //getEntityManager().refresh(result);
            return result;
        } catch (Exception e) {
            throw new DatabaseQueryException(typedQuery, e, null);
        }
    }

    @Lock(LockType.READ)
    public synchronized Object getByIdAndClassNameWithRefresh(String className, long id) throws DatabaseQueryException {
        var typedQuery = getEntityManager().createQuery("SELECT a FROM " + className + " a WHERE a.id = :id");
        typedQuery.setParameter("id", id);
        try {
            var result = typedQuery.getSingleResult();
            getEntityManager().refresh(result);
            return result;
        } catch (Exception e) {
            throw new DatabaseQueryException(typedQuery, e, null);
        }
    }

    public synchronized void deleteColumn(String className, String columnName) throws DatabaseQueryException {
        var query = getEntityManager().createNativeQuery("ALTER TABLE " + className + "  DROP COLUMN " + columnName);
        try {
            query.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseQueryException(query, e, null);
        }
    }

    public synchronized Map<String, Map<String, String>> getAllTableNames() {
        Metamodel mm = getMetamodel();

        var classes = new HashMap<String, Map<String, String>>();

        var types = mm.getManagedTypes();
        for (ManagedType<?> managedTyp : types) {
            var currentClass = managedTyp.getJavaType();
            String className = currentClass.getSimpleName();


            var filedResults = new HashMap<String, String>();
            var fields = currentClass.getDeclaredFields();
            var fieldsSuperclass = currentClass.getSuperclass().getDeclaredFields();
            for (var field : ArrayUtils.addAll(fieldsSuperclass, fields)) {
                filedResults.put(field.getName(), field.getGenericType().getTypeName());
            }
            classes.put(className, filedResults);
        }
        return classes;
    }

    public synchronized Metamodel getMetamodel() {
        Metamodel mm = getEntityManager().getMetamodel();
        return mm;
    }

    @Transactional
    public synchronized boolean persistEntitySuperClass(EntitySuperClass entry) throws DatabasePersistException {
        return this.persistEntitySuperClass(entry, false);
    }

    @Transactional
    public synchronized boolean persistEntitySuperClass(EntitySuperClass entry, boolean forceUpdate) throws DatabasePersistException {
        try
        {
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
            entry = updateDependenciesManyToOne(entry);
            entry = updateDependenciesManyToMany(entry);
            entry = updateDependenciesOneToMany(entry);
            persist(entry);
            return true;
        }

    }

    @Transactional
    public synchronized void merge(Object entry) {

        var entityManager = getEntityManager();
        //entityManager.getTransaction().begin();
        entityManager.merge(entry);
        entityManager.flush();
        //entityManager.getTransaction().commit();
    }

    @Transactional
    public synchronized void remove(Object entry) {
        var entityManager = getEntityManager();
        //entityManager.getTransaction().begin();
        entityManager.remove(entry);
        //entityManager.getTransaction().commit();
    }

    public synchronized void saveOrUpdate(Object entry) {

        Session session = getEntityManager().unwrap(Session.class);
        session.saveOrUpdate(entry);

    }

    @Transactional
    public synchronized void persist(Object entry) throws DatabasePersistException {
        var entityManager = getEntityManager();

        try {
            //entityManager.getTransaction().begin();
            entityManager.persist(entry);
            entityManager.flush();
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
                            var id = getIdByExternalId(dependencies.get(i).getClass().getSimpleName(), ((EntitySuperClass) dependencies.get(i)).getExternalId());
                            dependency = entityManager.getReference(EntitySuperClass.class, id);
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
    public EntitySuperClass updateDependenciesManyToOne(EntitySuperClass entity) throws DatabasePersistException {

        List<FieldWrapper> fieldsWithAnnotation = reflection.getFieldsWithAnnotation(entity, ManyToOne.class);

        for (FieldWrapper field : fieldsWithAnnotation) {
            try {
                Object dependency = field.getField();
                if (dependency != null && ((EntitySuperClass) dependency).getExternalId() != null) {
                    Object result = null;
                    try
                    {
                        var id = getIdByExternalId(dependency.getClass().getSimpleName(), ((EntitySuperClass) dependency).getExternalId());
                        result = entityManager.getReference(dependency.getClass(), id);
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
                            var id = getIdByExternalId(dependencies.get(i).getClass().getSimpleName(), ((EntitySuperClass) dependencies.get(i)).getExternalId());
                            dependency = entityManager.getReference(dependencies.get(i).getClass(), id);
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
        TypedQuery<EntitySuperClass> query = getEntityManager().createQuery("SELECT a FROM " + dependency.getClass().getSimpleName() + " a WHERE a.externalId = :externalId", EntitySuperClass.class);
        query.setParameter("externalId", dependency.getExternalId());
        query.setHint("org.hibernate.cacheable", true);
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
                return -1;
            }
        } catch (NoResultException e) {
            // Handle the case where no matching record was found
            return -1;
        }
    }

    @Lock(LockType.READ)
    public synchronized <T> Stream<T> getAllCamundaMachineOccupationsByMachineType(Class<T> dependency, String searchValue) {
        //var typedQuery = getEntityManager().createQuery("SELECT a FROM " + dependency.getSimpleName() + " a WHERE a.machineOccupation.status" + " != 'FINISHED'" + "AND a.activeProductionStep.machineType.externalId = :machineType" , dependency);
        var typedQuery = getEntityManager().createQuery("SELECT a FROM " + dependency.getSimpleName() + " a WHERE a.activeProductionStep.machineType.externalId = :machineType" , dependency);
        typedQuery.setParameter("machineType", searchValue);
        typedQuery.setHint("org.hibernate.cacheable", true);
        var result = typedQuery.getResultList();
        return result.stream();
    }

    @Lock(LockType.READ)
    public synchronized <T> Stream<T> getAllCamundaMachineOccupationsByProductionOrder(Class<T> dependency, String searchValue) {
        var typedQuery = getEntityManager().createQuery("SELECT a FROM " + dependency.getSimpleName() + " a WHERE a.machineOccupation.productionOrder.externalId = :productionOrder" , dependency);
        typedQuery.setParameter("productionOrder", searchValue);
        typedQuery.setHint("org.hibernate.cacheable", true);
        var result = typedQuery.getResultList();
        return result.stream();
    }

    private void initSubLists(Object entity) {
        List<FieldWrapper> fieldsWithAnnotation = reflection.getFieldsWithAnnotation(entity, OneToMany.class);
        fieldsWithAnnotation.addAll(reflection.getFieldsWithAnnotation(entity, ManyToMany.class));
        for (FieldWrapper field : fieldsWithAnnotation) {
            try {
                List<Object> dependencies = (List<Object>) field.getField();
                Hibernate.initialize(dependencies);
                for (int i = 0; i < dependencies.size(); i++) {
                    if (dependencies.get(i) != null) {
                        Object dependency = dependencies.get(i);
                        //initSubLists(dependency);
                    }
                }
            } catch (ReflectionUtilsException e) {
                return;
            }
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

    public void initSubListsMachineOccupation(MachineOccupation m)
    {
        //Hibernate throws an LazyInitializationException if the sublists are not fetched explicitly
        Hibernate.initialize(m.getProductionSteps());
        Hibernate.initialize(m.getSubMachineOccupations());
        Hibernate.initialize(m.getActiveToolSettings());
        Hibernate.initialize(m.getSubProductionSteps());
        Hibernate.initialize(m.getAvailableTools());
        if(m.getTool() != null)
        {
            Hibernate.initialize(m.getTool().getToolSettings());
        }
        for (var p: m.getSubProductionSteps()) {
            Hibernate.initialize(p.getTimeRecords());
        }
        for (var t: m.getAvailableTools()) {
            Hibernate.initialize(t.getToolSettings());
        }
        for (var s: m.getSubMachineOccupations()) {
            Hibernate.initialize(s.getProductionSteps());
            Hibernate.initialize(s.getSubMachineOccupations());
            Hibernate.initialize(s.getActiveToolSettings());
            Hibernate.initialize(s.getSubProductionSteps());
            Hibernate.initialize(s.getAvailableTools());

            if(s.getTool() != null)
            {
                Hibernate.initialize(s.getTool().getToolSettings());
            }
            for (var p: s.getSubProductionSteps()) {
                Hibernate.initialize(p.getTimeRecords());
            }
            for (var t: s.getAvailableTools()) {
                Hibernate.initialize(t.getToolSettings());
            }
        }
    }
}
