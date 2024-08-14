package entity.enumration;

public enum PaymentState {

    DUE("سررسید شده"),
    PAID("پرداخت شده"),
    DUE_NOT_PAID("سررسید شده پرداخت نشده"),
    NOT_DUE("سررسید نشده");

    private final String persianName;


    PaymentState(String persianName) {
        this.persianName = persianName;
    }


    public String getPersianName() {
        return persianName;
    }
}
