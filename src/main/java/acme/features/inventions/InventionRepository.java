
package acme.features.inventions;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;

@Repository
public interface InventionRepository extends AbstractRepository {

	@Query("select i from Invention i where i.id = :id")
	Invention findInventionById(int id);

	@Query("select sum(p.cost.amount) form Part p where p.invention.id = :id")
	Double sumPartsCostByInventionId(int id);
}
