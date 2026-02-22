
package acme.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidTickerValidator implements ConstraintValidator<ValidTicker, String> {

	private static final String PATTERN = "^[A-Z]{2}[0-9]{2}-\\w{5,10}$";


	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {

		boolean result;

		if (value == null)
			result = true;
		else
			result = !value.isBlank() && value.matches(ValidTickerValidator.PATTERN);

		return result;
	}
}
