package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoOut;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOut;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.item.ItemController.X_SHARER_USER_ID;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;


    private final User user = User.builder()
            .id(1L)
            .name("username")
            .email("my@email.com")
            .build();

    private final Item item = Item.builder()
            .id(1L)
            .name("item name")
            .description("description")
            .owner(user)
            .build();


    @Test
    @SneakyThrows
    void createItemWhenItemIsValid() {
        Long userId = 0L;
        ItemDto itemDtoToCreate = ItemDto.builder()
                .description("some item description")
                .name("some item name")
                .available(true)
                .build();

        when(itemService.add(userId, itemDtoToCreate)).thenReturn(ItemMapper.toItemDtoOut(ItemMapper.toItem(itemDtoToCreate)));

        String result = mockMvc.perform(post("/items")
                        .contentType("application/json")
                        .header(X_SHARER_USER_ID, userId)
                        .content(objectMapper.writeValueAsString(itemDtoToCreate)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ItemDto resultItemDto = objectMapper.readValue(result, ItemDto.class);
        assertEquals(itemDtoToCreate.getDescription(), resultItemDto.getDescription());
        assertEquals(itemDtoToCreate.getName(), resultItemDto.getName());
        assertEquals(itemDtoToCreate.getAvailable(), resultItemDto.getAvailable());
    }

    @Test
    @SneakyThrows
    void createItemWhenItemIsNotValidShouldReturnBadRequest() {
        Long userId = 0L;
        ItemDto itemDtoToCreate = ItemDto.builder()
                .description(" ")
                .name(" ")
                .available(null)
                .build();

        when(itemService.add(userId, itemDtoToCreate)).thenReturn(ItemMapper.toItemDtoOut(ItemMapper.toItem(itemDtoToCreate)));

        mockMvc.perform(post("/items")
                        .contentType("application/json")
                        .header(X_SHARER_USER_ID, userId)
                        .content(objectMapper.writeValueAsString(itemDtoToCreate)))
                .andExpect(status().isBadRequest());

        verify(itemService, never()).add(userId, itemDtoToCreate);
    }

    @Test
    @SneakyThrows
    void updateWhenItemIsValidShouldReturnStatusIsOk() {
        Long itemId = 0L;
        Long userId = 0L;
        ItemDto itemDtoToCreate = ItemDto.builder()
                .description("some item description")
                .name("some item name")
                .available(true)
                .build();

        when(itemService.update(userId, itemId, itemDtoToCreate)).thenReturn(ItemMapper.toItemDtoOut(ItemMapper.toItem(itemDtoToCreate)));

        String result = mockMvc.perform(patch("/items/{itemId}", itemId)
                        .contentType("application/json")
                        .header(X_SHARER_USER_ID, userId)
                        .content(objectMapper.writeValueAsString(itemDtoToCreate)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ItemDto resultItemDto = objectMapper.readValue(result, ItemDto.class);
        assertEquals(itemDtoToCreate.getDescription(), resultItemDto.getDescription());
        assertEquals(itemDtoToCreate.getName(), resultItemDto.getName());
        assertEquals(itemDtoToCreate.getAvailable(), resultItemDto.getAvailable());
    }

    @Test
    @SneakyThrows
    void getShouldReturnStatusOk() {
        Long itemId = 0L;
        Long userId = 0L;
        ItemDtoOut itemDtoToCreate = ItemDtoOut.builder()
                .id(itemId)
                .description("")
                .name("")
                .available(true)
                .build();

        when(itemService.getItemById(userId, itemId)).thenReturn(itemDtoToCreate);

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/items/{itemId}", itemId)
                        .contentType("application/json")
                        .header(X_SHARER_USER_ID, userId)
                        .content(objectMapper.writeValueAsString(itemDtoToCreate)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(itemDtoToCreate), result);
    }

    @Test
    @SneakyThrows
    void getAllShouldReturnStatusOk() {
        Long userId = 0L;
        Integer from = 0;
        Integer size = 10;
        List<ItemDtoOut> itemsDtoToExpect = List.of(ItemDtoOut.builder()
                .name("some item name")
                .description("some item description")
                .available(true)
                .build());

        when(itemService.getAll(userId, from, size)).thenReturn(itemsDtoToExpect);

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/items", from, size)
                        .header(X_SHARER_USER_ID, userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(itemsDtoToExpect), result);
    }

    @Test
    @SneakyThrows
    void searchItemsShouldReturnStatusOk() {
        Long userId = 0L;
        Integer from = 0;
        Integer size = 10;
        String text = "find";
        List<ItemDtoOut> itemsDtoToExpect = List.of(ItemDtoOut.builder()
                .name("some item name")
                .description("some item description")
                .available(true)
                .build());

        when(itemService.search(userId, text, from, size)).thenReturn(itemsDtoToExpect);

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/items/search", from, size)
                        .header(X_SHARER_USER_ID, userId)
                        .param("text", text))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(itemsDtoToExpect), result);
    }


    @Test
    @SneakyThrows
    void createCommentWhenCommentIsValidShouldReturnStatusIsOk() {
        ItemDtoOut itemDtoOut = itemService.add(user.getId(), ItemMapper.toItemDto(item));
        CommentDto commentToAdd = CommentDto.builder()
                .text("some comment")
                .build();
        CommentDtoOut commentDtoOut = CommentDtoOut.builder()
                .id(1L)
                .itemId(item.getId())
                .text(commentToAdd.getText())
                .build();
        when(itemService.createComment(user.getId(), commentToAdd, item.getId())).thenReturn(commentDtoOut);

        String result = mockMvc.perform(post("/items/{itemId}/comment", item.getId())
                        .contentType("application/json")
                        .header(X_SHARER_USER_ID, user.getId())
                        .content(objectMapper.writeValueAsString(commentToAdd)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(commentDtoOut), result);
    }

    @Test
    @SneakyThrows
    void findAllItemsShouldReturnBadRequest() {
        Integer from = -1;
        Integer size = 10;

        mockMvc.perform(get("/items")
                        .param("from", String.valueOf(from))
                        .param("size", String.valueOf(size))
                        .contentType("application/json")
                        .header(X_SHARER_USER_ID, user.getId()))
                .andExpect(status().isBadRequest());

        verify(itemService, never()).getAll(user.getId(), from, size);
    }

    @Test
    @SneakyThrows
    void searchShouldReturnBadRequest() {
        Integer from = -1;
        Integer size = 10;
        String text = "item";

        mockMvc.perform(get("/items/search")
                        .param("text", text)
                        .param("from", String.valueOf(from))
                        .param("size", String.valueOf(size))
                        .contentType("application/json")
                        .header(X_SHARER_USER_ID, user.getId()))
                .andExpect(status().isBadRequest());

        verify(itemService, never()).search(user.getId(), text, from, size);
    }
}
