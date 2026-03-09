
package acme.features.any.auditor.auditReport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;

@Service
public class AnyAuditReportListService extends AbstractService<Any, AuditReport> {

	@Autowired
	private AnyAuditReportRepository	repository;

	private Collection<AuditReport>		auditReports;


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		this.auditReports = this.repository.findPublishedAuditReports();
	}

	@Override
	public void unbind() {

		for (AuditReport auditReport : this.auditReports) {

			Tuple tuple;

			tuple = super.unbindObject(auditReport, "ticker", "name", "startMoment", "endMoment");

			tuple.put("hours", auditReport.getHours());

			long sectionCount = this.repository.countSectionsByReportId(auditReport.getId());

			tuple.put("sectionCount", sectionCount);
		}
	}
}
