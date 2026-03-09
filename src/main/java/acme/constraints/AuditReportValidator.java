
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

		// Ticker único (SIEMPRE)
		if (this.repository.existsAuditReportWithTicker(report.getTicker(), report.getId())) {
			context.buildConstraintViolationWithTemplate("{acme.validation.auditReport.duplicatedTicker.message}").addPropertyNode("ticker").addConstraintViolation();
			valid = false;
		}

		// Validaciones al publicar
		if (Boolean.FALSE.equals(report.getDraftMode())) {

			// Debe tener al menos una sección
			if (report.getId() != 0) {
				long count = this.repository.countSectionsByReportId(report.getId());

				if (count == 0) {
					context.buildConstraintViolationWithTemplate("{acme.validation.auditReport.must-have-section.message}").addPropertyNode("draftMode").addConstraintViolation();
					valid = false;
				}
			}

			// startMoment debe ser futuro
			if (!MomentHelper.isFuture(report.getStartMoment())) {
				context.buildConstraintViolationWithTemplate("{acme.validation.auditReport.startMoment-NotFuture.message}").addPropertyNode("startMoment").addConstraintViolation();
				valid = false;
			}

			// endMoment debe ser futuro
			if (!MomentHelper.isFuture(report.getEndMoment())) {
				context.buildConstraintViolationWithTemplate("{acme.validation.auditReport.endMoment-NotFuture.message}").addPropertyNode("endMoment").addConstraintViolation();
				valid = false;
			}

			// Intervalo válido
			if (!MomentHelper.isBefore(report.getStartMoment(), report.getEndMoment())) {
				context.buildConstraintViolationWithTemplate("{acme.validation.auditReport.startMoment-PostEndMoment.message}").addPropertyNode("startMoment").addConstraintViolation();
				valid = false;
			}
		}

		return valid;
	}
}
