
package acme.features.any.fundraiser.tactic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Tactic;

@Repository
public interface AnyTacticRepository extends AbstractRepository {

	@Query("select t from Tactic t where t.strategy.id = ?1 and t.strategy.draftMode = false")
	Iterable<Tactic> findPublishedTacticsByStrategyId(int strategyId);

	@Query("select t from Tactic t where t.id = ?1 and t.strategy.draftMode = false")
	Tactic findPublishedTacticById(int id);

}
