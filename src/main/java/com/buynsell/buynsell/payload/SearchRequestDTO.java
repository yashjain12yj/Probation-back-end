package com.buynsell.buynsell.payload;

import java.io.Serializable;

public class SearchRequestDTO implements Serializable {
    private String searchQuery;

    public SearchRequestDTO(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public SearchRequestDTO() {
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
}
