package ir.gnrco.credit.card.parser.dto;

public interface IReturnRequestFinRev extends IRequestMessageFinAuthRev{
    public OriginalTx getOriginalTxData();
}
