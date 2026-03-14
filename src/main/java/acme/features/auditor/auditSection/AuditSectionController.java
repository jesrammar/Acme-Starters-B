
package acme.features.auditor.auditSection;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.audit.AuditSection;
import acme.realms.Auditor;

@Controller
public class AuditSectionController extends AbstractController<Auditor, AuditSection> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AuditSectionListService.class);
		super.addBasicCommand("show", AuditSectionShowService.class);
		//	super.addBasicCommand("create", AuditSectionCreateService.class);
		super.addBasicCommand("update", AuditSectionUpdateService.class);
		super.addBasicCommand("delete", AuditSectionDeleteService.class);
	}
}
