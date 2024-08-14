package entity.enumration;

public enum Bank {
    MELLI("ملی"),
    REFAH("رفاه"),
    MASKAN("مسکن"),
    TEJARAT("تجارت");

    private final String bankName;


    Bank(String bankName) {
        this.bankName = bankName;
    }


    public String getBankName() {
        return bankName;
    }
}
