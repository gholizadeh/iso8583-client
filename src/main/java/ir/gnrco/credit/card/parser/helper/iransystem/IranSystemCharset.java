package ir.gnrco.credit.card.parser.helper.iransystem;

import java.nio.CharBuffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.HashMap;

import java.util.logging.Logger;

/**
 * A Charset implementation for IranSystem default and extended character set
 *
 * @author sa.gholizadeh
 */
public class IranSystemCharset extends Charset {

    private boolean debug = false;

    // HashMap's used for encoding and decoding
    protected static HashMap<String, Byte> defaultEncodeMap = new HashMap<String, Byte>();
    protected static HashMap<Byte, String> defaultDecodeMap = new HashMap<Byte, String>();
    protected static HashMap<String, Byte> extEncodeMap = new HashMap<String, Byte>();
    protected static HashMap<Byte, String> extDecodeMap = new HashMap<Byte, String>();

    // Data to populate the HashMaps with
    //      character, byte code, int(ASCII), UTF-8
    private static final Object[][] iranSystemCharacters = {
            { "۰",  (byte) 0x80, 128, '\u06F0', "۰" },
            { "۱",  (byte) 0x81, 129, '\u06F1', "۱" },
            { "۲",  (byte) 0x82, 130, '\u06F2', "۲" },
            { "۳",  (byte) 0x83, 131, '\u06F3', "۳" },
            { "۴",  (byte) 0x84, 132, '\u06F4', "۴" },
            { "۵",  (byte) 0x85, 133, '\u06F5', "۵" },
            { "۶",  (byte) 0x86, 134, '\u06F6', "۶" },
            { "۷",  (byte) 0x87, 135, '\u06F7', "۷" },
            { "۸",  (byte) 0x88, 136, '\u06F8', "۸" },
            { "۹",  (byte) 0x89, 137, '\u06F9', "۹" },
            { "،",  (byte) 0x8a, 138, '\u060C', "،" },
            { "ـ",  (byte) 0x8b, 139, '\u0640', "_" },
            { "؟",  (byte) 0x8c, 140, '\u061F', "؟" },
            { "ﺁ",  (byte) 0x8d, 141, '\uFE81', "آ" },
            { "ﺋ",  (byte) 0x8e, 142, '\uFE8B', "ئ" },
            { "ء",  (byte) 0x8f, 143, '\u0621', "ء" },
            { "ﺍ",  (byte) 0x90, 144, '\uFE8D', "ا" },
            { "ﺎ",  (byte) 0x91, 145, '\uFE8E', "ا" },
            { "ﺏ",  (byte) 0x92, 146, '\uFE8F', "ب" },
            { "ﺑ",  (byte) 0x93, 147, '\uFE91', "ب" },
            { "ﭖ",  (byte) 0x94, 148, '\uFB56', "پ" },
            { "ﭘ",  (byte) 0x95, 149, '\uFB58', "پ" },
            { "ﺕ",  (byte) 0x96, 150, '\uFE95', "ت" },
            { "ﺗ",  (byte) 0x97, 151, '\uFE97', "ت" },
            { "ﺙ",  (byte) 0x98, 152, '\uFE99', "ث" },
            { "ﺛ",  (byte) 0x99, 153, '\uFE9B', "ث" },
            { "ﺝ",  (byte) 0x9a, 154, '\uFE9D', "ج" },
            { "ﺟ",  (byte) 0x9b, 155, '\uFE9F', "ج" },
            { "ﭺ",  (byte) 0x9c, 156, '\uFB7A', "چ" },
            { "ﭼ",  (byte) 0x9d, 157, '\uFB7C', "چ" },
            { "ﺡ",  (byte) 0x9e, 158, '\uFEA1', "ح" },
            { "ﺣ",  (byte) 0x9f, 159, '\uFEA3', "ح" },
            { "خ",  (byte) 0xa0, 160, '\uFEA5', "خ" },
            { "ﺧ",  (byte) 0xa1, 161, '\uFEA7', "خ" },
            { "د",  (byte) 0xa2, 162, '\u062F', "د" },
            { "ذ",  (byte) 0xa3, 163, '\u0630', "ذ" },
            { "ر",  (byte) 0xa4, 164, '\u0631', "ر" },
            { "ز",  (byte) 0xa5, 165, '\u0632', "ز" },
            { "ژ",  (byte) 0xa6, 166, '\u0698', "ژ" },
            { "ﺱ",  (byte) 0xa7, 167, '\uFEB1', "س" },
            { "ﺳ",  (byte) 0xa8, 168, '\uFEB3', "س" },
            { "ﺵ",  (byte) 0xa9, 169, '\uFEB5', "ش" },
            { "ﺷ",  (byte) 0xaa, 170, '\uFEB7', "ش" },
            { "ﺹ",  (byte) 0xab, 171, '\uFEB9', "ص" },
            { "ﺻ",  (byte) 0xac, 172, '\uFEBB', "ص" },
            { "ﺽ",  (byte) 0xad, 173, '\uFEBD', "ض" },
            { "ﺿ",  (byte) 0xae, 174, '\uFEBF', "ض" },
            { "ط",  (byte) 0xaf, 175, '\u0637', "ط" },
            { "ظ",  (byte) 0xe0, 224, '\u0638', "ظ" },
            { "ﻉ",  (byte) 0xe1, 225, '\uFEC9', "ع" },
            { "ﻊ",  (byte) 0xe2, 226, '\uFECA', "ع" },
            { "ﻌ",  (byte) 0xe3, 227, '\uFECC', "ع" },
            { "ﻋ",  (byte) 0xe4, 228, '\uFECB', "ع" },
            { "ﻍ",  (byte) 0xe5, 229, '\uFECD', "غ" },
            { "ﻎ",  (byte) 0xe6, 230, '\uFECE', "غ" },
            { "ﻐ",  (byte) 0xe7, 231, '\uFED0', "غ" },
            { "ﻏ",  (byte) 0xe8, 232, '\uFECF', "غ" },
            { "ﻑ",  (byte) 0xe9, 233, '\uFED1', "ف" },
            { "ﻓ",  (byte) 0xea, 234, '\uFED3', "ف" },
            { "ﻕ",  (byte) 0xeb, 235, '\uFED5', "ق" },
            { "ﻗ",  (byte) 0xec, 236, '\uFED7', "ق" },
            { "ﮎ",  (byte) 0xed, 237, '\uFB8E', "ک" },
            { "ﮐ",  (byte) 0xee, 238, '\uFB90', "ک" },
            { "ﮒ",  (byte) 0xef, 239, '\uFB92', "گ" },
            { "ﮔ",  (byte) 0xf0, 240, '\uFB94', "گ" },
            { "ﻝ",  (byte) 0xf1, 241, '\uFEDD', "ل" },
            { "لا",  (byte) 0xf2, 242, '\uFEFB', "لا" },
            { "ﻟ",  (byte) 0xf3, 243, '\uFEDF', "ل" },
            { "ﻡ",  (byte) 0xf4, 244, '\uFEE1', "م" },
            { "ﻣ",  (byte) 0xf5, 245, '\uFEE3', "م" },
            { "ﻥ",  (byte) 0xf6, 246, '\uFEE5', "ن" },
            { "ﻧ",  (byte) 0xf7, 247, '\uFEE7', "ن" },
            { "و",  (byte) 0xf8, 248, '\u0648', "و" },
            { "ه",  (byte) 0xf9, 249, '\uFEE9', "ه" },
            { "ﻬ",  (byte) 0xfa, 250, '\uFEEC', "ه" },
            { "ﻫ",  (byte) 0xfb, 251, '\uFEEB', "ه" },
            { "ﯽ",  (byte) 0xfc, 252, '\uFBFD', "ی" },
            { "ﯼ",  (byte) 0xfd, 253, '\uFBFC', "ی" },
            { "ﯾ",  (byte) 0xfe, 254, '\uFBFE', "ی" },
            { " ",  (byte) 0xff, 255, '\u00A0', " " }
    };

