package com.example.javatutorial;

import com.sap.cloud.security.xsuaa.token.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "")
public class MainController {

    private final DestinationService destinationService;

    public MainController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping(path = "")
    public ResponseEntity<String> readAll(@AuthenticationPrincipal Token token) {
        if (!token.getAuthorities().contains(new SimpleGrantedAuthority("Display"))) {
            throw new NotAuthorizedException("This operation requires \"Display\" scope");
        }

        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

    @GetMapping("/hello-world-two")
    public ResponseEntity<String> helloWorldTwo() {
        var response = destinationService.callHelloWorldService();
        return ResponseEntity.ok(response);
    }
}
