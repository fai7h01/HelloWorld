package com.example.javatutorial;

import com.mycompany.northwind.namespaces.northwind.Product;
import com.mycompany.northwind.services.DefaultNorthwindService;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NorthwindService {

    private static final String DESTINATION_NAME = "Northwind";
    private static final String SERVICE_PATH = "/V4/Northwind/Northwind.svc/";

    public List<Product> getAllProducts() {
        return new DefaultNorthwindService()
                .withServicePath(SERVICE_PATH)
                .getAllProduct()
                .select(Product.ALL_FIELDS)
                .orderBy(Product.PRODUCT_NAME.asc())
                .execute(getDestination());
    }

    public Optional<Product> getProductById(final Integer productId) {
        Product product = new DefaultNorthwindService()
                .getProductByKey(productId)
                .select(Product.ALL_FIELDS)
                .execute(getDestination());
        return Optional.of(product);
    }

    private Destination getDestination() {
        return DestinationAccessor.getDestination(DESTINATION_NAME);
    }
}
