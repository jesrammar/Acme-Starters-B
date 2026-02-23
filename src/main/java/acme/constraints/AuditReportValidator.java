
package acme.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import acme.client.components.datatypes.Moment;
import acme.entities.audit.AuditReport;

public class AuditReportValidator implements ConstraintValidator<ValidAuditReport, AuditReport> {

	@Override
	public boolean isValid(final AuditReport report, final ConstraintValidatorContext context) {

		if (report == null)
			return true;

		boolean valid = true;

		// Comprobar que tenga al menos una sección
		if (report.getSections() == null || report.getSections().isEmpty()) {
			context.buildConstraintViolationWithTemplate("Audit report must have at least one section").addPropertyNode("sections").addConstraintViolation();
			valid = false;
		}

		// Comprobar que startMoment < endMoment y ambos en el futuro
		Moment now = new Moment(); // momento actual
		Moment start = report.getStartMoment();
		Moment end = report.getEndMoment();

		if (start != null && start.before(now)) {
			context.buildConstraintViolationWithTemplate("startMoment must be in the future").addPropertyNode("startMoment").addConstraintViolation();
			valid = false;
		}

		if (end != null && end.before(now)) {
			context.buildConstraintViolationWithTemplate("endMoment must be in the future").addPropertyNode("endMoment").addConstraintViolation();
			valid = false;
		}

		// start < end
		if (start != null && end != null && start.after(end)) {
			context.buildConstraintViolationWithTemplate("startMoment must be before endMoment").addPropertyNode("startMoment").addConstraintViolation();
			valid = false;
		}

		return valid;
	}
}
