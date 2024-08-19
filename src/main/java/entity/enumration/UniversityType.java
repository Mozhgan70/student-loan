package entity.enumration;

public enum UniversityType {

    Rozane("روزانه"),
    SHABANEH("شبانه"),
    GHEIRE_ENTEFAEI("دانشگاه‌های غیرانتفاعی"),
    PARDIS("پردیس"),
    ZARFIAT_MAZAD("ظرفیت مازاد"),
    PAYAM_NOOR("پیام نور"),
    ELMI_KARBORDI("علمی کاربردی"),
    AZAD_UNIVERSITY("دانشگاه آزاد");

    private final String persianName;


    UniversityType(String persianName) {
        this.persianName = persianName;
    }


    public String getPersianName() {
        return persianName;
    }
}
