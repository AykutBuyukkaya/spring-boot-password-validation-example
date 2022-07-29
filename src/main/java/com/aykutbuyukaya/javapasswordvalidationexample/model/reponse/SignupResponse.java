package com.aykutbuyukaya.javapasswordvalidationexample.model.reponse;

import com.aykutbuyukaya.javapasswordvalidationexample.model.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupResponse {

    private String message;
    private Instant timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User user;

}
