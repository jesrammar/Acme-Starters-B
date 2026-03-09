
package acme.features.any.auditor.auditReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;
import acme.features.auditor.auditReport.AuditReportRepository;

@Service
public class AnyAuditReportShowService extends AbstractService<Any, AuditReport> {

	@Autowired
	private AuditReportRepository	repository;

	private AuditReport				auditReport;


	@Override
	public void authorise() {
		boolean status;

		status = this.auditReport != null && !this.auditReport.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void unbind() {

		Tuple tuple;

		tuple = super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		tuple.put("hours", this.auditReport.getHours());

		long sectionCount = this.repository.countSectionsByReportId(this.auditReport.getId());

		tuple.put("sectionCount", sectionCount);
	}
}
