package com.example.javatutorial.service;

import com.example.javatutorial.exception.BaseException;
import com.mycompany.northwind.namespaces.northwind.Product;
import com.mycompany.northwind.services.DefaultNorthwindService;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.javatutorial.constants.NorthwindConstants.*;

@Service
public class NorthwindService {

    public List<Product> getAllProducts() {
        return new DefaultNorthwindService()
                .withServicePath(SERVICE_PATH)
                .getAllProduct()
                .select(Product.ALL_FIELDS)
                .orderBy(Product.PRODUCT_NAME.asc())
                .execute(getDestination());
    }

    public Product getProductById(final Integer productId) {
        var product = new DefaultNorthwindService()
                .withServicePath(SERVICE_PATH)
                .getProductByKey(productId)
                .select(Product.ALL_FIELDS)
                .execute(getDestination());
        if (product == null) {
            throw new BaseException("Product not found", HttpStatus.NOT_FOUND);
        }
        return product;
    }

    private Destination getDestination() {
        return DestinationAccessor.getDestination(DESTINATION_NAME);
    }
}
