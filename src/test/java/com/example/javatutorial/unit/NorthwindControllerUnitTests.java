package com.example.javatutorial.unit;

import com.example.javatutorial.controller.NorthwindController;
import com.example.javatutorial.dto.ResponseWrapper;
import com.example.javatutorial.service.NorthwindOlingoService;
import com.example.javatutorial.service.NorthwindService;
import com.mycompany.northwind.namespaces.northwind.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NorthwindControllerUnitTests {

    @Mock
    NorthwindService northwindService;

    @InjectMocks
    NorthwindController northwindController;

    Product product1;
    Product product2;

    @BeforeEach
    void setUp() {
        product1 = Product.builder()
                .productID(1)
                .categoryID(1)
                .productName("Apple")
                .unitPrice(BigDecimal.valueOf(5d))
                .build();
        product2 = Product.builder()
                .productID(2)
                .categoryID(1)
                .productName("Orange")
                .unitPrice(BigDecimal.valueOf(7d))
                .build();
    }

    @Test
    void testGetProducts() {
        var products = List.of(product1, product2);
        when(northwindService.getAllProducts()).thenReturn(products);

        var response = northwindController.getProducts();

        verify(northwindService, times(1)).getAllProducts();

        assertNotEquals(null, response.getBody());
        assertEquals(200, response.getStatusCode().value());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals(products, response.getBody().getData());
    }
}
