package com.example.javatutorial;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/northwind")
public class NorthwindController {

    private final NorthwindService northwindService;

    public NorthwindController(NorthwindService northwindService) {
        this.northwindService = northwindService;
    }

    @GetMapping(value = "/products", produces = "application/json")
    public ResponseEntity<?> getProducts() {
        var products = northwindService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/products/{productId}", produces = "application/json")
    public ResponseEntity<?> getProductById(@PathVariable Integer productId) {
        var product = northwindService.getProductById(productId);
        return ResponseEntity.ok(product);
    }
}
