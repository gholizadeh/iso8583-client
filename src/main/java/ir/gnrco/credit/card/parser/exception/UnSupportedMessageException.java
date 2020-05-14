package ir.gnrco.credit.card.parser.exception;

public class UnSupportedMessageException extends Exception {
    public UnSupportedMessageException(String message){ super(message);}
    public UnSupportedMessageException(Throwable nested) {
        super(nested.toString());
    }
}
