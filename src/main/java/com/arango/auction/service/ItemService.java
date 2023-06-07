package com.arango.auction.service;


import com.arango.auction.model.Item;

import java.util.List;

public interface ItemService {
    Item saveItem(Item item);

    List<Item> findAll();

    String deleteItem(Long itemId);

    String editItem(Long itemId, Item item);
}
