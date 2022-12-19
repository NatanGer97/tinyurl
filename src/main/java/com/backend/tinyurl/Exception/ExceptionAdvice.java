package com.backend.tinyurl.Exception;

import org.springframework.http.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExceptionAdvice {
    public static final String ERROR_MESSAGE = "Error occurred while processing your request. Please try again later.";

    /**
     * ResponseBody annotation tells a controller that the object returned
     * is automatically serialized into JSON and passed back into the HttpResponse object
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidCredentialsException.class)
    public String InvalidCredentialsException(InvalidCredentialsException invalidCredentialsException)
    {
        return invalidCredentialsException.getMessage();
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String InvalidCredentialsException(UserNotFoundException notFoundException)
    {
        return notFoundException.getMessage();
    }

}
