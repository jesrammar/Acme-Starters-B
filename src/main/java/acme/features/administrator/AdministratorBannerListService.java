
package acme.features.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Administrator;
import acme.client.services.AbstractService;
import acme.entities.banner.AdBanner;

@Service
public class AdministratorBannerListService extends AbstractService<Administrator, AdBanner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBannerRepository	repository;

	private Collection<AdBanner>			banners;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		this.banners = this.repository.findAllBanners();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.banners, "slogan", "target", "picture");
	}

}
