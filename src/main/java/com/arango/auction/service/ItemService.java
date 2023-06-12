package com.arango.auction.service;

import com.arango.auction.model.Item;
import java.util.List;
public interface ItemService {
    void saveItem(Item item);
    List<Item> findAll();
    void deleteItem(Long itemId);
    void editItem(Long itemId, Item item);
}
