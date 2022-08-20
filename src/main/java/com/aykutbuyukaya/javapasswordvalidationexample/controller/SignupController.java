package com.aykutbuyukaya.javapasswordvalidationexample.controller;

import com.aykutbuyukaya.javapasswordvalidationexample.exception.InvalidRequestParameterException;
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
import java.util.Objects;

@RestController
@RequestMapping("/authentication")
public class SignupController {

    private final UserRepository userRepository;

    public SignupController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest request,
                                                 @RequestParam(defaultValue = "en-us") String lang) {

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
                .message(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage())
                .timestamp(Instant.now())
                .build();

    }

    @ExceptionHandler(InvalidRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SignupResponse handleInvalidRequestParameterResponse(InvalidRequestParameterException e) {

        return SignupResponse.builder()
                .message(e.getMessage())
                .timestamp(Instant.now())
                .build();

    }

}
