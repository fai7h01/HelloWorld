package com.example.javatutorial.controller;

import com.example.javatutorial.exception.NotAuthorizedException;
import com.example.javatutorial.service.DestinationService;
import com.sap.cloud.security.xsuaa.token.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {

    private final DestinationService destinationService;

    public MainController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/greetings")
    public ResponseEntity<String> greetings(@AuthenticationPrincipal Token token) {
        if (!token.getAuthorities().contains(new SimpleGrantedAuthority("Display"))) {
            throw new NotAuthorizedException("This operation requires \"Display\" scope");
        }

        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

    @GetMapping("/destination/greetings")
    public ResponseEntity<String> greetingsFromDestination() {
        var response = destinationService.callHelloWorldService();
        return ResponseEntity.ok(response);
    }
}
