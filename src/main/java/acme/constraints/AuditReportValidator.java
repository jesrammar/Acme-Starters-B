
package acme.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.helpers.MomentHelper;
import acme.entities.audit.AuditReport;
import acme.features.auditor.auditReport.AuditReportRepository;

public class AuditReportValidator implements ConstraintValidator<ValidAuditReport, AuditReport> {

	@Autowired
	private AuditReportRepository repository;


	@Override
	public boolean isValid(final AuditReport report, final ConstraintValidatorContext context) {

		if (report == null)
			return true;

		boolean valid = true;
		context.disableDefaultConstraintViolation();

		// Solo validar si se publica
		if (Boolean.FALSE.equals(report.getDraftMode())) {

			// 1️: Debe tener al menos una sección
			if (report.getId() != 0) {
				long count = this.repository.countSectionsByReportId(report.getId());

				if (count == 0) {
					context.buildConstraintViolationWithTemplate("Audit report must have at least one section").addPropertyNode("draftMode").addConstraintViolation();
					valid = false;
				}
			}

			// 2️: Fechas futuras
			if (!MomentHelper.isFuture(report.getStartMoment())) {
				context.buildConstraintViolationWithTemplate("startMoment must be in the future").addPropertyNode("startMoment").addConstraintViolation();
				valid = false;
			}

			if (!MomentHelper.isFuture(report.getEndMoment())) {
				context.buildConstraintViolationWithTemplate("endMoment must be in the future").addPropertyNode("endMoment").addConstraintViolation();
				valid = false;
			}

			// 3️: start < end
			if (!MomentHelper.isBefore(report.getStartMoment(), report.getEndMoment())) {
				context.buildConstraintViolationWithTemplate("startMoment must be before endMoment").addPropertyNode("startMoment").addConstraintViolation();
				valid = false;
			}
		}

		return valid;
	}
}
