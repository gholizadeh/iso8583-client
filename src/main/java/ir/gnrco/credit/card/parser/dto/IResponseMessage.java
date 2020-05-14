package ir.gnrco.credit.card.parser.dto;

import ir.gnrco.credit.card.parser.enums.TxProcessStatusType;

/**
 * For all response messages
 * @author sa.gholizadeh <sa.gholizadeh@yahoo.com>
 */
public interface IResponseMessage extends IMessage{
    public TxProcessStatusType getTXResponseCode();
    public void setTXResponseCode(TxProcessStatusType TXResponseCode);
}
