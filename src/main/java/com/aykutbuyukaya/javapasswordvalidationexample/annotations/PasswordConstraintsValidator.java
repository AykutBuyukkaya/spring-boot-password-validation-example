package com.aykutbuyukaya.javapasswordvalidationexample.annotations;

import com.aykutbuyukaya.javapasswordvalidationexample.exception.InvalidRequestParameterException;
import lombok.extern.slf4j.Slf4j;
import org.passay.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

@Slf4j
public class PasswordConstraintsValidator implements ConstraintValidator<Password, String> {

    private static final String PASSAY_MESSAGE_FILE_PATH = "src/main/resources/passay_%s.properties";

    private final HttpServletRequest httpServletRequest;

    public PasswordConstraintsValidator(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        PasswordValidator passwordValidator = new PasswordValidator(generateMessageResolver(),
                Arrays.asList(
                        //Length rule. Min 10 max 128 characters
                        new LengthRule(10, 128),

                        //At least one upper case letter
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),

                        //At least one lower case letter
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),

                        //At least one number
                        new CharacterRule(EnglishCharacterData.Digit, 1),

                        //At least one special characters
                        new CharacterRule(EnglishCharacterData.Special, 1),

                        new WhitespaceRule(MatchBehavior.Contains)
                )
        );

        RuleResult result = passwordValidator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        //Sending one message each time failed validation.
        constraintValidatorContext.buildConstraintViolationWithTemplate(passwordValidator.getMessages(result).stream().findFirst()
                        .orElse(constraintValidatorContext.getDefaultConstraintMessageTemplate()))
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }

    private MessageResolver generateMessageResolver() {

        String lang = Optional.ofNullable(httpServletRequest.getParameter("lang"))
                .orElseThrow(() -> new InvalidRequestParameterException("Lang parameter cannot be null!"));

        Properties props = new Properties();

        //Default passay messages in english. So we do not need to search english messages file.
        if (!lang.contains("en")) {

            try (FileInputStream fileInputStream = new FileInputStream(String.format(PASSAY_MESSAGE_FILE_PATH, lang))) {

                props.load(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
                return new PropertiesMessageResolver(props);

            } catch (IOException exception) {

                log.error("Invalid language parameter!");
                throw new InvalidRequestParameterException("Invalid language parameter!");

            }

        }

        return new PropertiesMessageResolver();
    }
}
