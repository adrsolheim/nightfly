package no.adrsolheim.nightfly.exception;

/**
 * Presenting the user with custom error message tailored for this application
 */

public class NightflyException extends RuntimeException {
    public NightflyException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public NightflyException(String exMessage) {
        super(exMessage);
    }
}
