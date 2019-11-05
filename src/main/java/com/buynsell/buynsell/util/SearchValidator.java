package com.buynsell.buynsell.util;

import com.buynsell.buynsell.payload.SearchRequestDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SearchValidator {
    public Optional<String> validateSearchQuery(SearchRequestDTO searchRequestDTO){
        if (searchRequestDTO == null || searchRequestDTO.getSearchQuery() == null || searchRequestDTO.getSearchQuery().trim().equals(""))
            return Optional.of("Search query is empty");
        return Optional.empty();
    }
}
