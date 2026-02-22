package acme.entities.campaigns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import acme.constraints.ValidSafeText;
import acme.constraints.ValidText;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Spokesperson extends AbstractRole {

	

	

	@Mandatory
	@ValidText
	@ValidSafeText
	@Column
	private String cv;

	@Mandatory
	@ValidText
	@ValidSafeText
	@Column
	private String achievements;

	@Mandatory
	@Valid
	@Column
	private Boolean licensed;

}
