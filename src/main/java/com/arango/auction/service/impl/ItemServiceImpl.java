package com.arango.auction.service.impl;


import com.arango.auction.constants.AuctionExceptions;
import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.model.Item;
import com.arango.auction.repository.AuctionRepository;
import com.arango.auction.repository.ItemRepository;
import com.arango.auction.service.ItemService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DSLContext dslContext;

    @Autowired
    AuctionRepository auctionRepository;

    public Item saveItem(Item item) {
        return dslContext.transactionResult(() -> itemRepository.insert(item));
    }

    @Override
    public String editItem(Long itemId, Item item) {
        itemRepository.findById(item.getItemId()).orElseThrow(() -> new AuctionExceptions("Item not found"));
        dslContext.transaction(() -> itemRepository.updateItem(itemId, item.getItemName()));
        return "Item updated successfully";
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public String deleteItem(Long itemId) {
        itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found!"));
        dslContext.transaction(() -> itemRepository.deleteById(itemId));
        return "Item Deleted Successfully";
    }
}



