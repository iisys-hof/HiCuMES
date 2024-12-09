package de.iisys.sysint.hicumes;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.*;

public class BookingValuesDistributionCollectiveOrder extends DelegateSuperClass implements JavaDelegate {

    ObjectMapper mapper = new ObjectMapper();
    private JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.SchemaMapperDefault.class);

    private final String VAR_KEY_DISTRIBUTION_METHOD = "VAR_DISTRIBUTION_TYPE";

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        super.init(execution);

        if(!super.isDisabled)
        {

            Map<String, Object> vars = execution.getVariables();

            // 0 -> no distribution (all subs get the "raw" parent value); 1 -> distributed equally (by the number of subs); 2 -> distributed according to the portion of the total manufacturing amount
            int distributionMethod = vars.containsKey(VAR_KEY_DISTRIBUTION_METHOD) ? Integer.parseInt(vars.get(VAR_KEY_DISTRIBUTION_METHOD).toString()) : 0;

            LinkedHashMap<String, Object> machineOcc = (LinkedHashMap<String, Object>) vars.get("machineOccupation");
            ArrayList<Object> subMachineOccupations = (ArrayList<Object>) machineOcc.get("subMachineOccupations");

            if(subMachineOccupations == null)
            {
                execution.setVariable("timeDistribution", "");
                return;
            }

            // DBG
            //----------------------------------------
            /*LinkedHashMap<String, Object> temp = new LinkedHashMap<>();
            temp.put("Manufacturing_SetParameterTask", 5.6);
            temp.put("Manufacturing_ConfirmProductionTask", 98.56);
            temp.put("Manufacturing_CheckQualityTask", 322.87);
            temp.put("Manufacturing_ReworkTask", 3.97);


            machineOcc.put("timeDurations", temp);*/
            //----------------------------------------


            LinkedHashMap<String, Object> timeDurations = (LinkedHashMap<String, Object>) machineOcc.get("timeDurations");
            List<String> timeDurationKeys = Arrays.asList("Manufacturing_SetParameterTask", "Manufacturing_ConfirmProductionTask", "Manufacturing_CheckQualityTask", "Manufacturing_ReworkTask", "Manufacturing_SelectOrderTask");

            ObjectNode timeDistributionNode = mapper.createObjectNode();
            ArrayNode distributions = timeDistributionNode.putArray("timeDistribution");

            switch(distributionMethod){

                case 0: {

                    // DBG
                    //----------------------------------------
                    /*ArrayList<Object> subMachineOccupations = new ArrayList<>();
                    subMachineOccupations.add(machineOcc);
                    subMachineOccupations.add(machineOcc);*/
                    //----------------------------------------

                    for (Object subMachineOcc : subMachineOccupations) {
                        ObjectNode subMachineOccNode = mapper.createObjectNode();
                        subMachineOccNode.put("id", ((LinkedHashMap<String, Integer>) subMachineOcc).get("id").toString());
                        ObjectNode timeDurationsNode = mapper.createObjectNode();
                        for (String key : timeDurationKeys) {

                            if (timeDurations.containsKey(key)) {
                                Double value = Double.parseDouble(timeDurations.get(key).toString());
                                // Add if not existing
                                ((LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) subMachineOcc).get("timeDurations")).putIfAbsent(key, value);
                                // Put the value
                                ((LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) subMachineOcc).get("timeDurations")).put(key, value);

                                timeDurationsNode.put(key, value);
                            }
                        }
                        subMachineOccNode.set("timeDurations", timeDurationsNode);
                        distributions.add(subMachineOccNode);
                    }

                }
                break;

                case 1: {

                    // DBG
                    //----------------------------------------
                    /*ArrayList<Object> subMachineOccupations = new ArrayList<>();
                    subMachineOccupations.add(machineOcc);
                    subMachineOccupations.add(machineOcc);*/
                    //----------------------------------------

                    for (Object subMachineOcc : subMachineOccupations) {
                        ObjectNode subMachineOccNode = mapper.createObjectNode();
                        subMachineOccNode.put("id", ((LinkedHashMap<String, Integer>) subMachineOcc).get("id").toString());
                        ObjectNode timeDurationsNode = mapper.createObjectNode();

                        for (String key : timeDurationKeys) {

                            if (timeDurations.containsKey(key)) {
                                Double splitValue = Double.parseDouble(timeDurations.get(key).toString()) / subMachineOccupations.size();
                                // Add if not existing
                                ((LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) subMachineOcc).get("timeDurations")).putIfAbsent(key, splitValue);
                                // Put the value
                                ((LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) subMachineOcc).get("timeDurations")).put(key, splitValue);

                                timeDurationsNode.put(key, splitValue);
                            }
                        }
                        subMachineOccNode.set("timeDurations", timeDurationsNode);
                        distributions.add(subMachineOccNode);
                    }
                }
                break;

                case 2: {

                    // DBG -> access subMachineOccupations of the Parant instead
                    //----------------------------------------
                    /*ArrayList<Object> subMachineOccupations = new ArrayList<>();
                    subMachineOccupations.add(machineOcc);
                    subMachineOccupations.add(machineOcc);*/
                    //----------------------------------------

                    double totalAmount = 0;

                    for (Object subMachineOcc : subMachineOccupations) {

                        totalAmount += Double.parseDouble(((LinkedHashMap<Object, Object>) ((LinkedHashMap<Object, Object>) ((LinkedHashMap<String, Object>)subMachineOcc).get("productionOrder")).get("measurement")).get("amount").toString());
                    }

                    for (Object subMachineOcc : subMachineOccupations) {
                        ObjectNode subMachineOccNode = mapper.createObjectNode();
                        subMachineOccNode.put("id", ((LinkedHashMap<String, Integer>) subMachineOcc).get("id").toString());
                        ObjectNode timeDurationsNode = mapper.createObjectNode();

                        for (String key : timeDurationKeys) {

                            if (timeDurations.containsKey(key)) {

                                double amount = Double.parseDouble(((LinkedHashMap<Object, Object>) ((LinkedHashMap<Object, Object>) ((LinkedHashMap<String, Object>)subMachineOcc).get("productionOrder")).get("measurement")).get("amount").toString());
                                double percentage = amount / totalAmount;

                                Double percenttageVal = Double.parseDouble(timeDurations.get(key).toString()) * percentage;
                                // Add if not existing
                                ((LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) subMachineOcc).get("timeDurations")).putIfAbsent(key, percenttageVal);
                                // Put the value
                                ((LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) subMachineOcc).get("timeDurations")).put(key, percenttageVal);

                                timeDurationsNode.put(key, percenttageVal);
                            }
                        }
                        subMachineOccNode.set("timeDurations", timeDurationsNode);
                        distributions.add(subMachineOccNode);
                    }

                }
                break;
            }
            persistSubMachineOccupations(subMachineOccupations);
            execution.setVariable("machineOccupation", machineOcc);
            String escapedJson = mapper.writeValueAsString(timeDistributionNode);
            String reEscapedJson = escapedJson.replace("\\", "\\\\")
                    .replace("\t", "\\t")
                    .replace("\b", "\\b")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\f", "\\f")
                    .replace("\'", "\\'")      // <== not necessary
                    .replace("\"", "\\\"");
            execution.setVariable("timeDistribution", reEscapedJson);
        }
        else
        {
            System.out.println("--------------------------------------------------");
            System.out.println("++++ BookingValuesDistributionCollectiveOrder ++++");
            System.out.println("------------ Delegate is disabled!! --------------");
            System.out.println("--------------------------------------------------");
        }

    }

    public void persistSubMachineOccupations(ArrayList<Object> subMachineOccupations) {
        ObjectNode eventContentNode = mapper.createObjectNode();
        ArrayNode contentArray = eventContentNode.putArray("contents");

        for (Object subMachineOccupation : subMachineOccupations) {
            //System.out.println(singleObject);
            try {
                ObjectNode singleContent = mapper.createObjectNode();
                singleContent.put("eventClass", "MachineOccupation");
                singleContent.set("classContent", jsonTransformer.transformObjectToJson(subMachineOccupation));
                //System.out.println(newEntityEventJson);

                singleContent.put("forceUpdate", true);
                contentArray.add(singleContent);
            } catch (JsonParsingUtilsException e) {
                e.printStackTrace();
            }
        }
        JsonNode newEvent = new EventGenerator().generateEvent(Events.CoreDataTopic.NEW_MULTIPLE, eventContentNode);

        EventController.getInstance().hazelCastSendEvent(newEvent);
    }

}

