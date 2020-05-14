package ir.gnrco.credit.card.parser;

import ir.gnrco.credit.card.parser.dto.IMessage;
import ir.gnrco.credit.card.parser.packer.ISO93APooyaPackager;
import org.jpos.iso.ISOMsg;

/**
 * @author sa.gholizadeh [gholizade.saeed@yahoo.com]
 */
public class UnpackStringISOMessage {

    /**
     * To unpack pos string message.
     * @param message String (standard ISO 8583 message without header)
     * @return IMessage (a message impl dto according to MTI)
     */
    public IMessage parseISOMessage(String message) throws Exception {
        ISO93APooyaPackager packager = new ISO93APooyaPackager();
        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(packager);
        isoMsg.unpack(message.getBytes());

        IMessage messageDto = UnpackISOMessage.parseISOMessage(isoMsg);

        return messageDto;
    }
}