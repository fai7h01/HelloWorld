package com.example.javatutorial;

import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class DestinationService {

    private static final String DESTINATION_NAME = "helloworldtwo";
    private static final String REL_URL = "";

    public String callHelloWorldService() {
        try {
            HttpDestination destination = DestinationAccessor.getLoader().tryGetDestination(DESTINATION_NAME)
                    .get().asHttp();
            HttpClient client = HttpClientAccessor.getHttpClient(destination);
            HttpGet httpGet = new HttpGet(REL_URL);
            HttpResponse httpResponse = client.execute(httpGet);
            return IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to call Hello World service", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get destination", e);
        }
    }
}
