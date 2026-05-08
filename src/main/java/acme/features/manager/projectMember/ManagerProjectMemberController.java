package acme.features.manager.projectMember;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.UserAccount;
import acme.client.controllers.AbstractController;
import acme.realms.Manager;

@Controller
public class ManagerProjectMemberController extends AbstractController<Manager, UserAccount> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ManagerProjectMemberListService.class);
		super.addBasicCommand("show", ManagerProjectMemberShowService.class);
		super.addBasicCommand("create", ManagerProjectMembershipCreateService.class);
		super.addBasicCommand("delete", ManagerProjectMembershipDeleteService.class);
	}

}
