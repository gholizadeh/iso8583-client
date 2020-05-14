package ir.gnrco.credit.card.parser.helper.iransystem;

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.HashSet;
import java.util.Iterator;

/**
 * This is a CharsetProvider for the IranSystem character set.
 *
 * To activate this CharsetProvider, it's necessary to add a file to
 * the classpath of the JVM runtime at the following location:
 *   META-INF/services/java.nio.charsets.spi.CharsetProvider
 *
 * That file must contain a line with the fully qualified name of
 * this class on a line by itself:
 *   ir.gnrco.credit.card.parser.helper.iransystem.IranSystemCharsetProvider
 *
 * See the javadoc page for java.nio.charsets.spi.CharsetProvider
 * for full details.
 *
 * @author Sa.gholizadeh
 */
public class IranSystemCharsetProvider extends CharsetProvider {

    // The name of the charset we provide
    private static final String CHARSET_NAME = "IranSystem";

    // A handle to the Charset object
    private Charset iranSystem = null;

    private boolean debug = false;

    /**
     * Constructor, instantiate a Charset object and save the reference.
     */
    public IranSystemCharsetProvider() {
        super();
        if(debug)
            System.out.println("Instansiating " + CHARSET_NAME);
        this.iranSystem = new IranSystemCharset(CHARSET_NAME, null);
    }

    /**
     * Called by Charset static methods to find a particular named
     * Charset.  If it's the name of this charset (we don't have
     * any aliases) then return the Rot13 Charset, else return null.
     */
    public Charset charsetForName (String charsetName) {
        if(charsetName.equalsIgnoreCase(CHARSET_NAME)) {
            return(iranSystem);
        }
        return(null);
    }

    /**
     * Return an Iterator over the set of Charset objects we provide.
     * @return An Iterator object containing references to all the
     *  Charset objects provided by this class.
     */
    public Iterator<Charset> charsets() {
        HashSet<Charset> set = new HashSet<Charset>(1);
        set.add(iranSystem);
        return(set.iterator());
    }
}