    private static Logger logger = Logger.getLogger(IranSystemCharset.class.getName());

    // static section that populates the encode and decode HashMap objects
    static {
        // default alphabet
        int len = iranSystemCharacters.length;
        for (int i = 0; i < len; i++) {
            Object[] map = iranSystemCharacters[i];
            defaultEncodeMap.put((String) map[4], (Byte) map[1]);
            defaultDecodeMap.put((Byte) map[1], (String) map[4]);
        }
    }

    /**
     * Constructor for the IranSystem charset.  Call the superclass
     * constructor to pass along the name(s) we'll be known by.
     * Then save a reference to the delegate Charset.
     */
    protected IranSystemCharset(String canonical, String[] aliases) {
        super(canonical, aliases);
    }

    // ----------------------------------------------------------

    /**
     * Called by users of this Charset to obtain an encoder.
     * This implementation instantiates an instance of a private class
     * (defined below) and passes it an encoder from the base Charset.
     */
    public CharsetEncoder newEncoder() {
        return new IranSystemEncoder(this);
    }

    /**
     * Called by users of this Charset to obtain a decoder.
     * This implementation instantiates an instance of a private class
     * (defined below) and passes it a decoder from the base Charset.
     */
    public CharsetDecoder newDecoder() {
        return new IranSystemDecoder(this);
    }

