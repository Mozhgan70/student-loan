package entity.enumration;

public enum UniversityType {

    SHABANEH("شبانه"),
    NON_PROFIT_UNIVERSITY("دانشگاه‌های غیرانتفاعی"),
    PARDIS("پردیس"),
    EXCESS_CAPACITY("ظرفیت مازاد"),
    PAYAM_NOOR("پیام نور"),
    APPLIED_SCIENCE("علمی کاربردی"),
    AZAD_UNIVERSITY("دانشگاه آزاد");

    private final String persianName;


    UniversityType(String persianName) {
        this.persianName = persianName;
    }


    public String getPersianName() {
        return persianName;
    }
}
