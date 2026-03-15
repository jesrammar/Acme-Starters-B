
package acme.features.auditor.auditReport;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.audit.AuditReport;
import acme.realms.Auditor;

@Controller
public class AuditReportController extends AbstractController<Auditor, AuditReport> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AuditReportListService.class);
		super.addBasicCommand("show", AuditReportShowService.class);
		super.addBasicCommand("create", AuditReportCreateService.class);
		super.addBasicCommand("update", AuditReportUpdateService.class);
		super.addBasicCommand("delete", AuditReportDeleteService.class);

		super.addCustomCommand("publish", "update", AuditReportPublishService.class);
	}
}
