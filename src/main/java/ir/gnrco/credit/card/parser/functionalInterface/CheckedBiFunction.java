package ir.gnrco.credit.card.parser.functionalInterface;

import ir.gnrco.credit.card.parser.exception.ISOFieldOutOfSizeException;
import ir.gnrco.credit.card.parser.exception.IllegalAmountException;
import ir.gnrco.credit.card.parser.exception.IllegalCurrencyException;
import org.jpos.iso.ISOException;
import java.text.ParseException;

/**
 * Standard BiFunction (functional interface) with exceptions
 * @author sa.gholizadeh [gholizade.saeed@yahoo.com]
 */
@FunctionalInterface
public interface CheckedBiFunction <T, U, R>{
    R apply(T t, U u) throws ISOException, ParseException, IllegalCurrencyException, IllegalAmountException, ISOFieldOutOfSizeException;
}
