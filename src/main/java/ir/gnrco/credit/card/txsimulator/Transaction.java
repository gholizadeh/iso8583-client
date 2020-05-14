package ir.gnrco.credit.card.txsimulator;


import ir.gnrco.credit.card.parser.enums.MessageType;
import ir.gnrco.credit.card.parser.enums.TxProcessCodeType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

public class Transaction implements Comparable<Transaction> {

	private String name;
	private BigDecimal amount;
	private Date date;
	private Long cardNo;
	private TxProcessCodeType txProcessCode;
	private MessageType messageType;
	private Long txId;
	private String ip;
	private Integer port;

	public Transaction() {
	}

	public Transaction(String name, BigDecimal amount, Date date, Long cardNo, TxProcessCodeType txProcessCode, MessageType messageType, String ip, Integer port, Long txId) {
		this.name = name;
		this.amount = amount;
		this.date = date;
		this.cardNo = cardNo;
		this.txProcessCode = txProcessCode;
		this.messageType = messageType;
		this.ip = ip;
		this.port = port;
		if(txId instanceof Long) {
            this.txId = txId;
        }else {
            Random rnd = new Random();
            this.txId = Long.valueOf(100000 + rnd.nextInt(900000));
        }
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getCardNo() {
		return cardNo;
	}

	public void setCardNo(Long cardNo) {
		this.cardNo = cardNo;
	}

	public TxProcessCodeType getTxProcessCode() {
		return txProcessCode;
	}

	public void setTxProcessCode(TxProcessCodeType txProcessCode) {
		this.txProcessCode = txProcessCode;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

    public Long getTxId() {
        return txId;
    }

    public void setTxId(Long txId) {
        this.txId = txId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
	public String toString() {
		return name + "(" + cardNo + ")";
	}


	public int compareTo(Transaction other) {
		return this.name.compareTo(other.getName());
	}
}