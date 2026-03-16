
package acme.features.auditor.auditReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditReportPublishService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditReportRepository	repository;

	private AuditReport				auditReport;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;
		int auditorId;
		id = super.getRequest().getData("id", int.class);
		auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.auditReport = this.repository.findOneByIdAndAuditorId(id, auditorId);
	}

	@Override
	public void authorise() {
		boolean status;

		int id = super.getRequest().getData("id", int.class);
		int auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();

		AuditReport report = this.repository.findOneByIdAndAuditorId(id, auditorId);

		status = report != null && report.getDraftMode() == true;

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		this.auditReport.setDraftMode(false);

		super.validateObject(this.auditReport);

		boolean uniqueTicker = !this.repository.existsAuditReportWithTicker(this.auditReport.getTicker(), this.auditReport.getId());
		super.state(uniqueTicker, "ticker", "acme.validation.auditReport.duplicatedTicker.message");

		boolean hasSections = this.repository.countSectionsByReportId(this.auditReport.getId()) >= 1;
		super.state(hasSections, "*", "acme.validation.auditReport.sections.message");

		boolean startInFuture = MomentHelper.isFuture(this.auditReport.getStartMoment());
		super.state(startInFuture, "startMoment", "acme.validation.auditReport.startMomentNotFuture.message");

		boolean endInFuture = MomentHelper.isFuture(this.auditReport.getEndMoment());
		super.state(endInFuture, "endMoment", "acme.validation.auditReport.endMomentNotFuture.message");

		boolean sequenceOk = MomentHelper.isBefore(this.auditReport.getStartMoment(), this.auditReport.getEndMoment());
		super.state(sequenceOk, "startMoment", "acme.validation.auditReport.startMoment-PostEndMoment.message");

		if (super.getErrors().hasErrors())
			this.auditReport.setDraftMode(true);
	}

	@Override
	public void execute() {
		this.auditReport.setDraftMode(false);
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
