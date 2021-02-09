package com.labforwardtask.lab.dto.mapper;

import com.labforwardtask.lab.dto.AttributeDefinitionDTO;
import com.labforwardtask.lab.model.AttributeDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
public class AttributeDefinitionMapper {

    public List<AttributeDefinition> from(List<AttributeDefinitionDTO> dtos) {
        if (isNull(dtos)) {
            return new ArrayList<>();
        }

        return dtos.stream().map(this::from).collect(toList());
    }

    public AttributeDefinition from(AttributeDefinitionDTO dto) {
        AttributeDefinition attr = new AttributeDefinition();

        attr.setDescription(dto.getDescription());
        attr.setKey(dto.getKey());
        attr.setRequired(dto.getRequired());
        attr.setType(dto.getType());
        attr.setShouldCheckType(dto.getShouldCheckType());

        return attr;
    }

    public List<AttributeDefinitionDTO> to(List<AttributeDefinition> attributeDefinitions) {
        if (isNull(attributeDefinitions)) {
            return new ArrayList<>();
        }

        return attributeDefinitions.stream().map(this::to).collect(toList());
    }

    public AttributeDefinitionDTO to(AttributeDefinition attr) {
        AttributeDefinitionDTO dto = new AttributeDefinitionDTO();

        dto.setDescription(attr.getDescription());
        dto.setKey(attr.getKey());
        dto.setRequired(attr.getRequired());
        dto.setType(attr.getType());
        dto.setShouldCheckType(attr.getShouldCheckType());

        return dto;
    }

}
