
package acme.features.fundraiser.tactic;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;

@Repository
public interface TacticRepository extends AbstractRepository {

	@Query("select t from Tactic t where t.id = :tacticId")
	Tactic findTacticById(int tacticId);

	@Query("select t from Tactic t where t.strategy.id = ?1")
	Collection<Tactic> findTacticsByStrategyId(int strategyId);

	@Query("select str from Strategy str where str.id = ?1 and str.fundraiser.id = ?2")
	Strategy findStrategyByIdAndFundraiserId(int strategyId, int fundraiserId);

	@Query("select t from Tactic t where t.id = ?1 and t.strategy.fundraiser.id = ?2")
	Tactic findTacticByIdAndFundraiserId(int id, int fundraiserId);
}
