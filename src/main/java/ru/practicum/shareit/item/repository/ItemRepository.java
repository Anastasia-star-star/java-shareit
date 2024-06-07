package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOwnerId(Long ownerId);

    @Query(value = "select i from Item i " +
            "where lower(i.name) like lower(concat('%',?1,'%')) "
            + " or lower(i.description) like lower(concat('%',?1,'%'))"
            + " and i.available=true")
    List<Item> searchAvailableItems(String text);

    void deleteById(long itemId);
}