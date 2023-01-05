package com.feliiks.gardons.feliiks.gardons.exceptions;

import com.feliiks.gardons.exceptions.TokenValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenValidationExceptionTest {

    private final static String message = "An error occurred";

    @Test
    void getCause() {
        Exception cause = new Exception("An error occurred in a galaxy far far away...");
        TokenValidationException exception = new TokenValidationException(message, cause);

        try {
            throw exception;
        } catch (TokenValidationException e) {
            Assertions.assertEquals(exception, e);
            Assertions.assertEquals(cause, e.getCause());
        }
    }
}