    /**
     * This method must be implemented by concrete Charsets.  We always
     * say no, which is safe.
     */
    public boolean contains(Charset cs) {
        return (false);
    }

    /**
     * The encoder implementation for the IranSystem Charset.
     * This class, and the matching decoder class below, should also
     * override the "impl" methods, such as implOnMalformedInput() and
     * make passthrough calls to the baseEncoder object.  That is left
     * as an exercise for the hacker.
     */
    private class IranSystemEncoder extends CharsetEncoder {

        /**
         * Constructor, call the superclass constructor with the
         * Charset object and the encodings sizes from the
         * delegate encoder.
         */
        IranSystemEncoder(Charset cs) {
            super(cs, 1, 2);
        }

        /**
         * Implementation of the encoding loop.
         */
        protected CoderResult encodeLoop(CharBuffer cb, ByteBuffer bb) {
            CoderResult cr = CoderResult.UNDERFLOW;

            while (cb.hasRemaining()) {
                if (!bb.hasRemaining()) {
                    cr = CoderResult.OVERFLOW;
                    break;
                }
                char ch = cb.get();

                // first check the default alphabet
                Byte b = (Byte) defaultEncodeMap.get("" + ch);
                if(debug)
                    logger.finest("Encoding ch " + ch + " to byte " + b);
                if (b != null) {
                    bb.put((byte) b.byteValue());
                } else {
                    // no match found, send a ?
                    b = new Byte((byte) 0x3F);
                    bb.put((byte) b.byteValue());
                }
            }
            return cr;
        }
    }

    // --------------------------------------------------------

    /**
     * The decoder implementation for the Gsm 7Bit Charset.
     */
    private class IranSystemDecoder extends CharsetDecoder {

        /**
         * Constructor, call the superclass constructor with the
         * Charset object and pass alon the chars/byte values
         * from the delegate decoder.
         */
        IranSystemDecoder(Charset cs) {
            super(cs, 1, 1);
        }

        /**
         * Implementation of the decoding loop.
         */
        protected CoderResult decodeLoop(ByteBuffer bb, CharBuffer cb) {
            CoderResult cr = CoderResult.UNDERFLOW;

            while (bb.hasRemaining()) {
                if (!cb.hasRemaining()) {
                    cr = CoderResult.OVERFLOW;
                    break;
                }
                byte b = bb.get();

                // first check the default alphabet
                if(debug)
                    logger.finest("Looking up byte " + b);

                int ib = b & 0xFF;
                if(ib > 0x80) {
                    String s = (String) defaultDecodeMap.get(new Byte(b));
                    if (s != null) {
                        char ch = s.charAt(0);

                        if (debug)
                            logger.finest("Found string " + s);
                        cb.put(ch);

                    } else {
                        cb.put('?');
                    }
                }else{
                    cb.put((char)b);
                }
            }
            return cr;
        }
    }
}