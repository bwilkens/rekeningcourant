package nl.quintor.rc.web.rest.exception;

/**
 * Created by marcel on 5-2-2017.
 */
public class ValidationError {
    private String message;

    ValidationError(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}
