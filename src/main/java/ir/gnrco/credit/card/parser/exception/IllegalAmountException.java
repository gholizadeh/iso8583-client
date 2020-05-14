package ir.gnrco.credit.card.parser.exception;

public class IllegalAmountException extends Exception{
    public IllegalAmountException(String message){ super(message);}
    public IllegalAmountException(Throwable nested) {
        super(nested.toString());
    }
}
