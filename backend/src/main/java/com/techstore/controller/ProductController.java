package com.techstore.controller;

import com.techstore.model.Product;
import com.techstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> list(@RequestParam(required = false) String keyword,
                              @RequestParam(required = false) String category) {
        return productService.list(keyword, category);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }
}
