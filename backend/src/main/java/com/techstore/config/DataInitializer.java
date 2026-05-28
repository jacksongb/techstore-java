package com.techstore.config;

import com.techstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ProductService productService;

    @Override
    public void run(String... args) {
        productService.initData();
    }
}
