package acme.entities.campaigns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidNumber;
import acme.constraints.ValidHeader;
import acme.constraints.ValidSafeText;
import acme.constraints.ValidText;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Milestone extends AbstractEntity {


	@Mandatory
	@ValidHeader
	@ValidSafeText
	@Column
	private String title;

	@Mandatory
	@ValidText
	@ValidSafeText
	@Column
	private String achievements;

	@Mandatory
	@ValidNumber(min = 0.01)
	@Column
	private Double effort;

	@Mandatory
	@Valid
	@Enumerated(EnumType.STRING)
	@Column
	private MilestoneKind kind;

	

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Campaign campaign;

}
