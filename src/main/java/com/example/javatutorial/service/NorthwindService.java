package com.example.javatutorial.service;

import com.mycompany.northwind.namespaces.northwind.Product;
import com.mycompany.northwind.services.DefaultNorthwindService;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.javatutorial.enums.NorthwindConstants.*;

@Service
public class NorthwindService {

    public List<Product> getAllProducts() {
        return new DefaultNorthwindService()
                .withServicePath(SERVICE_PATH.getValue())
                .getAllProduct()
                .select(Product.ALL_FIELDS)
                .orderBy(Product.PRODUCT_NAME.asc())
                .execute(getDestination());
    }

    public Optional<Product> getProductById(final Integer productId) {
        Product product = new DefaultNorthwindService()
                .withServicePath(SERVICE_PATH.getValue())
                .getProductByKey(productId)
                .select(Product.ALL_FIELDS)
                .execute(getDestination());
        return Optional.of(product);
    }

    private Destination getDestination() {
        return DestinationAccessor.getDestination(DESTINATION_NAME.getValue());
    }
}
