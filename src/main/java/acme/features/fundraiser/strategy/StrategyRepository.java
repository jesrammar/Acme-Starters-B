
package acme.features.fundraiser.strategy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Strategy;

@Repository
public interface StrategyRepository extends AbstractRepository {

	//Calculo del expectedPercentage
	@Query("select sum(T.expectedPercentage) from Tactic T where T.strategy.id = ?1 ")
	Double getExpectedPercentage(int strategyId);

	//Obtener una strategy mediante su ID
	@Query("select str from Strategy str where str.id = :id")
	Strategy findStrategyById(int id);

	//Obtener la strategy de un fundraiser
	@Query("select str from Strategy str where str.fundraiser.userAccount.id = :userAccountId")
	Iterable<Strategy> findStrategyByFundraiserId(int userAccountId);

	// Contar tactics de una strategy
	@Query("select count(t) from Tactic t where t.strategy.id = ?1")
	long countTacticsByStrategyId(int strategyId);

	//Obtener las strategies con draftMode = false (para el menú any)

	@Query("select str from Strategy str where str.draftMode = false")
	Iterable<Strategy> findPublishedStrategies();
}
