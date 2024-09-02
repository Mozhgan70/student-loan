package entity.enumration;

public enum Bank {
    MELLI("ملی","603799"),
    REFAH("رفاه","589463"),
    MASKAN("مسکن","628023"),
    TEJARAT("تجارت","627353");

    private final String bankName;
    private final String preNumber;

    Bank(String bankName,String preNumber) {
        this.bankName = bankName;
        this.preNumber=preNumber;
    }


    public String getBankName() {
        return bankName;
    }

    public String getPreNumber() {
        return preNumber;
    }
}
