package com.aykutbuyukaya.javapasswordvalidationexample.model.request;

import com.aykutbuyukaya.javapasswordvalidationexample.annotations.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @Email
    private String email;

    @Password
    private String password;

}
