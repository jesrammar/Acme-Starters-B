
package acme.features.auditor.auditReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		status = this.auditReport != null && this.auditReport.getDraftMode();

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
