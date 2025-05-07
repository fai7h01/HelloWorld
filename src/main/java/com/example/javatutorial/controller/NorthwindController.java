package com.example.javatutorial.controller;

import com.example.javatutorial.dto.ResponseWrapper;
import com.example.javatutorial.service.NorthwindOlingoService;
import com.example.javatutorial.service.NorthwindService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/northwind")
public class NorthwindController {

    private final NorthwindService northwindService;
    private final NorthwindOlingoService northwindOlingoService;

    public NorthwindController(NorthwindService northwindService, NorthwindOlingoService northwindOlingoService) {
        this.northwindService = northwindService;
        this.northwindOlingoService = northwindOlingoService;
    }

    @GetMapping(value = "/products", produces = "application/json")
    public ResponseEntity<ResponseWrapper> getProducts() {
        var products = northwindService.getAllProducts();
        return buildSuccessResponse(products);
    }

    @GetMapping(value = "/products/{productId}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> getProductById(@PathVariable Integer productId) {
        var product = northwindService.getProductById(productId);
        return buildSuccessResponse(product);
    }

    @GetMapping(value = "/olingo/products", produces = "application/json")
    public ResponseEntity<ResponseWrapper> getOlingoProducts(
            @RequestParam Map<String, String> requestParams) {
        return buildSuccessResponse(northwindOlingoService.getAllProducts(requestParams));
    }

    @GetMapping(value = "/{entity}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> getEntitySet(@PathVariable("entity") String entity,
                                                        @RequestParam(required = false) MultiValueMap<String, Object> requestParams) {
        var params = (requestParams != null) ? requestParams : new LinkedMultiValueMap<String, Object>();
        return buildSuccessResponse(northwindOlingoService.fetchEntitySet(entity, params));
    }

    private ResponseEntity<ResponseWrapper> buildSuccessResponse(Object data) {
        return ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message("Operation successful")
                .data(data)
                .build());
    }
}
