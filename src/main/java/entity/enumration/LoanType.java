package entity.enumration;

public enum LoanType {
    TUITION_FEE_LOAN("وام شهریه"),
    EDUCATION_LOAN("وام تحصیلی"),
    HOUSING_LOAN("وام ودیعه مسکن");

    private final String persianName;


    LoanType(String persianName) {
        this.persianName = persianName;
    }

    public String getPersianName() {
        return persianName;
    }
}
