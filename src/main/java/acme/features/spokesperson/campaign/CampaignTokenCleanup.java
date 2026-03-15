package acme.features.spokesperson.campaign;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CampaignTokenCleanup {

	@PersistenceContext
	private EntityManager entityManager;

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void clearAcmeTokensOnStartup() {
		// The framework can reuse CSRF vouchers after restarts, so we drop stale tokens at boot.
		this.entityManager.createNativeQuery("delete from acme_token").executeUpdate();
	}
}
