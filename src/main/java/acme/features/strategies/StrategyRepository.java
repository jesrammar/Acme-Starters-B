
package acme.features.strategies;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface StrategyRepository extends AbstractRepository {

	@Query("select sum(T.expectedPercentage) from Tactic T where T.strategy.id = strategyId ")
	Double getExpectedPercentage(int strategyId);

}
