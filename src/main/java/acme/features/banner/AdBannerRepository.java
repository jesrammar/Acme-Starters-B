
package acme.features.banner;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.helpers.RandomHelper;
import acme.client.repositories.AbstractRepository;
import acme.entities.banner.AdBanner;

@Repository
public interface AdBannerRepository extends AbstractRepository {

	@Query("select count(b) from AdBanner b")
	int countBanners();

	@Query("select b from AdBanner b")
	List<AdBanner> findAllBanners(PageRequest pageRequest);

	default AdBanner findRandomBanner() {
		AdBanner result;
		int count, index;
		PageRequest page;
		List<AdBanner> list;

		count = this.countBanners();
		if (count == 0)
			result = null;
		else {
			index = RandomHelper.nextInt(0, count);

			page = PageRequest.of(index, 1, Sort.by(Direction.ASC, "id"));
			list = this.findAllBanners(page);
			result = list.isEmpty() ? null : list.get(0);
		}

		return result;
	}

}
