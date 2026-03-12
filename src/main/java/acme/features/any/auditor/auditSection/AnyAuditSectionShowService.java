
package acme.features.any.auditor.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditSection;

@Service
public class AnyAuditSectionShowService extends AbstractService<Any, AuditSection> {

	@Autowired
	private AnyAuditSectionRepository	repository;

	private AuditSection				section;


	@Override
	public void authorise() {
		boolean status = this.section != null && !this.section.getAuditReport().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.section = this.repository.findPublishedAuditSectionById(id);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.section, "name", "notes", "hours", "kind");
		tuple.put("auditReportId", this.section.getAuditReport().getId());
	}
}
