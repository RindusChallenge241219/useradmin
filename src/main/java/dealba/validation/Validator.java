package dealba.validation;

/** Validate user input. */
public interface Validator {

    /**
     * @param aString String to be validated. It cannot be null.
     * @return If the passed string is valid.
     */
    boolean validate(String aString);

    /**
     * Give a hint on valid strings. Useful for feedback in case of errors.
     */
    String giveHint();
}