
package acme.features.any.sponsor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.realms.Sponsor;

@Repository
public interface AnySponsorRepository extends AbstractRepository {

	@Query("select i from Sponsor i where i.id = :id")
	Sponsor findSponsorById(int id);

}
