package by.intexsoft.gehaltszuteiler.exceptions;

public class HttpResponseReturnValueException extends IllegalStateException {

    public HttpResponseReturnValueException(String message) {
        super(message);
    }

    public HttpResponseReturnValueException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
