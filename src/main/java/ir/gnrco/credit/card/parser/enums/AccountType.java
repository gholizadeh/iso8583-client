package ir.gnrco.credit.card.parser.enums;

import java.util.Arrays;

/**
 * @author sa.gholizadeh [gholizade.saeed@yahoo.com]
 */
public enum AccountType {

    /**
     * حساب ترجیحی
     */
    PREFERRED_ACCOUNT(0L, AccountType.preferredAccount),
    /**
     * حساب کیف پول الکترونیکی
     */
    ELECTRONIC_WALLET_ACCOUNT(60L, AccountType.electronicWalletAccount);

    AccountType(Long code, String title) {
        this.code = code;
        this.title = title;
    }

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

    public static AccountType findByCode(Long code) {
        return Arrays.asList(values()).stream()
                .filter(type -> type.getCode().equals(code))
                .findFirst().orElse(null);
    }

    public static AccountType findByTitle(String title) {
        return Arrays.asList(values()).stream()
                .filter(type -> type.getTitle().equals(title))
                .findFirst().orElse(null);
    }

    public Long getUniqueId() {
        return code;
    }

    private static final String preferredAccount = "CC.AccountType.preferredAccount";
    private static final String electronicWalletAccount = "CC.AccountType.electronicWalletAccount";
}
