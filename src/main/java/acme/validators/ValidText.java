package acme.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Documented
@Constraint(validatedBy = {})
@Target({
	ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@NotBlank
@Size(min = 1, max = 255)
public @interface ValidText {

	String message() default "{acme.validation.validText.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
