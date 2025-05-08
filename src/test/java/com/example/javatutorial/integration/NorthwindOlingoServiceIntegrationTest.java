package com.example.javatutorial.integration;

import com.example.javatutorial.service.NorthwindOlingoService;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.LinkedMultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(initializers = DestinationInitializer.class)
public class NorthwindOlingoServiceIntegrationTest {

    @Autowired
    NorthwindOlingoService service;

    private MockedStatic<DestinationAccessor> destAccessorMock;

    @Test
    void fetchEntitySet_shouldReturnSomeProducts() {
        ReflectionTestUtils.setField(
                service,
                "serviceRoot",
                "https://services.odata.org/V4/Northwind/Northwind.svc"
        );
        var params = new LinkedMultiValueMap<String, Object>();
        params.add("top","5");
        ClientEntitySet set = service.fetchEntitySet("Products", params);
        assertThat(set.getEntities()).hasSizeGreaterThan(0);
        assertThat(set.getEntities().get(0).getProperty("ProductName").getValue().toString()).isEqualTo("Chai");
    }
}
