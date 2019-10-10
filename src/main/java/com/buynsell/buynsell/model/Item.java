package com.buynsell.buynsell.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ITEM")
@EntityListeners(AuditingEntityListener.class)
public class Item extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Please enter price")
    private double price;

    @NotNull(message = "Please give availability")
    private boolean isAvailable;

    @NotBlank
    private String contactName;

    @NotBlank
    private String contactEmail;

    private String category;

    @ManyToOne
    User user;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    Set<Image> images;

    public Set<Image> getImages() {
        return images;
    }

    public void addImage(Image image) {
        if (images == null)
            images = new HashSet<>();
        images.add(image);
    }

    public void removeImage(Image image) {
        if (images.contains(image))
            images.remove(image);
    }

    public long getId() {
        return id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", contactName='" + contactName + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", category='" + category + '\'' +
                ", user=" + user +
                ", images=" + images +
                '}';
    }
}
