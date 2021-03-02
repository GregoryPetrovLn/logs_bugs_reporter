package telran.logs.bugs.exceptions;

import javax.validation.constraints.Min;

@SuppressWarnings("serial")
public class DuplicatedException extends RuntimeException {
    public DuplicatedException(String message) {
        super(message);
    }
}
