
package acme.entities.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.client.helpers.LoggerHelper;
import acme.features.banner.AdBannerRepository;

@ControllerAdvice
public class AdBannerAdvisor {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdBannerRepository repository;

	// Beans ------------------------------------------------------------------


	@ModelAttribute("adbanner")
	public AdBanner getBanner() {
		AdBanner result;

		try {
			result = this.repository.findRandomBanner();
		} catch (final Throwable oops) {
			LoggerHelper.error(oops.getLocalizedMessage());
			result = null;
		}

		return result;
	}

}
