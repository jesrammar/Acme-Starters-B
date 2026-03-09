
package acme.features.sponsorships.donation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;

@Repository
public interface DonationRepository extends AbstractRepository {

	@Query("select i from Donation i where i.id = :id")
	Donation findDonationById(int id);

	@Query("select i from Donation i where i.sponsorship.id = :sponsorshipId")
	Iterable<Donation> findDonationsBySponsorshipId(int sponsorshipId);
}
