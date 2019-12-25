package dealba.validation;

/** Validates user logins. */
public class PasswordValidator implements Validator {

    @Override
    public boolean validate(String aString) {
        return aString.matches("^[a-zA-Z][a-zA-Z0-9]*$") &&
            aString.length() > 8 && aString.length() < 21;
    }

    @Override
    public String giveHint() {
        return "A valid password contains a string of ASCII characters and " +
            "numbers. The length of the string must be between 8 and 20 " +
            "characters.";
    }
    
}