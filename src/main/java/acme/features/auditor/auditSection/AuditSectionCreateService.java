
package acme.features.auditor.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;
import acme.entities.audit.AuditSection;
import acme.entities.audit.SectionKind;
import acme.realms.Auditor;

@Service
public class AuditSectionCreateService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditSectionRepository	repository;

	private AuditSection			auditSection;


	@Override
	public void load() {
		int reportId = super.getRequest().getData("reportId", int.class);
		int auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		AuditReport report = this.repository.findAuditReportByIdAndAuditorId(reportId, auditorId);

		this.auditSection = super.newObject(AuditSection.class);
		this.auditSection.setAuditReport(report);
		;
	}

	@Override
	public void authorise() {
		boolean status = this.auditSection.getAuditReport() != null && this.auditSection.getAuditReport().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditSection);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditSection);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
		choices = SelectChoices.from(SectionKind.class, this.auditSection.getKind());
		tuple.put("reportId", this.auditSection.getAuditReport().getId());
		tuple.put("draftMode", this.auditSection.getAuditReport().getDraftMode());
		tuple.put("kinds", choices);
	}
}
