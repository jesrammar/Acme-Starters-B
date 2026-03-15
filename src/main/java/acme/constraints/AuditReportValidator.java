
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.audit.AuditReport;
import acme.features.auditor.auditReport.AuditReportRepository;

@Validator
public class AuditReportValidator extends AbstractValidator<ValidAuditReport, AuditReport> {

	@Autowired
	private AuditReportRepository repository;


	@Override
	protected void initialise(final ValidAuditReport annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final AuditReport auditReport, final ConstraintValidatorContext context) {
		assert context != null;
		boolean isValid = true;

		if (auditReport == null)
			return true;

		// Check duplicated audit report with equal ticker
		{
			boolean uniqueAuditReport;
			uniqueAuditReport = !this.repository.existsAuditReportWithTicker(auditReport.getTicker(), auditReport.getId());
			super.state(context, uniqueAuditReport, "ticker", "acme.validation.auditReport.duplicatedTicker.message");
		}

		// For the already published audit reports
		if (Boolean.FALSE.equals(auditReport.getDraftMode())) {

			// Check audit report has at least one section
			{
				boolean hasAtLeastOneSection;
				hasAtLeastOneSection = this.repository.countSectionsByReportId(auditReport.getId()) >= 1;
				super.state(context, hasAtLeastOneSection, "*", "acme.validation.auditReport.sections.message");
			}

			//			// Check startMoment is in the future
			//			{
			//				boolean startMomentInFuture;
			//				startMomentInFuture = MomentHelper.isFuture(auditReport.getStartMoment());
			//				super.state(context, startMomentInFuture, "startMoment", "acme.validation.auditReport.startMomentNotFuture.message");
			//			}
			//
			//			// Check endMoment is in the future
			//			{
			//				boolean endMomentInFuture;
			//				endMomentInFuture = MomentHelper.isFuture(auditReport.getEndMoment());
			//				super.state(context, endMomentInFuture, "endMoment", "acme.validation.auditReport.endMomentNotFuture.message");
			//			}

			// Check startMoment is before endMoment
			{
				boolean startMomentIsPreviousToEndMoment;
				startMomentIsPreviousToEndMoment = MomentHelper.isBefore(auditReport.getStartMoment(), auditReport.getEndMoment());
				super.state(context, startMomentIsPreviousToEndMoment, "startMoment", "acme.validation.auditReport.startMoment-PostEndMoment.message");
			}
			isValid = !super.hasErrors(context);
		}
		return isValid;
	}

}
