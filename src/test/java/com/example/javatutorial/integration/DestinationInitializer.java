package com.example.javatutorial.integration;

import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import org.mockito.MockedStatic;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URI;

import static com.example.javatutorial.constants.NorthwindConstants.DESTINATION_NAME;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class DestinationInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        Destination fakeDest = mock(Destination.class);

        HttpDestination fakeHttp = mock(HttpDestination.class);
        when(fakeDest.asHttp()).thenReturn(fakeHttp);
        when(fakeHttp.getUri())
                .thenReturn(
                        URI.create("https://services.odata.org/V4/Northwind/Northwind.svc")
                );

        MockedStatic<DestinationAccessor> destAccessorMock = mockStatic(DestinationAccessor.class);
        destAccessorMock
                .when(() -> DestinationAccessor.getDestination(DESTINATION_NAME))
                .thenReturn(fakeDest);
    }
}
