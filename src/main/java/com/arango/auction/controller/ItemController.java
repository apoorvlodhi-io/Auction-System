package com.arango.auction.controller;

import com.arango.auction.model.Item;
import com.arango.auction.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired
    ItemService itemService;
    @PostMapping(value = ("/add"))
    public Item createItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @PostMapping(value = ("/delete/{id}"))
    public ResponseEntity deleteItem(@PathVariable(value = "id")String itemId) {
        return new ResponseEntity(itemService.deleteItem(itemId), HttpStatus.OK);
    }

    @PostMapping(value = ("/edit/{id}"))
    public ResponseEntity editItem(@PathVariable(value = "id") String itemId,@RequestBody Item item) {
        return new ResponseEntity(itemService.editItem(itemId,item), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public List<Item> getAllItems() {
        return itemService.findAll();

    }
}
