package com.arango.auction.service;


import com.arango.auction.model.Item;

import java.util.List;

public interface ItemService {
    Item saveItem(Item item);

    List<Item> findAll();

    Object deleteItem(String itemId);

    Object editItem(String itemId, Item item);
}
