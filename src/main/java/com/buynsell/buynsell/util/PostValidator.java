package com.buynsell.buynsell.util;

import com.buynsell.buynsell.payload.CreatePostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PostValidator {
    public ResponseEntity validateCreatePost(CreatePostDTO createPostDTO) {
        String title = createPostDTO.getTitle();
        if (title == null || title.trim().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title required");
        } else if (title.trim().length() < 5 || title.trim().length() > 50) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title length must be 5 to 50 characters long");
        }

        String description = createPostDTO.getDescription();
        if (description == null || description.trim().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Description required");
        } else if (description.trim().length() < 15 || description.trim().length() > 500) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Description must be 15 to 500 characters long");
        }

        String category = createPostDTO.getCategory();
        if (category == null || category.trim().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category required");
        }

        String price = createPostDTO.getPrice();
        if (price == null || price.trim().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price required");
        }
        try {
            double dPrice = Double.parseDouble(price);
            if (dPrice < 0 || dPrice > 100000000){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price must be in limit");
            }
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price must be a number");
        }

        String contactName = createPostDTO.getContactName();
        if (contactName == null || contactName.trim().length() == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contact name required");
        }else if (contactName.trim().length() < 3 || contactName.trim().length() > 20){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price must be 3 to 20 characters long");
        }
        for (char c : contactName.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contact Name should not contain any digit or special character");
            }
        }

        String contactEmail = createPostDTO.getContactEmail();
        if(contactEmail == null || contactEmail.trim().length() == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contact Email required");
        } else if (contactEmail.trim().length() < 5 || contactEmail.trim().length() > 40){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contact email must be 5 to 50 characters long");
        }
        Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        Matcher regMatcher = regexPattern.matcher(contactEmail);
        if (!regMatcher.matches())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Email");

        MultipartFile[] images = createPostDTO.getImages();
        if (images == null || images.length == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image required");
        }
        for (MultipartFile image : images){
            if(image.getSize() == 0 || image.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(image.getName() + " is not readable");
            }else if (!image.getContentType().contains("image")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(image.getOriginalFilename() + " is not a image. Image required");
            } else if (image.getSize() > 1000000){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(image.getOriginalFilename() + ": Image must be less than 1 MB");
            }
        }
        return null;
    }
}
