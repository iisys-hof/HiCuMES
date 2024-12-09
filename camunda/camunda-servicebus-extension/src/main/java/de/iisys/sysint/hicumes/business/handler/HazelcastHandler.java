package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.databind.JsonNode;

public interface HazelcastHandler {

    public JsonNode handleEvent(JsonNode contentNode);
}
