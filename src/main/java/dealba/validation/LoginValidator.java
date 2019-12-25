package dealba.validation;

/** Validates user logins. */
public class LoginValidator implements Validator {

    @Override
    public boolean validate(String aString) {
        return aString.matches("^[a-zA-Z][a-zA-Z0-9]*$") &&
            aString.length() > 4 && aString.length() < 21;
    }

    @Override
    public String giveHint() {
        return "A valid login contains a string starting with an uppercase or" +
            " lower letter followed by letters or numbers. The length of the " +
            "string must be between 5 and 20 characters.";
    }
    
}