package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
        item.setPrice(220000.00);
        item.setAvailable(true);
        item.setContactEmail("yashjain12yj@gmail.com");
        item.setContactName("9806886930");
        itemRepository.insert(item);
        return "Item created";
    }

    // Get All Item
    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Create a new Item
    @PostMapping(value = "/items",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Item createItem(@Valid @RequestBody Item item) {
        return itemRepository.insert(item);
    }
}
