
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.projects.Project;
import acme.features.inventor.invention.InventionRepository;

@Validator
public class ProjectValidator extends AbstractValidator<ValidProject, Project> {

	@Autowired
	private InventionRepository inventionRepository;


	@Override
	public boolean isValid(final Project project, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (project == null)
			result = true;
		else {
			{
				boolean validTimeInterval;
				validTimeInterval = project.getKickOffMoment() == null || project.getCloseOutMoment() == null || project.getKickOffMoment().before(project.getCloseOutMoment());

				super.state(context, validTimeInterval, "*", "acme.validation.project.timeInterval.message");
			}

			{
				boolean publishedWithAtLeastOneInvention;
				if (Boolean.TRUE.equals(project.getDraftMode()))
					publishedWithAtLeastOneInvention = true;
				else {
					long count = this.inventionRepository.countByProjectId(project.getId());
					publishedWithAtLeastOneInvention = count > 0;
				}

				super.state(context, publishedWithAtLeastOneInvention, "draftMode", "acme.validation.project.draftMode.message");
			}
			return !super.hasErrors(context);

		}
		return result;
	}

}
