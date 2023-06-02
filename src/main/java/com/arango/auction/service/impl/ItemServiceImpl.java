package com.arango.auction.service.impl;


import com.arango.auction.constants.AuctionExceptions;
import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.model.Auction;
import com.arango.auction.model.Item;
import com.arango.auction.repository.AuctionRepository;
import com.arango.auction.repository.ItemRepository;
import com.arango.auction.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    AuctionRepository auctionRepository;
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Object editItem(String itemId, Item item) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if(optionalItem.isPresent()){
            item.setItemId(itemId);
            return itemRepository.save(item);
        }
        else{
            throw new AuctionExceptions("Item not found");
        }
    }

    @Override
    public List<Item> findAll() {
        return (List<Item>) itemRepository.findAll();
    }

    @Override
    public Object deleteItem(String itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if(optionalItem.isPresent()){
            itemRepository.deleteById(itemId);
            return "Item Deleted Successfully";
        }else {
            return "Item not found!";
        }
    }


}



