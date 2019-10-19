package com.buynsell.buynsell.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ITEM")
@EntityListeners(AuditingEntityListener.class)
public class Item extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_seq")
    @SequenceGenerator(name = "item_id_seq", sequenceName = "ITEM_ID_SEQ", allocationSize = 1)
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

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<Image> images;

    @Column(name = "SEARCH_STRING")
    private String searchString;

    @PreUpdate
    @PrePersist
    void updateSearchString() {
        final String fullSearchString = getTitle() + getDescription() + getPrice() + getContactName() + getContactEmail() + getCategory();
        setSearchString(fullSearchString);
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void addImage(Image image) {
        if (images == null)
            images = new HashSet<>();
        images.add(image);
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
