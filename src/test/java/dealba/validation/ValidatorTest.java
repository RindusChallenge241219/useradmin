package dealba.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidatorTest {

    @Test
    public void testNameValidator() {

        NameValidator validator = new NameValidator();
        assertFalse("Empty string is not valid", validator.validate(""));
        assertFalse("Name cannot contain numbers", validator.validate("007"));
        assertFalse("Name cannot contain non-ASCII letters", validator.validate("Cristóbal"));
        assertFalse("Name must start with uppercase letter", validator.validate("pepe"));
        assertTrue(validator.validate("Luis"));
    }

    @Test
    public void testLoginValidator() {
        LoginValidator validator = new LoginValidator();
        assertFalse("Empty string is not valid", validator.validate(""));
        assertFalse("Login cannot contain only numbers", validator.validate("007"));
        assertFalse("Login cannot start with a number but a letter", validator.validate("7Bond"));
        assertFalse("Login must be a least 5-characters long", validator.validate("Bond"));
        assertFalse("Login cannot be over 20-characters long",
            validator.validate("abcdefghijklmnopqrstuvwxyz"));
        assertFalse("Login cannot contain whitespaces", validator.validate("James Bond"));
        assertTrue(validator.validate("Bond007"));
    }
}