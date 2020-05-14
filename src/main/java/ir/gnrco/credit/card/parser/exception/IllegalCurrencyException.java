package ir.gnrco.credit.card.parser.exception;

public class IllegalCurrencyException extends Exception {
    public IllegalCurrencyException(String message){ super(message);}
    public IllegalCurrencyException(Throwable nested) {
        super(nested.toString());
    }
}
