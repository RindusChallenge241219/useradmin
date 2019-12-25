package dealba.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameValidatorTest {

    @Test
    public void test() {

        NameValidator validator = new NameValidator();
        assertFalse("Empty string is not valid", validator.validate(""));
        assertFalse("Name cannot contain numbers", validator.validate("007"));
        assertFalse("Name cannot contain non-ASCII letters", validator.validate("Cristóbal"));
        assertFalse("Name must start with uppercase letter", validator.validate("pepe"));
        assertTrue(validator.validate("Luis"));
    }
}