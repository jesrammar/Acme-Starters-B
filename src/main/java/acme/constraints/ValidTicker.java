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
import javax.validation.constraints.Pattern;

@Documented
@NotBlank
@Pattern(regexp = "^[A-Z]{2}[0-9]{2}-\\w{5,10}$")
@Constraint(validatedBy = {})
@Target({ FIELD, METHOD, PARAMETER })
@Retention(RUNTIME)
public @interface ValidTicker {

	String message() default "{acme.validation.ticker.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
