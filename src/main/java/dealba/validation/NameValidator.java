package dealba.validation;

/** Validates user names. */
public class NameValidator implements Validator {

    @Override
    public boolean validate(String aString) {
        return aString.matches("^[A-Z][a-z]*$");
    }

    @Override
    public String giveHint() {
        return "A valid name only contains ASCII letters and starts with an uppercase letter. It cannot be empty.";
    }
}