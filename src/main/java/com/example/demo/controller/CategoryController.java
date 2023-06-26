package com.example.demo.controller;

import com.example.demo.dto.response.ResponMessage;
import com.example.demo.model.Category;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    private ResponseEntity<?> showListCategory() {
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
        if (categoryService.existsByName(category.getName())) {
            return new ResponseEntity<>(new ResponMessage("name_existed"), HttpStatus.OK);
        }
        categoryService.save(category);
        return new ResponseEntity<>(new ResponMessage("create_success"), HttpStatus.OK);

    }

    @GetMapping("/page")
    public ResponseEntity<?> pageCategory(@PageableDefault(size = 3) Pageable pageable) {
        return new ResponseEntity<>(categoryService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailCategory(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Optional<Category> category1 = categoryService.findById(id);
        System.out.println(category1);
        if (!category1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        if (!category.getAvatar().equalsIgnoreCase(category1.get().getAvatar())) {
//            category.setId(category1.get().getId());
//        }
        if (!category.getName().equalsIgnoreCase(category1.get().getName())) {
            if (categoryService.existsByName(category.getName())) {
                return new ResponseEntity<>(new ResponMessage("name_existed"), HttpStatus.OK);
            }
        }
        if (category.getName().equalsIgnoreCase(category1.get().getName())) {
            return new ResponseEntity<>(new ResponMessage("no_change"), HttpStatus.OK);
        }
        category.setId(category1.get().getId());
        categoryService.save(category);
        return new ResponseEntity<>(new ResponMessage("update_success"), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        System.out.println(category);
        if (!category.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            categoryService.deleteById(id);
            return new ResponseEntity<>(new ResponMessage("delete_success"), HttpStatus.OK);
        }
    }
}
