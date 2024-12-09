package de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.ProductionNumbers;
import de.iisys.sysint.hicumes.core.entities.SubProductionStep;
import de.iisys.sysint.hicumes.core.entities.User;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingOutput;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

@Table(name = "HicumesSettings")
@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class HicumesSettings extends EntitySuperClass {

    boolean disableBooking = false;

}
