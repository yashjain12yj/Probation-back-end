package com.buynsell.buynsell.payload;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class CreatePostDTO implements Serializable {
    String title;
    String description;
    String category;
    String price;
    String contactName;
    String contactEmail;
    MultipartFile[] images;

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }

    //    ArrayList<Long> imageIds;
//    MultipartFile[] images;

    @Override
    public String toString() {
        return "CreatePostDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactName='" + contactName + '\'' +
                '}';
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
