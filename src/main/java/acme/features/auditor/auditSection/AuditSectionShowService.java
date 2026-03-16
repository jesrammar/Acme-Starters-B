
package acme.features.auditor.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditSection;
import acme.entities.audit.SectionKind;
import acme.realms.Auditor;

@Service
public class AuditSectionShowService extends AbstractService<Auditor, AuditSection> {

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
		boolean status = this.section != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.section, "name", "notes", "hours");
		super.unbindGlobal("auditReportDraftMode", this.section.getAuditReport().getDraftMode());
		super.unbindGlobal("reportId", this.section.getAuditReport().getId());

		SelectChoices choices = SelectChoices.from(SectionKind.class, this.section.getKind());
		super.unbindGlobal("kind", choices);

	}
}
