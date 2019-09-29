package com.buynsell.buynsell.controller;


import com.buynsell.buynsell.exception.ItemNotFoundException;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
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

  /*  // Get a Single Item
    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable(value = "id") Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    // Update a Item
    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable(value = "id") Long itemId,
                           @Valid @RequestBody Item itemDetails) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        item.setTitle(itemDetails.getTitle());
        item.setDescription(itemDetails.getDescription());

        Item updatedItem = itemRepository.save(item);
        return updatedItem;
    }

    // Delete(deactivate) a Item
    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        itemRepository.delete(item);

        return ResponseEntity.ok().build();
    }*/
}
