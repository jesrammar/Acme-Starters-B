
package acme.roles;

import javax.validation.constraints.NotBlank;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;

public class Fundraiser extends AbstractRole {

	@NotBlank
	@Mandatory
	String	bank;

	@NotBlank
	@Mandatory
	String	statement;

	@Mandatory
	Boolean	agent;

}
