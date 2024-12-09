package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.CD_OverheadCostCenter;
import de.iisys.sysint.hicumes.core.entities.OverheadCost;
import de.iisys.sysint.hicumes.core.entities.User;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventOverheadCosts;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;

public class HandleOverheadCosts implements ISubscribeEvent {

    @Subscribe
    public void handle(EventOverheadCosts eventOverheadCosts) throws UtilsBaseException, BusinessException {

        int overHeadCostsId = eventOverheadCosts.getOverHeadCostsId();
        String userName = eventOverheadCosts.getUserName();
        String costCenterExtId = eventOverheadCosts.getCostCenterExtId();
        String noteString = eventOverheadCosts.getNote();

        OverheadCost overheadCost = null;
        //Create a new entry, because there is none yet
        if(overHeadCostsId == -1)
        {
           overheadCost = startOverheadCost(userName, costCenterExtId, noteString);
        }
        //There is already a costentry, that has to be stopped
        else if(eventOverheadCosts.isEndCost())
        {
            overheadCost = stopOverheadCost(overHeadCostsId, userName);
        }
        else
        {
            overheadCost = editOverheadCost(overHeadCostsId, noteString);
        }
        persistService.getPersistenceManager().merge(overheadCost);
/*
        SSESessionManager sessionManager = SSESessionManager.getInstance();
        //sessionManager.sendEvent(Long.toString(machineOccupation.getId()), eventResponseActiveTask);

        if(camundaMachineOccupation.isSubMachineOccupation())
        {
            var parentCMO = persistService.getCamundaMachineOccupationByMachineOccupationId(camundaMachineOccupation.getMachineOccupation().getParentMachineOccupation().getId());
            sessionManager.broadcast(new EventBackgroundProcessingDone(eventAddNote,parentCMO));
        }
        else
        {
            sessionManager.broadcast(new EventBackgroundProcessingDone(eventAddNote, camundaMachineOccupation));
        }*/
    }

    private OverheadCost startOverheadCost(String userName, String costCenterExtId, String noteString) throws DatabaseQueryException {
        User user = persistService.getUserByName(userName);
        CD_OverheadCostCenter costCenter = persistService.getPersistenceManager().getByExternalIdString(CD_OverheadCostCenter.class, costCenterExtId);
        //create overheadCost and start timerecord
        return new OverheadCost(user, costCenter, noteString);
    }

    private OverheadCost stopOverheadCost(int overHeadCostsId, String userName) throws DatabaseQueryException {
        var overheadCost = persistService.getPersistenceManager().getById(OverheadCost.class, overHeadCostsId);
        overheadCost.stopTimeRecord();
        var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.START_PROCESS,  overheadCost.toCamundaStartProcess(userName));
        hazelServer.sendEvent(event);
        return overheadCost;
    }

    private OverheadCost editOverheadCost(int overHeadCostsId, String noteString) throws DatabaseQueryException {
        var overheadCost = persistService.getPersistenceManager().getById(OverheadCost.class, overHeadCostsId);
        overheadCost.setNote(noteString);
        return overheadCost;
    }

}
