package com.aykutbuyukaya.javapasswordvalidationexample.controller;

import com.aykutbuyukaya.javapasswordvalidationexample.model.entity.User;
import com.aykutbuyukaya.javapasswordvalidationexample.model.reponse.SignupResponse;
import com.aykutbuyukaya.javapasswordvalidationexample.model.request.SignupRequest;
import com.aykutbuyukaya.javapasswordvalidationexample.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

@RestController
@RequestMapping("/authentication")
public class SignupController {

    private final UserRepository userRepository;

    public SignupController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signUp(@RequestBody @Valid SignupRequest request) {

        SignupResponse response = SignupResponse.builder()
                .user(userRepository.save(new User(request.getEmail(), request.getPassword())))
                .message("Sign Up Complete!")
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SignupResponse handlePasswordValidationException(MethodArgumentNotValidException e) {

        //Returning password error message as a response.
        return SignupResponse.builder()
                .message(String.join(",", e.getBindingResult().getFieldError().getDefaultMessage()))
                .timestamp(Instant.now())
                .build();

    }

}
