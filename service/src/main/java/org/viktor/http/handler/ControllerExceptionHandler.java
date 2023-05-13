package org.viktor.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice(basePackages = "org.viktor.http.controller")
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handlerException(Exception exception, HttpServletRequest httpServletRequest) {
        log.error("Failed to return response", exception);
        return "error/error500";
    }
}
