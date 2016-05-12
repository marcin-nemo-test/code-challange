package tk.zielony.codechallange.api;

/**
 * Created by Marcin on 2016-05-12.
 */
public class ExceptionEvent {
    private Exception exception;

    public ExceptionEvent(Exception e) {
        this.exception = e;
    }

    public Exception getException() {
        return exception;
    }
}