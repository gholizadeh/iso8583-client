package ir.gnrco.credit.card.parser;

import ir.gnrco.credit.card.parser.dto.IMessage;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * deprecated old factory class to parse messages (used .xml to pars and also reflection)
 * @todo (do not use it - remove)
 */
@Deprecated
public class UnpackISOMessageXml {

    public IMessage parseISOMessage(String message) throws Exception {
        try {
            // Load package from resources directory.
            InputStream is = getClass().getResourceAsStream("/fields.xml");
            GenericPackager packager = new GenericPackager(is);
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(message.getBytes());

            IMessage messageDto = instantiate(isoMsg);

            return messageDto;
        } catch (ISOException e) {
            throw new Exception(e);
        }
    }

    private IMessage instantiate(ISOMsg isoMsg) throws ClassNotFoundException, IllegalAccessException, InstantiationException, ISOException, NoSuchMethodException, InvocationTargetException {
        Constructor c = Class.forName("ir.gnrco.credit.card.parser.dto.Message" + isoMsg.getMTI()).getConstructor(ISOMsg.class);
        IMessage obj = (IMessage) c.newInstance(isoMsg);

        return obj;
    }

}