package ir.gnrco.credit.card.parser.enums;


import java.util.Arrays;

/**
 * @author Behrooz Mohamadi
 * @email behroooz.mohamadi@gmail.com
 * @date 7/17/2019
 */
public enum TxProcessCodeType  {

    /**
     * خرید کالا و خدمات
     */
    PURCHASE(0,000000L, TxProcessCodeType.PURCHASE_CAPTION),
    /**
     * دریافت وجه نقد
     */
    CASH_RECEIVE(1,10000L, TxProcessCodeType.CASH_RECEIVE_CAPTION),
    /**
     * کسر وجه از کارت برای شارژ کیوا
     */
    MONEY_DEDUCTION_FROM_CARD_FOR_KIVA(2,130000L, TxProcessCodeType.MONEY_DEDUCTION_FROM_CARD_FOR_KIVA_CAPTION),
    /**
     * برگشت از خرید
     */
    RETURN_FROM_PURCHASE(3,200000L, TxProcessCodeType.RETURN_FROM_PURCHASE_CAPTION),
    /**
     * واریز وجه دشارژ شده کیوا به کارت
     */
    DEPOSIT_MONEY_DECHARGED_KIVA_TO_CARD(4,276000L, TxProcessCodeType.DEPOSIT_MONEY_DECHARGED_KIVA_TO_CARD_CAPTION),
    /**
     * واریز وجه شارژ به کیوا
     */
    DEPOSIT_CASH_CHARGED_TO_KIVA(5,280060L, TxProcessCodeType.DEPOSIT_CASH_CHARGED_TO_KIVA_CAPTION),
    /**
     * درخواست مانده حساب
     */
    REQUEST_REMAINING_ACCOUNT(6,310000L, TxProcessCodeType.REQUEST_REMAINING_ACCOUNT_CAPTION),
    /**
     * درخواست بررسی کارت و حساب
     */
    REQUEST_CHECK_CARD_ACCOUNT(7,330000L, TxProcessCodeType.REQUEST_CHECK_CARD_ACCOUNT_CAPTION),
    /**
     * درخواست بررسی کیوا
     */
    REQUEST_CHECK_KIVA(8,336000L, TxProcessCodeType.REQUEST_CHECK_KIVA_CAPTION),
    /**
     * درخواست چکیده صورت حساب
     */
    REQUEST_BILLING_SUMMARY(9,340000L, TxProcessCodeType.REQUEST_BILLING_SUMMARY_CAPTION),
    /**
     * درخواست انتقال وجه بین دوکارت
     */
    REQUEST_TRANSFERRING_BETWEEN_CARDS(10,400000L, TxProcessCodeType.REQUEST_TRANSFERRING_BETWEEN_CARDS_CAPTION),
    /**
     * انتقال وجه از
     */
    TRANSFERRING_FUND_FROM(11,460000L, TxProcessCodeType.TRANSFERRING_FUND_FROM_CAPTION),
    /**
     * انتقال وجه به
     */
    TRANSFERRING_FUND_TO(12,470000L, TxProcessCodeType.TRANSFERRING_FUND_TO_CAPTION),
    /**
     * پرداخت
     */
    PAYMENT(13,500000L, TxProcessCodeType.PAYMENT_CAPTION),
    /**
     * درخواست شارژ کیپا
     */
    REQUEST_CHARGE_KIPA(14,600060L, TxProcessCodeType.REQUEST_CHARGE_KIPA_CAPTION),
    /**
     * درخواست تایید رمز کارت
     */
    REQUEST_CARD_CONFIRM_PASSWORD(15,710000L, TxProcessCodeType.REQUEST_CARD_CONFIRM_PASSWORD_CAPTION),
    /**
     * رفع یا اصلاح مغایرت
     */
    FIX_MODIFY_RECONCILIATION(16,900000L, TxProcessCodeType.FIX_MODIFY_RECONCILIATION_CAPTION);

    TxProcessCodeType(Integer id,Long code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
    }

    private Integer id;
    private Long code;
    private String title;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String code) {
        this.title = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static TxProcessCodeType findByCode(Long code) {
        return Arrays.asList(values()).stream()
                .filter(txProcessCodeType -> txProcessCodeType.code.equals(code))
                .findFirst().orElse(null);
    }

    public static TxProcessCodeType findById(Integer id) {
        return Arrays.asList(values()).stream()
                .filter(txProcessCodeType -> txProcessCodeType.id.equals(id))
                .findFirst().orElse(null);
    }

    public Long getUniqueId() {
        return id.longValue();
    }

    private static final String PURCHASE_CAPTION = "CC.TxProcessCodeType.PURCHASE";
    private static final String CASH_RECEIVE_CAPTION = "CC.TxProcessCodeType.CASH_RECEIVE";
    private static final String MONEY_DEDUCTION_FROM_CARD_FOR_KIVA_CAPTION = "CC.TxProcessCodeType.MONEY_DEDUCTION_FROM_CARD_FOR_KIVA";
    private static final String RETURN_FROM_PURCHASE_CAPTION = "CC.TxProcessCodeType.RETURN_FROM_PURCHASE";
    private static final String DEPOSIT_MONEY_DECHARGED_KIVA_TO_CARD_CAPTION = "CC.TxProcessCodeType.DEPOSIT_MONEY_DECHARGED_KIVA_TO_CARD";
    private static final String DEPOSIT_CASH_CHARGED_TO_KIVA_CAPTION = "CC.TxProcessCodeType.DEPOSIT_CASH_CHARGED_TO_KIVA";
    private static final String REQUEST_REMAINING_ACCOUNT_CAPTION = "CC.TxProcessCodeType.REQUEST_REMAINING_ACCOUNT";
    private static final String REQUEST_CHECK_CARD_ACCOUNT_CAPTION = "CC.TxProcessCodeType.REQUEST_CHECK_CARD_ACCOUNT";
    private static final String REQUEST_CHECK_KIVA_CAPTION = "CC.TxProcessCodeType.REQUEST_CHECK_KIVA";
    private static final String REQUEST_BILLING_SUMMARY_CAPTION = "CC.TxProcessCodeType.REQUEST_BILLING_SUMMARY";
    private static final String REQUEST_TRANSFERRING_BETWEEN_CARDS_CAPTION = "CC.TxProcessCodeType.REQUEST_TRANSFERRING_BETWEEN_CARDS";
    private static final String TRANSFERRING_FUND_FROM_CAPTION = "CC.TxProcessCodeType.TRANSFERRING_FUND_FROM";
    private static final String TRANSFERRING_FUND_TO_CAPTION = "CC.TxProcessCodeType.TRANSFERRING_FUND_TO";
    private static final String PAYMENT_CAPTION = "CC.TxProcessCodeType.PAYMENT";
    private static final String REQUEST_CHARGE_KIPA_CAPTION = "CC.TxProcessCodeType.REQUEST_CHARGE_KIPA";
    private static final String REQUEST_CARD_CONFIRM_PASSWORD_CAPTION = "CC.TxProcessCodeType.REQUEST_CARD_CONFIRM_PASSWORD";
    private static final String FIX_MODIFY_RECONCILIATION_CAPTION = "CC.TxProcessCodeType.FIX_MODIFY_RECONCILIATION";

}
