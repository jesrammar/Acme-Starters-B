
package acme.features.auditor.auditReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditReportCreateService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	private AuditReportRepository	repository;

	private AuditReport				auditReport;


	@Override
	public void load() {
		Auditor auditor = (Auditor) super.getRequest().getPrincipal().getActiveRealm();
		this.auditReport = super.newObject(AuditReport.class);
		this.auditReport.setAuditor(auditor);
		this.auditReport.setDraftMode(true);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);
		boolean uniqueTicker;
		uniqueTicker = !this.repository.existsAuditReportWithTicker(this.auditReport.getTicker(), this.auditReport.getId());
		super.state(uniqueTicker, "ticker", "acme.validation.auditReport.duplicatedTicker.message");
	}

	@Override
	public void execute() {
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
