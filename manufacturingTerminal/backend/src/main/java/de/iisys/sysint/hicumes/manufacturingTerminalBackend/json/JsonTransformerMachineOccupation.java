package de.iisys.sysint.hicumes.manufacturingTerminalBackend.json;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonTransformerMachineOccupation {

    private JsonTransformer jsonTransformerParent;
    private JsonTransformer jsonTransformerChild;

    public JsonTransformerMachineOccupation() {
        jsonTransformerChild = new JsonTransformer(JsonViews.ViewMachineOccupationChild.class);
        jsonTransformerParent = new JsonTransformer(JsonViews.ViewMachineOccupationParent.class);
    }

    public Response getJsonResponseFromStream(Stream<CamundaMachineOccupation> value) throws JsonParsingUtilsException {
        //TODO Error handling
        //Check, if the MachineOccupation is a collection order and select appropriate jsonTransformer
        var jsonStream = value.map(camundaMachineOccupation -> {
            if(camundaMachineOccupation.getMachineOccupation().isCollection())
            {
                try {
                    return jsonTransformerParent.writeValueAsString(camundaMachineOccupation);
                } catch (JsonParsingUtilsException e) {
                    e.printStackTrace();
                }
            }
            else
            {

                try {
                    return jsonTransformerChild.writeValueAsString(camundaMachineOccupation);
                } catch (JsonParsingUtilsException e) {
                    e.printStackTrace();
                }
            }
            return null;
        });
        //Build array from the stream of strings
        var json = "[" + jsonStream.collect(Collectors.joining(",")) + "]";
        return Response.ok(json).type(MediaType.APPLICATION_JSON_TYPE).build();

    }

    public Response getJsonResponse(CamundaMachineOccupation value) throws JsonParsingUtilsException {
        //Check, if the MachineOccupation is a collection order and select appropriate jsonTransformer
        if(value != null) {
            if (value.getMachineOccupation() != null && value.getMachineOccupation().isCollection()) {
                return jsonTransformerParent.getJsonResponse(value);
            } else {
                return jsonTransformerChild.getJsonResponse(value);
            }
        }
        return jsonTransformerChild.getJsonResponse("");
    }

    public JsonNode transformObjectToJson(MachineOccupation machineOccupation) throws JsonParsingUtilsException {
        if(machineOccupation.getSubMachineOccupations() != null && machineOccupation.getSubMachineOccupations().size() > 0)
        {
                return jsonTransformerParent.transformObjectToJson(machineOccupation);
        }
        else
        {
                return jsonTransformerChild.transformObjectToJson(machineOccupation);
        }
    }
}
