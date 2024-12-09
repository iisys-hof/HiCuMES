package de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.mappingBackend.exceptions.mappingExceptions.ParsingMappingException;

public enum FrontendTreeNodeIcons {
    OBJECT,
    LOOP,
    STRING,
    LONG,
    LOCALDATETIME,
    LOCALTIME,
    DATE,
    TIME,
    FLOAT,
    INTEGER,
    DOUBLE,
    BOOLEAN,
    SHORT,
    LOCALDATE,
    DURATION,
    UNIT,
    QUANTITY,
    BYTE,
    UNKNOWN;

    public static FrontendTreeNodeIcons fromJavaClassString(String text) {
        if (text.equals("int")) {
            return FrontendTreeNodeIcons.INTEGER;
        }
        var splitted = text.split("\\.");
        var lastElement = splitted[splitted.length -1 ];
        var upperCase = lastElement.toUpperCase();
        return FrontendTreeNodeIcons.valueOf(upperCase);
    }

    public static FrontendTreeNodeIcons fromJsonValue(JsonNode node) throws ParsingMappingException {
        var jsonNodeType = node.getNodeType();
        switch (jsonNodeType) {
            case STRING:
                return FrontendTreeNodeIcons.STRING;
            case BOOLEAN:
                return FrontendTreeNodeIcons.BOOLEAN;
            case NUMBER:
                return FrontendTreeNodeIcons.DOUBLE;
            case MISSING:
            case POJO:
            case ARRAY:
            case BINARY:
            case OBJECT:
            case NULL:
                return FrontendTreeNodeIcons.UNKNOWN;

            default:
                throw new ParsingMappingException("Tried to get JSON types and could not find type: " + jsonNodeType, null);
        }

    }
}
