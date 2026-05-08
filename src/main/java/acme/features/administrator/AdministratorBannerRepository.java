
package acme.features.administrator;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.banner.AdBanner;

@Repository
public interface AdministratorBannerRepository extends AbstractRepository {

	@Query("select b from AdBanner b where b.id = :id")
	AdBanner findBannerById(int id);

	@Query("select b from AdBanner b")
	Collection<AdBanner> findAllBanners();

}
