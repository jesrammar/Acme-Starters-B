
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidScore;
import acme.client.components.validation.ValidString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tactic extends AbstractEntity {

	@NotBlank
	@Mandatory
	@ValidString
	String		name;

	@Mandatory
	@NotBlank
	@ValidString
	String		notes;

	@Mandatory
	@ValidScore
	Double		expectedPercentage;

	@Mandatory
	@Enumerated(EnumType.STRING)
	//Valid???
	TacticKind	kind;

}
