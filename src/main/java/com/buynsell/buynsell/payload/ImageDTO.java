package com.buynsell.buynsell.payload;

public class ImageDTO {
    private Long id;
    private byte[] data;

    public ImageDTO(Long id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
