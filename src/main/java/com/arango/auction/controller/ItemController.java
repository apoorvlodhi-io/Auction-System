package com.arango.auction.controller;

import com.arango.auction.model.Item;
import com.arango.auction.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/v1/items")
public class ItemController {

    @Autowired
    ItemService itemService;
    @PostMapping(value = ("/add"))
    public void createItem(@RequestBody Item item) {
        itemService.saveItem(item);
    }

    @PostMapping(value = ("/delete/{id}"))
    public void deleteItem(@PathVariable(value = "id")Long itemId) {
        itemService.deleteItem(itemId);
    }

    @PostMapping(value = ("/edit/{id}"))
    public void editItem(@PathVariable(value = "id") Long itemId,@RequestBody Item item) {
       itemService.editItem(itemId,item);
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.findAll();

    }
}
