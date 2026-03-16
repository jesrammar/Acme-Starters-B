
package acme.features.auditor.auditSection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;
import acme.entities.audit.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditSectionListService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditSectionRepository		repository;
	private Collection<AuditSection>	sections;
	private AuditReport					report;


	@Override
	public void authorise() {
		int reportId = super.getRequest().getData("reportId", int.class);
		int auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		AuditReport checkReport = this.repository.findAuditReportByIdAndAuditorId(reportId, auditorId);
		super.setAuthorised(checkReport != null);
	}

	@Override
	public void load() {
		int reportId = super.getRequest().getData("reportId", int.class);
		int auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();

		this.sections = this.repository.findSectionsByReportIdAndAuditorId(reportId, auditorId);
		this.report = this.repository.findAuditReportByIdAndAuditorId(reportId, auditorId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.sections, "name", "kind", "notes", "hours");
		super.unbindGlobal("reportId", super.getRequest().getData("reportId", int.class));
		super.unbindGlobal("auditReportDraftMode", this.report.getDraftMode());
	}
}
