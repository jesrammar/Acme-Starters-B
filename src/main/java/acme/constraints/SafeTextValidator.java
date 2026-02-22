package acme.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SafeTextValidator implements ConstraintValidator<ValidSafeText, String> {

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		
		assert context != null;

		boolean result;

		if (value == null)
			result = true;
		else {
			
			result = value.indexOf('<') < 0 && value.indexOf('>') < 0;
		}

		return result;
	}

}
