
package acme.features.any.inventor.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Part;

@Repository
public interface AnyPartRepository extends AbstractRepository {

	@Query("select p from Part p where p.id = :partId and p.invention.inventor.id = :inventorId and p.invention.draftMode = false")
	Part findPublishedPartByIdAndInventorId(int partId, int inventorId);

	@Query("select p from Part p where p.id = :partId and p.invention.draftMode = false")
	Part findPublishedPartById(int partId);

	@Query("select p from Part p where p.invention.inventor.id = :inventorId and p.invention.draftMode = false")
	Collection<Part> findPublishedPartsByInventorId(int inventorId);

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findPublishedPartsByInventionId(int inventionId);

	@Query("select p from Part p where p.invention.id = :inventionId and p.invention.inventor.id = :inventorId and p.invention.draftMode = false")
	Collection<Part> findPublishedPartsByInventionIdAndInventorId(int inventionId, int inventorId);

}
