
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
	@Query("select str from Strategy str where str.id = ?1")
	Strategy findStrategyById(int id);

	//Obtener la strategy de un fundraiser
	@Query("select str from Strategy str where str.fundraiser.userAccount.id = ?1")
	Iterable<Strategy> findStrategyByFundraiserId(int userAccountId);

	// Contar tactics de una strategy
	@Query("select count(t) from Tactic t where t.strategy.id = ?1")
	long countTacticsByStrategyId(int strategyId);

	//Comprobación de si existe estrategia con ese ticker 
	@Query("select count(str) > 0 from Strategy str where str.ticker = :ticker and str.id != ?1")
	boolean existsStrategyWithTicker(String ticker, int id);

	@Query("select str from Strategy str where str.id = ?1 and str.fundraiser.id = ?2")
	Strategy findOneByIdAndFundraiserId(int id, int fundraiserId);
}
