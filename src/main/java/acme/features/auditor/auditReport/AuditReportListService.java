
package acme.features.auditor.auditReport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditReportListService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditReportRepository	repository;

	private Collection<AuditReport>	auditReports;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.auditReports = (Collection<AuditReport>) this.repository.findAuditReportsByAuditorId(userAccountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {

		for (AuditReport auditReport : this.auditReports) {

			Tuple tuple;

			tuple = super.unbindObject(auditReport, "ticker", "name", "startMoment", "endMoment", "draftMode");

			tuple.put("hours", auditReport.getHours());

			long sectionCount = this.repository.countSectionsByReportId(auditReport.getId());

			tuple.put("sectionCount", sectionCount);
		}
	}
}
