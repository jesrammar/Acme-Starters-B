
package acme.features.any.inventor.invention;

import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class AnyInventionListService extends AbstractService<Inventor, Invention> {

}
