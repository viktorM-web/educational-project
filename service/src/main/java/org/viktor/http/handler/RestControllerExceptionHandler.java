package org.viktor.http.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "org.viktor.http.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
