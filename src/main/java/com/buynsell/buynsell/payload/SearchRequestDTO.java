package com.buynsell.buynsell.payload;

import java.io.Serializable;

public class SearchRequestDTO implements Serializable {
    private String searchQuery;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
}
