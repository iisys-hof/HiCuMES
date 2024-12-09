package de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos.CamundaMachineOccupationDTO;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos.CamundaSubProductionStepDTO;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos.NoteDTO;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos.TimeRecordDTO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CamundaMachineOccupationDTOSerializer extends StdSerializer<CamundaMachineOccupationDTO> {

    private JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.ViewMachineOccupation.class);;

    public CamundaMachineOccupationDTOSerializer() {
        this(null);
    }

    public CamundaMachineOccupationDTOSerializer(Class<CamundaMachineOccupationDTO> t) {
        super(t);
    }

    @Override
    public void serialize(CamundaMachineOccupationDTO value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        writeNumberFieldIfNotNull("id", value.getCmoId(), gen);

        gen.writeObjectFieldStart("machineOccupation");
        writeMachineOccupation(gen, value);
        writeSubMachineOccupation(value.getSubMachineOccupations(), gen);
        gen.writeEndObject(); // end machineOccupation

        gen.writeObjectFieldStart("activeProductionStep");
            writeStringFieldIfNotNull("name", value.getProdStepName(), gen);
            gen.writeObjectFieldStart("machineType");
                writeStringFieldIfNotNull("externalId", value.getMachineType(), gen);
            gen.writeEndObject(); // end machineType
        gen.writeEndObject(); // end activeProductionStep

        gen.writeArrayFieldStart("camundaSubProductionSteps");
        writeSubProductionSteps(value, gen);
        gen.writeEndArray();

        gen.writeEndObject(); // end root object
    }

    private void writeSubMachineOccupation(List<CamundaMachineOccupationDTO> subMachineOccupations, JsonGenerator gen) throws IOException {
        if(subMachineOccupations != null)
        {
            gen.writeArrayFieldStart("subMachineOccupations");
            for (CamundaMachineOccupationDTO subMO : subMachineOccupations) {
                if (subMO != null) {
                    gen.writeStartObject();
                    writeMachineOccupation(gen, subMO);
                    gen.writeEndObject();
                }
            }
            gen.writeEndArray();; // end subMachineOccupations
        }
    }

    private void writeMachineOccupation(JsonGenerator gen, CamundaMachineOccupationDTO machineOccupationDTO) throws IOException {
        writeProductionOrder(machineOccupationDTO, gen);
        writeNumberFieldIfNotNull("id", machineOccupationDTO.getMoId(), gen);
        writeStringFieldIfNotNull("name", machineOccupationDTO.getMachineOccupationName(), gen);
        writeMachine(machineOccupationDTO, gen);
        writeStringFieldIfNotNull("plannedStartDateTime", formatDateTime(machineOccupationDTO.getStartDateTime()), gen);
        writeStringFieldIfNotNull("plannedEndDateTime", formatDateTime(machineOccupationDTO.getEndDateTime()), gen);
        writeStringFieldIfNotNull("actualStartDateTime", formatDateTime(machineOccupationDTO.getActualStartDateTime()), gen);
        writeStringFieldIfNotNull("status", String.valueOf(machineOccupationDTO.getStatus()), gen);

        gen.writeObjectFieldStart("totalProductionNumbers");
            gen.writeObjectFieldStart("acceptedMeasurement");
                writeNumberFieldIfNotNull("amount", machineOccupationDTO.getTotalAcceptedAmount(), gen);
            gen.writeEndObject(); // end measurement
        gen.writeEndObject(); // end customerOrder
    }

    private void writeProductionOrder(CamundaMachineOccupationDTO value, JsonGenerator gen) throws IOException {
        if(value.getProductionOrdnerName() == null)
        {
            gen.writeNullField("productionOrder");
        }
        else {
            gen.writeObjectFieldStart("productionOrder");
            writeStringFieldIfNotNull("name", value.getProductionOrdnerName(), gen);
            writeStringFieldIfNotNull("orderType", value.getOrderType(), gen);
            writeNumberFieldIfNotNull("sumSteps", value.getSumStep(), gen);
            gen.writeObjectFieldStart("customerOrder");
            writeStringFieldIfNotNull("name", value.getUsage(), gen);
            gen.writeEndObject(); // end customerOrder
            gen.writeObjectFieldStart("product");
            writeStringFieldIfNotNull("name", value.getProductName(), gen);
            writeStringFieldIfNotNull("elemname", value.getProductDetail(), gen);
            gen.writeEndObject(); // end product
            writeNumberFieldIfNotNull("orderAmount", value.getBamount(), gen);
            gen.writeObjectFieldStart("measurement");
            writeNumberFieldIfNotNull("amount", value.getAmount(), gen);
            writeStringFieldIfNotNull("unitString", value.getUnit(), gen);
            gen.writeEndObject(); // end measurement
            writeNotesArray(value.getNotes(), gen);
            gen.writeEndObject(); // end productionOrder
        }

    }

    private void writeMachine(CamundaMachineOccupationDTO value, JsonGenerator gen) throws IOException {
        if (value.getMachineExtId() != null) {
            gen.writeObjectFieldStart("machine");
            writeNumberFieldIfNotNull("id", value.getMachineId(), gen);
            writeStringFieldIfNotNull("externalId", value.getMachineExtId(), gen);
            writeStringFieldIfNotNull("name", value.getMachineName(), gen);
            gen.writeEndObject(); // end machine
        }
    }

    private void writeNotesArray(List<NoteDTO> notes, JsonGenerator gen) throws IOException {
        gen.writeArrayFieldStart("notes");
        if (notes != null) {
            for (NoteDTO note : notes) {
                if (note != null) {
                    gen.writeStartObject();
                    writeStringFieldIfNotNull("noteString", note.getNoteString(), gen);
                    writeStringFieldIfNotNull("userName", note.getUserName(), gen);
                    writeStringFieldIfNotNull("creationDateTime", formatDateTime(note.getCreationDateTime()), gen);
                    gen.writeEndObject(); // end note
                }
            }
        }
        gen.writeEndArray(); // end notes
    }

    private void writeSubProductionSteps(CamundaMachineOccupationDTO value, JsonGenerator gen) throws IOException {
        if (value.getCSubSteps() != null && !value.getCSubSteps().isEmpty() && value.getCSubSteps().get(0) != null) {
            for (CamundaSubProductionStepDTO step : value.getCSubSteps()) {
                if (step.getFormKey() != null) {
                    gen.writeStartObject();
                    writeStringFieldIfNotNull("formKey", step.getFormKey(), gen);
                    writeStringFieldIfNotNull("name", step.getName(), gen);
                    writeStringFieldIfNotNull("filledFormField", step.getFilledFormField(), gen);
                    writeJsonFieldIfNotNull("formField", step.getFormField(), gen);
                    gen.writeObjectFieldStart("subProductionStep");
                    writeTimeRecordsArray(step.getTimeRecords(), gen);
                    gen.writeEndObject(); // end subProductionStep
                    gen.writeEndObject(); // end CamundaSubProductionStep
                }
            }
        }
    }

    private void writeTimeRecordsArray(List<TimeRecordDTO> timeRecords, JsonGenerator gen) throws IOException {
        gen.writeArrayFieldStart("timeRecords");
        if (timeRecords != null) {
            for (TimeRecordDTO tr : timeRecords) {
                if (tr.getStartDateTime() != null) {
                    gen.writeStartObject();
                    writeStringFieldIfNotNull("startDateTime", formatDateTime(tr.getStartDateTime()), gen);
                    writeStringFieldIfNotNull("endDateTime", formatDateTime(tr.getEndDateTime()), gen);
                    writeObjectFieldIfNotNull("responseUser", tr.getUserName(), "userName", gen);
                    gen.writeEndObject(); // end TimeRecordEntry
                }
            }
        }
        gen.writeEndArray(); // end TimeRecords
    }

    private void writeStringFieldIfNotNull(String fieldName, String value, JsonGenerator gen) throws IOException {
        if (value != null) {
            gen.writeStringField(fieldName, value);
        }
    }

    private void writeNumberFieldIfNotNull(String fieldName, Number value, JsonGenerator gen) throws IOException {
        if (value != null) {
            gen.writeNumberField(fieldName, value.doubleValue());
        }
    }

    private void writeObjectFieldIfNotNull(String fieldName, String value, String subFieldName, JsonGenerator gen) throws IOException {
        if (value != null) {
            gen.writeObjectFieldStart(fieldName);
            gen.writeStringField(subFieldName, value);
            gen.writeEndObject();
        }
    }

    private void writeJsonFieldIfNotNull(String fieldName, String value, JsonGenerator gen) throws IOException {
        if (value != null) {
            try {
                gen.writeFieldName(fieldName);
                gen.writeRawValue(jsonTransformer.transformStringToJson(value).toPrettyString());
            } catch (JsonParsingUtilsException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime != null) {
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        }
        return null;
    }
}
