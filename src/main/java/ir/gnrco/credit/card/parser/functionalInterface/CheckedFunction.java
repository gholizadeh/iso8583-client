package ir.gnrco.credit.card.parser.functionalInterface;

import ir.gnrco.credit.card.parser.exception.IllegalAmountException;
import ir.gnrco.credit.card.parser.exception.IllegalCurrencyException;
import org.jpos.iso.ISOException;

import java.text.ParseException;

/**
 * Standard Function (functional interface) with Exceptions
 * @author sa.gholizadeh [gholizade.saeed@yahoo.com]
 */
@FunctionalInterface
public interface CheckedFunction<T, R>{
    R apply(T t) throws NumberFormatException, ParseException, IllegalAmountException, IllegalCurrencyException, ISOException;;
}
