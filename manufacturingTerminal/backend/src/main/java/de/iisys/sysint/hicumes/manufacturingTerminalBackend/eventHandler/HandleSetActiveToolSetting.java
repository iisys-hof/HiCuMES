package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventSetActiveToolSettings;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;

public class HandleSetActiveToolSetting implements ISubscribeEvent {

    @Subscribe
    public void handle(EventSetActiveToolSettings eventSetActiveToolSettings) throws UtilsBaseException, BusinessException {

        var camundaMachineOccupationId = eventSetActiveToolSettings.getCamundaMachineOccupationId();
        var toolSettingIds = eventSetActiveToolSettings.getToolSettingIds();
        var machineOccupation = persistService.getCamundaMachineOccupationById(camundaMachineOccupationId);
        for (int toolSettingId : toolSettingIds) {
            var toolSetting = persistService.getToolSettingById(toolSettingId);

            machineOccupation.getMachineOccupation().activeToolSettingsAdd(toolSetting);
        }
        persistService.getPersistenceManager().persist(machineOccupation);
    }
}
