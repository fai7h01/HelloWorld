package com.example.javatutorial.service;

import com.example.javatutorial.constants.NorthwindConstants;
import com.mycompany.northwind.namespaces.northwind.Product;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.client.core.ODataClientFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.javatutorial.constants.NorthwindConstants.DESTINATION_NAME;

@Service
public class NorthwindOlingoService {

    private final ODataClient client;
    private final String serviceRoot;

    public NorthwindOlingoService() {
        this.client = ODataClientFactory.getClient();
        var baseUrl = getDestination()
                .asHttp()
                .getUri()
                .toString()
                .replaceAll("/$", "");
        this.serviceRoot = baseUrl + NorthwindConstants.SERVICE_PATH
                .replaceAll("/$", "");
    }

    public ClientEntitySet fetchEntitySet(String entitySetName, MultiValueMap<String, Object> rawParams) {
        var uriBuilder = client.newURIBuilder(serviceRoot)
                .appendEntitySetSegment(entitySetName);
        rawParams.forEach((k, v) -> uriBuilder.addCustomQueryOption(k, v.toString()));

        var req = client.getRetrieveRequestFactory().getEntitySetRequest(uriBuilder.build());
        var res = req.execute();
        checkStatus(res);

        return res.getBody();
    }

    public List<Product> getAllProducts(Map<String, String> params) {
        var uri = client.newURIBuilder(serviceRoot)
                .appendEntitySetSegment("Products");

        params.forEach(uri::addCustomQueryOption);

        var req = client.getRetrieveRequestFactory()
                .getEntitySetRequest(uri.build());

        var res = req.execute();
        checkStatus(res);

        return res.getBody()
                .getEntities()
                .stream()
                .map(this::mapToProduct)
                .collect(Collectors.toList());
    }

    public Optional<Product> getProductById(Integer productId) {
        var uri = client.newURIBuilder(serviceRoot)
                .appendEntitySetSegment("Products")
                .appendKeySegment(productId)
                .build();

        var req = client.getRetrieveRequestFactory()
                .getEntityRequest(uri);

        var res = req.execute();
        checkStatus(res);

        return Optional.ofNullable(res.getBody())
                .map(this::mapToProduct);
    }

    //Helpers

    private void checkStatus(ODataRetrieveResponse<?> res) {
        var code = res.getStatusCode();
        if (code < 200 || code >= 300) {
            throw new ResponseStatusException(
                    HttpStatus.valueOf(code),
                    "OData request failed: HTTP " + code
            );
        }
    }

    private <T> T getPrimitive(ClientEntity e, String propName, Class<T> type) {
        var val = e.getProperty(propName).getValue();
        var raw = val.asPrimitive().toValue();
        return (T) raw;
    }

    private Product mapToProduct(ClientEntity entity) {
        var product = new Product();
        product.setProductID(getPrimitive(entity, "ProductID", Number.class).intValue());
        product.setProductName(getPrimitive(entity, "ProductName", String.class));
        product.setSupplierID(getPrimitive(entity, "SupplierID", Number.class).intValue());
        product.setCategoryID(getPrimitive(entity, "CategoryID", Number.class).intValue());
        product.setQuantityPerUnit(getPrimitive(entity, "QuantityPerUnit", String.class));
        product.setUnitPrice(getPrimitive(entity, "UnitPrice", BigDecimal.class));
        product.setUnitsInStock(getPrimitive(entity, "UnitsInStock", Number.class).shortValue());
        product.setUnitsOnOrder(getPrimitive(entity, "UnitsOnOrder", Number.class).shortValue());
        product.setReorderLevel(getPrimitive(entity, "ReorderLevel", Number.class).shortValue());
        product.setDiscontinued(getPrimitive(entity, "Discontinued", Boolean.class));
        return product;
    }

    private Destination getDestination() {
        return DestinationAccessor.getDestination(DESTINATION_NAME);
    }
}
