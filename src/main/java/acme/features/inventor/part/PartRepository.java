
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Part;

@Repository
public interface PartRepository extends AbstractRepository {

	@Query("select p from Part p where p.id = :partId and p.invention.inventor.id = :inventorId")
	Part findPartByIdAndInventorId(int partId, int inventorId);

	@Query("select p from Part p where p.id = :partId")
	Part findPartById(int partId);

	@Query("select p from Part p where p.invention.inventor.id = :inventorId")
	Collection<Part> findPartsByInventorId(int inventorId);

	@Query("select p from Part p where p.invention.id = :inventionId and p.invention.inventor.id = :inventorId")
	Collection<Part> findPartsByInventionIdAndInventorId(int inventionId, int inventorId);

}
