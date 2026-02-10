package acme.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Documented
@Constraint(validatedBy = {})
@Target({
	ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@NotBlank
@Pattern(regexp = "^[A-Z]{2}[0-9]{2}-\\w{5,10}$")
public @interface ValidTicker {

	String message() default "{acme.validation.validTicker.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
