
package acme.features.auditor.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditSectionShowService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditSectionRepository	repository;

	private AuditSection			section;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.section = this.repository.findAuditSectionById(id);
	}

	@Override
	public void authorise() {
		boolean status = this.section != null && this.section.getAuditReport().getAuditor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.section, "name", "notes", "hours", "kind");
		tuple.put("auditReportId", this.section.getAuditReport().getId());
	}
}
