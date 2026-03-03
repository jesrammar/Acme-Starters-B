
package acme.features.auditor.auditSection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditSectionListService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditSectionRepository		repository;

	private Collection<AuditSection>	sections;


	@Override
	public void load() {
		int auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.sections = (Collection<AuditSection>) this.repository.findSectionsByReportId(auditReportId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		for (AuditSection section : this.sections) {
			Tuple tuple = super.unbindObject(section, "name", "notes", "hours", "kind");
			tuple.put("auditReportId", section.getAuditReport().getId());
		}
	}
}
