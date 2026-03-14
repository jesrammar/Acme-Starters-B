
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Part;

@Repository
public interface PartRepository extends AbstractRepository {

	@Query("select p from Part p where p.id = :partId and p.invention.inventor.id = :inventorId")
	Part findPartByInventorIdAndPartId(int inventorId, int partId);

	@Query("select p from Part p where p.id = :partId")
	Part findPartById(int partId);

	@Query("select p from Part p where p.invention.inventor.id = :inventorId")
	Collection<Part> findAllPartsByInventorId(int inventorId);

}
