package com.labforwardtask.lab.dto.mapper;

import com.labforwardtask.lab.dto.ItemDTO;
import com.labforwardtask.lab.model.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.labforwardtask.lab.util.ItemTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@ExtendWith(MockitoExtension.class)
class ItemMapperTest {

    @InjectMocks
    private ItemMapper itemMapper;

    @Test
    void from() {
        ItemDTO dto = createDefaultItemDTO();

        Item item = itemMapper.from(dto);

        assertThat(item).hasNoNullFieldsOrPropertiesExcept(ID);

        assertThat(item.getItemAttributes()).contains(
                entry(KEY_NAME, NAME_VALUE),
                entry(KEY_SIZE, SIZE_VALUE)
        );
    }

    @Test
    void fromNullValues() {
        Item item = itemMapper.from(new ItemDTO());

        assertThat(item).hasAllNullFieldsOrProperties();
    }

    @Test
    void fromList() {
        List<ItemDTO> dtoList = new ArrayList<>();
        dtoList.add(createDefaultItemDTO());
        dtoList.add(new ItemDTO());

        List<Item> itemList = itemMapper.from(dtoList);
        assertThat(itemList)
                .hasSize(2)
                .extracting(Item::getItemAttributes)
                .contains(createDefaultAttributesMap())
                .containsNull();
    }

    @Test
    void to() {
        Item Item = createDefaultItem();

        ItemDTO dto = itemMapper.to(Item);

        assertThat(dto.getItemAttributes()).contains(
                entry(KEY_NAME, NAME_VALUE),
                entry(KEY_SIZE, SIZE_VALUE)
        );
    }

    @Test
    void toNullValues() {
        ItemDTO dto = itemMapper.to(new Item());

        assertThat(dto).hasAllNullFieldsOrProperties();
    }

    @Test
    void toList() {
        List<Item> list = new ArrayList<>();
        list.add(createDefaultItem());
        list.add(new Item());

        List<ItemDTO> dtoList = itemMapper.to(list);
        assertThat(dtoList)
                .hasSize(2)
                .extracting(ItemDTO::getItemAttributes)
                .contains(createDefaultAttributesMap())
                .containsNull();

    }

}