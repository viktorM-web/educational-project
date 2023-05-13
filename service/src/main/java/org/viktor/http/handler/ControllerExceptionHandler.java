package org.viktor.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice(basePackages = "org.viktor.http.controller")
public class ControllerExceptionHandler {
}
