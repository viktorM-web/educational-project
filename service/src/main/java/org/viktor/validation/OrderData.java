package org.viktor.validation;

import org.junit.jupiter.api.Order;
import org.viktor.validation.impl.OrderDataValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OrderDataValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderData {

    String message() default "In this period car busy or your driver licence expired";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
