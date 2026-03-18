
package acme.features.auditor.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditSection;
import acme.entities.audit.SectionKind;
import acme.realms.Auditor;

@Service
public class AuditSectionDeleteService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditSectionRepository	repository;

	private AuditSection			section;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.section = this.repository.findOneByIdAndAuditorId(id, auditorId);
	}

	@Override
	public void authorise() {
		boolean status = this.section != null && this.section.getAuditReport().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.section, "name", "notes", "hours", "kind");

	}

	@Override
	public void validate() {
	}

	@Override
	public void execute() {
		this.repository.delete(this.section);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.section, "name", "notes", "hours", "kind");
		tuple.put("reportId", this.section.getAuditReport().getId());
		tuple.put("auditReportDraftMode", this.section.getAuditReport().getDraftMode());
		SelectChoices choices = SelectChoices.from(SectionKind.class, this.section.getKind());
		tuple.put("kind", choices);
	}
}
