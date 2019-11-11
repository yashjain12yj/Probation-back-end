package com.buynsell.buynsell.util;

import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.payload.EditItemDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class PostValidator {

    public Optional<String> validateTitle(String value) {
        Optional<String> errorMsg = validateLabel("Title", value, 5, 50);
        return errorMsg.isPresent() ? errorMsg : empty();
    }

    public Optional<String> validateDescription(String value) {
        Optional<String> errorMsg = validateLabel("Description", value, 15, 3000);
        return errorMsg.isPresent() ? errorMsg : empty();
    }

    public Optional<String> validateCategory(String value) {
        return isEmpty(value) ? of("Category required") : empty();
    }

    private static Optional<String> validateLabel(String label, String value, int minLength, int maxLength) {
        if (isEmpty(value))
            return of(label + "  required");
        else if (value.trim().length() < minLength || value.trim().length() > maxLength)
            return of(label + " length must be " + minLength + " to " + maxLength + " characters long");
        return empty();
    }

    public Optional<String> validatePrice(String price) {
        if (price == null || price.trim().length() == 0)
            return of("Price required");
        try {
            double dPrice = Double.parseDouble(price);
            if (dPrice < 0 || dPrice > 100000000)
                return of("Price must be in limit");
        } catch (NumberFormatException ex) {
            return of("Price must be a number");
        }
        return empty();
    }

    public Optional<String> validateImagesForEdit(MultipartFile[] images) {
        if (images == null) return empty();
        Optional<String> validationMsg = validateImage(images);
        if (validationMsg.isPresent()) return validationMsg;
        return empty();
    }

    public Optional<String> validateImage(MultipartFile[] images) {
        for (MultipartFile image : images) {
            if (image.getSize() == 0 || image.isEmpty())
                return of(image.getName() + " is not readable");
            else if (!image.getContentType().contains("image"))
                return of(image.getOriginalFilename() + " is not a image. Image required");
            else if (image.getSize() > 1000000)
                return of(image.getOriginalFilename() + ": Image must be less than 1 MB");
        }
        return empty();
    }


    public Optional<String> validateImages(MultipartFile[] images) {
        if (images == null || images.length == 0)
            return of("Image required");
        Optional<String> validationMsg = validateImage(images);
        if (validationMsg.isPresent()) return validationMsg;
        return empty();
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

        return empty();
    }

    public Optional<String> validateEditPost(EditItemDTO editItemDTO) {
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

        return empty();
    }
}
