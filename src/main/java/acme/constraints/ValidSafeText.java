package acme.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = SafeTextValidator.class)
@Target({
	FIELD, METHOD, PARAMETER, ANNOTATION_TYPE
})
@Retention(RUNTIME)
public @interface ValidSafeText {

	String message() default "{acme.validation.safeText.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
