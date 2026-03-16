
package acme.features.any.auditor.auditSection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditSection;

@Service
public class AnyAuditSectionListService extends AbstractService<Any, AuditSection> {

	@Autowired
	private AnyAuditSectionRepository	repository;

	private Collection<AuditSection>	sections;


	@Override
	public void authorise() {
		int auditReportId = super.getRequest().getData("auditReportId", int.class);
		int count = this.repository.countPublishedReportsById(auditReportId);
		super.setAuthorised(count == 1);
	}

	@Override
	public void load() {
		int auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.sections = this.repository.findPublishedSectionsByReportId(auditReportId);
	}

	@Override
	public void unbind() {
		for (AuditSection section : this.sections) {
			Tuple tuple = super.unbindObject(section, "name", "notes", "hours", "kind");
			tuple.put("auditReportId", section.getAuditReport().getId());
		}
	}
}
