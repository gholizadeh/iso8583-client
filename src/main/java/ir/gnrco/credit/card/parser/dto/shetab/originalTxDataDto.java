package ir.gnrco.credit.card.parser.dto.shetab;

import ir.gnrco.credit.card.parser.dto.IMessageParser;
import org.jpos.iso.ISOMsg;

/**
 * this class is to get original transaction data
 * will use in return transactions and have aggregation with 1220 and 1420 series
 */
public class originalTxDataDto implements IMessageParser {
    /**
     * it would keep address of isoMsg to set and get field from it through IMessageParser
     */
    private ISOMsg isoMsg;

    @Override
    public ISOMsg getIsoMsg() {
        return isoMsg;
    }

    public void setIsoMsg(ISOMsg isoMsg) {
        this.isoMsg = isoMsg;
    }
}
