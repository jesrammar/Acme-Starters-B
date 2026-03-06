
package acme.features.fundraiser.tactic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Tactic;

@Repository
public interface TacticRepository extends AbstractRepository {

	@Query("select t from Tactic t where t.id = :tacticId")
	Tactic findTacticById(int tacticId);

	@Query("select t from Tactic t where t.strategy.id = :strategyId")
	Iterable<Tactic> findTacticsByStrategyId(int strategyId);

}
