package ir.gnrco.credit.card.parser.helper.iransystem;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 *
 * @author sa.gholizadeh
 * @todo should be tested before use. there was no data set to test
 */
public class IranSystemConverter {

    private static IranSystemCharsetProvider provider = new IranSystemCharsetProvider();

    public static String convert(String inputMessage) throws UnsupportedEncodingException {
        Charset charset = provider.charsetForName("IranSystem");
        // append a string into StringBuilder input
        StringBuilder input = new StringBuilder();
        input.append(inputMessage);
        // reverse StringBuilder input
        inputMessage = input.reverse().toString();
        return new String(inputMessage.getBytes("Windows-1256"), charset);
    }

    public static String convertToIranSystem(String inputMessage) throws UnsupportedEncodingException {
        Charset charset = provider.charsetForName("IranSystem");
        return new String(inputMessage.getBytes(charset));
    }

}