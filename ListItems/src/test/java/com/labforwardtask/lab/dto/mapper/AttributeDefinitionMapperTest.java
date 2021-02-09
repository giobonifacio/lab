package com.labforwardtask.lab.dto.mapper;

import com.labforwardtask.lab.dto.AttributeDefinitionDTO;
import com.labforwardtask.lab.model.AttributeDefinition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.labforwardtask.lab.util.AttributeDefinitionTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AttributeDefinitionMapperTest {

    @InjectMocks
    private AttributeDefinitionMapper attributeDefinitionMapper;

    @Test
    void from() {
        AttributeDefinitionDTO dto = createDefaultAttributeDefinitionDTO();

        AttributeDefinition attributeDefinition = attributeDefinitionMapper.from(dto);

        assertThat(attributeDefinition).hasNoNullFieldsOrPropertiesExcept(ID);
        assertThat(attributeDefinition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(attributeDefinition.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(attributeDefinition.getRequired()).isEqualTo(TRUE);
        assertThat(attributeDefinition.getType()).isEqualTo(STRING);
        assertThat(attributeDefinition.getShouldCheckType()).isEqualTo(FALSE);
    }

    @Test
    void fromNullValues() {
        AttributeDefinition attributeDefinition = attributeDefinitionMapper.from(new AttributeDefinitionDTO());

        assertThat(attributeDefinition).hasAllNullFieldsOrProperties();
    }

    @Test
    void fromList() {
        List<AttributeDefinitionDTO> dtoList = new ArrayList<>();
        dtoList.add(createDefaultAttributeDefinitionDTO());
        dtoList.add(new AttributeDefinitionDTO());

        List<AttributeDefinition> attributeDefinitionList = attributeDefinitionMapper.from(dtoList);
        assertThat(attributeDefinitionList)
            .hasSize(2)
            .extracting(AttributeDefinition::getKey).contains(null, DEFAULT_KEY);;
    }

    @Test
    void to() {
        AttributeDefinition attributeDefinition = createDefaultAttributeDefinition();

        AttributeDefinitionDTO dto = attributeDefinitionMapper.to(attributeDefinition);

        assertThat(dto.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(dto.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(dto.getRequired()).isEqualTo(TRUE);
        assertThat(dto.getType()).isEqualTo(STRING);
        assertThat(dto.getShouldCheckType()).isEqualTo(FALSE);
    }

    @Test
    void toNullValues() {
        AttributeDefinitionDTO dto = attributeDefinitionMapper.to(new AttributeDefinition());

        assertThat(dto).hasAllNullFieldsOrProperties();
    }

    @Test
    void toList() {
        List<AttributeDefinition> list = new ArrayList<>();
        list.add(createDefaultAttributeDefinition());
        list.add(new AttributeDefinition());

        List<AttributeDefinitionDTO> dtoList = attributeDefinitionMapper.to(list);
        assertThat(dtoList)
            .hasSize(2)
            .extracting(AttributeDefinitionDTO::getKey).contains(null, DEFAULT_KEY);
    }


}