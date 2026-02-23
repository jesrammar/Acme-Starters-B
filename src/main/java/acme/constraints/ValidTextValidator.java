
package acme.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidTextValidator implements ConstraintValidator<ValidText, String> {

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {

		boolean result;

		if (value == null)
			result = true;
		else
			result = !value.isBlank() && value.length() >= 1 && value.length() <= 255;

		return result;
	}
}
