
package acme.features.any.fundraiser.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.strategies.Strategy;

public interface AnyStrategyRepository {

	//Obtener las strategies con draftMode = false
	@Query("select str from Strategy str where str.draftMode = false")
	Collection<Strategy> findPublishedStrategies();

	//Obtener una strategy mediante su ID
	@Query("select str from Strategy str where str.id = :id and str.draftMode = false")
	Strategy findPublishedStrategyById(int id);

	// Contar tactics de una strategy
	@Query("select count(t) from Tactic t where t.strategy.id = ?1")
	long countTacticsByStrategyId(int strategyId);

	//Calculo del expectedPercentage
	@Query("select sum(T.expectedPercentage) from Tactic T where T.strategy.id = ?1 ")
	Double getExpectedPercentage(int strategyId);

}
