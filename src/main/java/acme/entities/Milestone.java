package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidNumber;
import acme.validators.ValidHeader;
import acme.validators.ValidText;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Milestone extends AbstractEntity{
	
	
	@Mandatory
	@ValidHeader
	@Column
	private String title;
	
	@Mandatory
	@ValidText
	@Column
	private String achievements	;
	
	@Mandatory
	@ValidNumber(min = 0.01)
	@Column
	private Double effort;

	
	@Mandatory
	@Enumerated(EnumType.STRING)
	@Column
	private MilestoneKind kind;	
	
	
	@ManyToOne(optional = false)
	private Campaign campaign;

}
