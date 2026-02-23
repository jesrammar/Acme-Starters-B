package acme.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Documented
@NotBlank
@Size(min = 1, max = 75)
@Constraint(validatedBy = {})
@Target({ FIELD, METHOD, PARAMETER })
@Retention(RUNTIME)
public @interface ValidHeader {

	String message() default "{acme.validation.header.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
