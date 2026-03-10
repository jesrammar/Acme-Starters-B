
package acme.features.auditor.auditReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditReportDeleteService extends AbstractService<Auditor, AuditReport> {

	// Repository ------------------------------------------------------------
	@Autowired
	private AuditReportRepository	repository;

	private AuditReport				auditReport;

	// AbstractService interface --------------------------------------------


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.auditReport = this.repository.findOneByIdAndAuditorId(id, auditorId);
	}

	@Override
	public void authorise() {
		boolean status = this.auditReport != null && this.auditReport.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
	}

	@Override
	public void validate() {
	}

	@Override
	public void execute() {

		this.repository.delete(this.auditReport);
	}

	@Override
	public void unbind() {
	}

}
