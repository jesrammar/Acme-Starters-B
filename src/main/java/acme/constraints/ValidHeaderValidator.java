
package acme.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidHeaderValidator implements ConstraintValidator<ValidHeader, String> {

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {

		boolean result;

		if (value == null)
			result = true;
		else
			result = !value.isBlank() && value.length() >= 1 && value.length() <= 75;

		return result;
	}
}
