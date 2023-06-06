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
        Long itemId = dslContext.transactionResult(()-> itemRepository.insert(item));
        item.setItemId(itemId);
        return item;
    }

    @Override
    public Item editItem(Long itemId,Item item) {
        Optional<Item> optionalItem = itemRepository.findById(item.getItemId());
        if(optionalItem.isPresent()){
            dslContext.transaction(() -> itemRepository.updateItem(itemId, item.getItemName()));
            item.setItemId(itemId);
            return item;
        }
        else{
            throw new AuctionExceptions("Item not found");
        }
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Object deleteItem(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if(optionalItem.isPresent()){
            dslContext.transaction(() -> itemRepository.deleteById(itemId));
            return "Item Deleted Successfully";
        }else {
            return "Item not found!";
        }
    }


}



