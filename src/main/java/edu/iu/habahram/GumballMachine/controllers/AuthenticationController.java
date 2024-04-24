package edu.iu.habahram.GumballMachine.controllers;
import edu.iu.habahram.GumballMachine.model.*;
import edu.iu.habahram.GumballMachine.repository.*;
import edu.iu.habahram.GumballMachine.security.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    CustomerRepository customerRepository;
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    TokenService tokenService, CustomerRepository customerRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.customerRepository = customerRepository;
    }


    @PostMapping("/signup")
    public void signup(@RequestBody Customer customer) {
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signin")
    public String login(@RequestBody Customer customer) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                customer.username()
                                , customer.password()));

        return tokenService.generateToken(authentication);
    }


}
