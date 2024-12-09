package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.Data;

@Data
public class EventOverheadCosts extends EventEntity {
    private int overHeadCostsId;
    private String userName;
    private String costCenterExtId;
    private String note;
    private boolean endCost;
    public EventOverheadCosts(int id, String userName, String costCenterExtId, String note, boolean endCost) {
        this.overHeadCostsId = id;
        this.userName = userName;
        this.costCenterExtId = costCenterExtId;
        this.note = note;
        this.endCost = endCost;
    }
}
