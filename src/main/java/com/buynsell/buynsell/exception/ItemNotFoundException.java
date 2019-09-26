package com.buynsell.buynsell.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {
    private long itemTitle;

    public ItemNotFoundException(long itemTitle) {
        super(String.format("Item %s not found",itemTitle));
        this.itemTitle = itemTitle;
    }
    public long getItemTitle() {
        return itemTitle;
    }
}
