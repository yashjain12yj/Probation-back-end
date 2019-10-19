package com.buynsell.buynsell.payload;

import javax.validation.Valid;
import java.io.Serializable;

public class SearchRequestDTO implements Serializable {
    String searchQuery;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
}
