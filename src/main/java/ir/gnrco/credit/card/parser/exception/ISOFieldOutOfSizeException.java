package ir.gnrco.credit.card.parser.exception;

public class ISOFieldOutOfSizeException extends Exception {
    public ISOFieldOutOfSizeException(String message){ super(message);}
    public ISOFieldOutOfSizeException(Throwable nested) {
        super(nested.toString());
    }
}
