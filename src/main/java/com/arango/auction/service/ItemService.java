package com.arango.auction.service;


import com.arango.auction.model.Item;

import java.util.List;

public interface ItemService {
    Item saveItem(Item item);

    List<Item> findAll();

    Object deleteItem(Long itemId);

    Object editItem(Long itemId, Item item);
}
