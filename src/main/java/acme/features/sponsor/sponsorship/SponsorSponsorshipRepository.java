
package acme.features.sponsor.sponsorship;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select i from Sponsorship i where i.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select i from Sponsorship i where i.ticker = :ticker")
	Sponsorship findSponsorshipByTicker(String ticker);

	@Query("select i from Sponsorship i where i.sponsor.userAccount.id = :userAccountId")
	Iterable<Sponsorship> findSponsorshipsBySponsorId(int userAccountId);

	@Query("select sum(d.money.amount) from Donation d where d.sponsorship.id = :sponsorshipId")
	Double sumTotalMoneyBySponsorshipId(int sponsorshipId);

	@Query("select count(d) from Donation d where d.sponsorship.id = :sponsorshipId")
	long countDonationsBySponsorshipId(int sponsorshipId);
}
