package acme.entities.projects;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.principals.UserAccount;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.entities.campaigns.Campaign;
import acme.realms.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Mandatory
	@ValidHeader
	@Column
	private String title;

	@Optional
	@ValidText
	@Column(length = 512)
	private String keywords;

	@Mandatory
	@ValidText
	@Column(length = 1024)
	private String description;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date kickOffMoment;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date closeOutMoment;

	@Mandatory
	@Valid
	@Column
	private Boolean draftMode;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Manager manager;

	@ManyToMany
	@JoinTable(name = "project_membership", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_account_id"))
	private Set<UserAccount> members = new LinkedHashSet<>();

	@OneToMany(mappedBy = "project")
	private Set<Campaign> campaigns = new LinkedHashSet<>();

	@Transient
	private String memberUsernames;

	@Transient
	private String memberUsername;

	@Transient
	private String campaignTicker;

}
