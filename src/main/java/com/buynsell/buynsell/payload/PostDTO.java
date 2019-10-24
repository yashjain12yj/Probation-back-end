package com.buynsell.buynsell.payload;

import com.buynsell.buynsell.model.Image;
import com.buynsell.buynsell.model.User;

import java.util.HashSet;
import java.util.Set;

public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String contactName;
    private String contactEmail;
    private String category;
    private User user;
    private Set<ImageDTO> images;
    private String createdAt;
    private boolean isAvailable;

    public PostDTO(){
        this.images = new HashSet<>();
    }

    public void addImage(Image image){
        images.add(new ImageDTO(image.getId(), image.getData()));
    }

    public Set<ImageDTO> getImages() {
        return images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
