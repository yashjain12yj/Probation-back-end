package com.buynsell.buynsell.util;

import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.payload.EditItemDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Component
public class PostValidator {

    public Optional<String> validateTitle(String title){
        if (title == null || title.trim().length() == 0)
            return Optional.of("Title required");
        else if (title.trim().length() < 5 || title.trim().length() > 50)
            return Optional.of("Title length must be 5 to 50 characters long");
        return Optional.empty();
    }

    public Optional<String> validateDescription(String description){
        if (description == null || description.trim().length() == 0)
            return Optional.of("Description required");
        else if (description.trim().length() < 15 || description.trim().length() > 3000)
            return Optional.of("Description must be 15 to 3000 characters long");
        return Optional.empty();
    }

    public Optional<String> validateCategory(String category){
        if (category == null || category.trim().equals(""))
            return Optional.of("Category required");
        return Optional.empty();
    }

    public Optional<String> validatePrice(String price){
        if (price == null || price.trim().length() == 0)
            return Optional.of("Price required");
        try {
            double dPrice = Double.parseDouble(price);
            if (dPrice < 0 || dPrice > 100000000)
                return Optional.of("Price must be in limit");
        } catch (NumberFormatException ex) {
            return Optional.of("Price must be a number");
        }
        return Optional.empty();
    }
    public Optional<String> validateImagesForEdit(MultipartFile[] images){
        if (images == null) return Optional.empty();
        for (MultipartFile image : images){
            if(image.getSize() == 0 || image.isEmpty())
                return Optional.of(image.getName() + " is not readable");
            else if (!image.getContentType().contains("image"))
                return Optional.of(image.getOriginalFilename() + " is not a image. Image required");
            else if (image.getSize() > 1000000)
                return Optional.of(image.getOriginalFilename() + ": Image must be less than 1 MB");
        }
        return Optional.empty();
    }

    public Optional<String> validateImages(MultipartFile[] images){
        if (images == null || images.length == 0)
            return Optional.of("Image required");
        for (MultipartFile image : images){
            if(image.getSize() == 0 || image.isEmpty())
                return Optional.of(image.getName() + " is not readable");
            else if (!image.getContentType().contains("image"))
                return Optional.of(image.getOriginalFilename() + " is not a image. Image required");
            else if (image.getSize() > 1000000)
                return Optional.of(image.getOriginalFilename() + ": Image must be less than 1 MB");
        }
        return Optional.empty();
    }

    public Optional<String> validateCreatePost(CreatePostDTO createPostDTO) {
        Optional<String> re = validateTitle(createPostDTO.getTitle());
        if (re.isPresent()) return re;

        re = validateDescription(createPostDTO.getDescription());
        if (re.isPresent()) return re;

        re = validateCategory(createPostDTO.getCategory());
        if (re.isPresent()) return re;

        re = validatePrice(createPostDTO.getPrice());
        if (re.isPresent()) return re;

        re = validateImages(createPostDTO.getImages());
        if (re.isPresent()) return re;

        return Optional.empty();
    }

    public Optional<String> validateEditPost(EditItemDTO editItemDTO){
        Optional<String> re = validateTitle(editItemDTO.getTitle());
        if (re.isPresent()) return re;

        re = validateDescription(editItemDTO.getDescription());
        if (re.isPresent()) return re;

        re = validateCategory(editItemDTO.getCategory());
        if (re.isPresent()) return re;

        re = validatePrice(editItemDTO.getPrice());
        if (re.isPresent()) return re;

        re = validateImagesForEdit(editItemDTO.getImages());
        if (re.isPresent()) return re;

        return Optional.empty();
    }
}
