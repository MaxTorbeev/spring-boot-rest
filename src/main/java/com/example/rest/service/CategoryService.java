package com.example.rest.service;

import com.example.rest.entity.CategoryEntity;
import com.example.rest.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public void store(CategoryEntity categoryEntity) {
        categoryRepo.save(categoryEntity);
    }
}
