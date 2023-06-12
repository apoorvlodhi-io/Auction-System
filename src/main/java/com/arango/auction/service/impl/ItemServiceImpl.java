package com.arango.auction.service.impl;


import com.arango.auction.constants.AuctionExceptions;
import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.model.Item;
import com.arango.auction.repository.AuctionRepository;
import com.arango.auction.repository.ItemRepository;
import com.arango.auction.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final DSLContext dslContext;
    private final AuctionRepository auctionRepository;

    @Override
    public void saveItem(Item item) {
        Item savedItem = itemRepository.insert(item);
        log.info("Item created ith id:{}",savedItem.getItemId());
    }

    @Override
    @Transactional
    public void editItem(Long itemId, Item item) {
        itemRepository.findById(item.getItemId()).orElseThrow(() -> new AuctionExceptions("Item not found"));
        itemRepository.updateItem(itemId, item.getItemName());
        log.info("Item updated successfully");
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.findById(itemId).orElseThrow(() -> new AuctionExceptions("Item not found!"));
        itemRepository.deleteById(itemId);
        log.info("Item Deleted Successfully");
    }
}



