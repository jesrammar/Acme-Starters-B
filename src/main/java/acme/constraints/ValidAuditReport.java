
package acme.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({
	ElementType.TYPE, ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AuditReportValidator.class)
public @interface ValidAuditReport {

	String message() default "{acme.validation.audit-report.invalid.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
