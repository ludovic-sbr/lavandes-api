package com.feliiks.gardons.feliiks.gardons.exceptions;

import com.feliiks.gardons.exceptions.AuthenticationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthenticationExceptionTest {

    private final static String message = "An error occurred";

    @Test
    void getCause() {
        Exception cause = new Exception("An error occurred in a galaxy far far away...");
        AuthenticationException exception = new AuthenticationException(message, cause);

        try {
            throw exception;
        } catch (AuthenticationException e) {
            Assertions.assertEquals(exception, e);
            Assertions.assertEquals(cause, e.getCause());
        }
    }
}
