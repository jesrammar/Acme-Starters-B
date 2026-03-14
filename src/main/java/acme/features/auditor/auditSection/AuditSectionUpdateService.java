
package acme.features.auditor.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audit.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditSectionUpdateService extends AbstractService<Auditor, AuditSection> {

	// Repository ------------------------------------------------------------
	@Autowired
	private AuditSectionRepository	repository;

	private AuditSection			section;

	// AbstractService interface ---------------------------------------------


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
		super.validateObject(this.section);
	}

	@Override
	public void execute() {
		this.repository.save(this.section);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.section, "name", "notes", "hours", "kind");
	}
}
