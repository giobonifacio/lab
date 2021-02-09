package com.labforwardtask.lab.util;

import com.labforwardtask.lab.dto.AttributeDefinitionDTO;
import com.labforwardtask.lab.model.AttributeDefinition;

public class AttributeDefinitionTestUtil {

    public static final String DEFAULT_DESCRIPTION = "Default description";
    public static final String ID = "id";
    public static final String DEFAULT_KEY = "Default Key";
    public static final boolean TRUE = true;
    public static final String STRING = "String";
    public static final boolean FALSE = false;

    public static AttributeDefinitionDTO createDefaultAttributeDefinitionDTO() {
        AttributeDefinitionDTO dto = new AttributeDefinitionDTO();

        dto.setDescription(DEFAULT_DESCRIPTION);
        dto.setKey(DEFAULT_KEY);
        dto.setRequired(TRUE);
        dto.setType(STRING);
        dto.setShouldCheckType(FALSE);

        return dto;
    }

    public static AttributeDefinition createDefaultAttributeDefinition() {
        AttributeDefinition attributeDefinition = new AttributeDefinition();

        attributeDefinition.setDescription(DEFAULT_DESCRIPTION);
        attributeDefinition.setKey(DEFAULT_KEY);
        attributeDefinition.setRequired(TRUE);
        attributeDefinition.setType(STRING);
        attributeDefinition.setShouldCheckType(FALSE);

        return attributeDefinition;
    }
}
