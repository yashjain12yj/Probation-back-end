package com.buynsell.buynsell.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "IMAGES")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Lob
    private byte[] data;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    Item item;

    public Long getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
