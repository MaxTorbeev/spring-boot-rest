package com.example.rest.controller;

import com.example.rest.entity.CategoryEntity;
import com.example.rest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> store(@RequestBody CategoryEntity categoryEntity) {
        try {
            categoryService.store(categoryEntity);

            return ResponseEntity.ok("Category has been created.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
