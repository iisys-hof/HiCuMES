package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.*;
import de.iisys.sysint.hicumes.core.entities.enums.EProductionStatus;
import de.iisys.sysint.hicumes.core.entities.exceptions.UnitEntityException;
import de.iisys.sysint.hicumes.core.entities.unit.Measurement;
import de.iisys.sysint.hicumes.core.entities.unit.Unit;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IEmitEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCreateSplitOrder;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

import javax.inject.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static de.iisys.sysint.hicumes.manufacturingTerminalBackend.startup.StartupController.sessionManager;

public class HandleCreateSplitOrder implements ISubscribeEvent {

    @Inject
    private IEmitEvent events;
    @Subscribe
    public void handle(EventCreateSplitOrder eventCreateSplitOrder) throws Exception {
        var mainMachineOccupation = eventCreateSplitOrder.getCamundaMachineOccupation().getMachineOccupation();
        var mainProductionOrder = mainMachineOccupation.getProductionOrder();
        var splits = eventCreateSplitOrder.getSplits();
        /*var totalSplit = 0;
        for(Double val: splits)
        {
            totalSplit+=val;
        }*/

        /*if(mainProductionOrder.getMeasurement().getAmount() - totalSplit > 0)
        {
            //Save original production order with reduced amount
            mainProductionOrder.getMeasurement().setAmount(mainProductionOrder.getMeasurement().getAmount() - totalSplit);
            persistService.getPersistenceManager().merge(mainProductionOrder);
            persistService.getPersistenceManager().merge(mainMachineOccupation);
            sessionManager.broadcast(new EventBackgroundProcessingDone(eventCreateSplitOrder, eventCreateSplitOrder.getCamundaMachineOccupation()));
        }*/
        //var splitMachineOccupations = new ArrayList<MachineOccupation>();

        for (int i = 0; i < splits.size(); ++i) {
//
//            var splitProductionOrder = new ProductionOrder(new Measurement(splits.get(i), mainProductionOrder.getMeasurement().getUnitString()), mainProductionOrder.getCustomerOrder(), mainProductionOrder.getProduct(), mainProductionOrder.getPriority(), mainProductionOrder.getDeadline(), EProductionStatus.PLANNED);
//
//            splitProductionOrder.setExternalId(mainProductionOrder.getExternalId() + i+1);
//            splitProductionOrder.setName(mainProductionOrder.getName() + " Teilauftrag_" + (i+1));
//            persistService.getPersistenceManager().persist(splitProductionOrder);
//
//            var splitMachineOccupation = new MachineOccupation(mainMachineOccupation.getProductionSteps(), mainMachineOccupation.getTool(), splitProductionOrder, mainMachineOccupation.getCamundaProcessName());
//            //TODO define naming scheme
//            splitMachineOccupation.setExternalId(mainMachineOccupation.getExternalId() + "_Teilauftrag_" + (i+1));
//            splitMachineOccupation.setName(mainMachineOccupation.getName() + " Teilauftrag_" + (i+1));
//            splitMachineOccupation.setPlannedStartDateTime(mainMachineOccupation.getPlannedStartDateTime());
//            splitMachineOccupation.setPlannedEndDateTime(mainMachineOccupation.getPlannedEndDateTime());
//            splitMachineOccupation.setStatus(mainMachineOccupation.getStatus());
//            splitMachineOccupation.setActualStartDateTime(mainMachineOccupation.getActualStartDateTime());
//            splitMachineOccupation.setActualEndDateTime(mainMachineOccupation.getActualEndDateTime());
//
//            persistService.getPersistenceManager().persist(splitMachineOccupation);
//
//            persistService.removeCamundaMachineOccupation(eventCreateSplitOrder.getCamundaMachineOccupation());
//
//            events.emit(new EventNewMachineOccupation(splitMachineOccupation));
//            //events.emit(new EventNewEntity(splitMachineOccupation));
//
//            SSESessionManager sessionManager = SSESessionManager.getInstance();
//            sessionManager.broadcast(new EventBackgroundProcessingDone(eventCreateSplitOrder, eventCreateSplitOrder.getCamundaMachineOccupation()));
//

            //var splitProductionOrder = new ProductionOrder(new Measurement(split, mainProductionOrder.getMeasurement().getUnitString()), mainProductionOrder.getCustomerOrder(), mainProductionOrder.getProduct(), mainProductionOrder.getPriority(), mainProductionOrder.getDeadline(), EProductionStatus.PLANNED);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            var datetimeNow = LocalDateTime.now().format(formatter);

            //copy (just by reference) production order, save set new names and remove id
            var splitProductionOrder = deepCopy(mainProductionOrder);
            //persistService.getPersistenceManager().getEntityManager().detach(splitProductionOrder);

            splitProductionOrder.setExternalId(mainProductionOrder.getExternalId() + "_split_" + (i+1) + datetimeNow);
            splitProductionOrder.setName(mainProductionOrder.getName() + " Teilauftrag " + (i+1));
            splitProductionOrder.setId(0);
            splitProductionOrder.getMeasurement().setAmount(splits.get(i));
            splitProductionOrder.getMeasurement().setUnit(mainProductionOrder.getMeasurement().getUnit());
            splitProductionOrder.setKeyValueMap(new HashMap<>(mainProductionOrder.getKeyValueMap()));
            splitProductionOrder.setMachineOccupations(new ArrayList<>());
            splitProductionOrder.setNotes(new ArrayList<>(mainProductionOrder.getNotes()));
            splitProductionOrder.setProduct(mainProductionOrder.getProduct());
            splitProductionOrder.setCustomerOrder(mainProductionOrder.getCustomerOrder());
            splitProductionOrder.setDeadline(mainProductionOrder.getDeadline());

            //splitProductionOrder.setCustomerOrder();
            persistService.getPersistenceManager().persist(splitProductionOrder);

            //Do the same for machineOccupation
            var splitMachineOccupation = deepCopy(mainMachineOccupation);

            //persistService.getPersistenceManager().getEntityManager().detach(splitMachineOccupation);

            splitMachineOccupation.setExternalId(mainMachineOccupation.getExternalId() + "_split_" + i+1 +  datetimeNow);
            splitMachineOccupation.setName(mainMachineOccupation.getName());
            splitMachineOccupation.setId(0);
            splitMachineOccupation.setProductionOrder(splitProductionOrder);
            //Reset production numbers
            splitMachineOccupation.setTotalProductionNumbers(new ProductionNumbers());
            //Create new collections to avoid shared reference exception
            splitMachineOccupation.setActiveToolSettings(new ArrayList<>(mainMachineOccupation.getActiveToolSettings()));
            splitMachineOccupation.setProductionSteps(new ArrayList<>(mainMachineOccupation.getProductionSteps()));
            splitMachineOccupation.setSubMachineOccupations(new ArrayList<>(mainMachineOccupation.getSubMachineOccupations()));
            splitMachineOccupation.setSubProductionSteps(new ArrayList<>(mainMachineOccupation.getSubProductionSteps()));
            splitMachineOccupation.setTimeDurations(new HashMap<>(mainMachineOccupation.getTimeDurations()));
            splitMachineOccupation.setCamundaProcessName(mainMachineOccupation.getCamundaProcessName());
            splitMachineOccupation.setMachine(mainMachineOccupation.getMachine());
            splitMachineOccupation.setTool(mainMachineOccupation.getTool());
            persistService.getPersistenceManager().persist(splitMachineOccupation);


            events.emit(new EventNewMachineOccupation(splitMachineOccupation));
            //events.emit(new EventNewEntity(splitMachineOccupation));
        }
        persistService.removeCamundaMachineOccupation(eventCreateSplitOrder.getCamundaMachineOccupation());
        sessionManager = SSESessionManager.getInstance();
        sessionManager.broadcast(new EventBackgroundProcessingDone(eventCreateSplitOrder, eventCreateSplitOrder.getCamundaMachineOccupation()));

    }

    private <T> T deepCopy(T object) throws Exception {

        Class<?> clazz = object.getClass();
        T copy = (T) clazz.getDeclaredConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            if (isPrimitiveStringEnum(field.getType())) {
                field.setAccessible(true);
                Object value = field.get(object);
                field.set(copy, value);
            }
        }

        return copy;
    }

    private boolean isPrimitiveStringEnum(Class<?> type) {
        return type.isPrimitive() ||
                type.equals(Byte.class) ||
                type.equals(Short.class) ||
                type.equals(Integer.class) ||
                type.equals(Long.class) ||
                type.equals(Float.class) ||
                type.equals(Double.class) ||
                type.equals(Boolean.class) ||
                type.equals(Character.class) ||
                type.equals(String.class) ||
                type.equals(LocalDateTime.class) ||
                type.isEnum(); // Check if it's an enum type
    }

}
