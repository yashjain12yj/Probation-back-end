package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/private/item")
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/")
    public String getValue(){
        Item item = new Item();
        item.setTitle("Car");
        item.setDescription("2015 Model");
        item.setActive(true);
        item.setPrice(220000.00);
        itemRepository.insert(item);
        return "Item created";
    }

    // Get All Item
    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Create a new Item
    @PostMapping("/items")
    public Item createItem(@Valid @RequestBody Item item) {
        return itemRepository.insert(item);
    }
}
