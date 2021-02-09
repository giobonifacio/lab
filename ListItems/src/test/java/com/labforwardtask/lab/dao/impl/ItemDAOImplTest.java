package com.labforwardtask.lab.dao.impl;

import com.labforwardtask.lab.dto.ItemDTO;
import com.labforwardtask.lab.dto.mapper.ItemMapper;
import com.labforwardtask.lab.model.Item;
import com.labforwardtask.lab.repository.ItemRepository;
import com.labforwardtask.lab.util.ItemTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.labforwardtask.lab.util.ItemTestUtil.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemDAOImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemDAOImpl itemService;


    @Test
    void findAll() {
        Item defaultItem = createDefaultItem();
        Item itemNullValues = new Item();
        List<Item> items = asList(defaultItem, itemNullValues);

        when(itemRepository.findAll()).thenReturn(items);
        when(itemMapper.to(items)).thenReturn(asList(createDefaultItemDTO(), new ItemDTO()));

        List<ItemDTO> itemList = itemService.findAll();

        assertThat(itemList)
                .hasSize(2)
                .extracting(ItemDTO::getItemAttributes)
                .contains(createDefaultAttributesMap())
                .containsNull();
    }

    @Test
    void insert() {
        Item defaultItem = ItemTestUtil.createDefaultItem();
        ItemDTO defaultItemDTO = createDefaultItemDTO();

        when(itemRepository.insert(defaultItem)).thenReturn(defaultItem);
        when(itemMapper.from(defaultItemDTO)).thenReturn(defaultItem);
        when(itemMapper.to(defaultItem)).thenReturn(defaultItemDTO);

        ItemDTO insertedItem = itemService.insert(defaultItemDTO);

        assertThat(insertedItem.getItemAttributes()).contains(
            entry(KEY_NAME, NAME_VALUE),
            entry(KEY_SIZE, SIZE_VALUE)
        );
    }

}