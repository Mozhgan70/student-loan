package entity.enumration;

public enum EducationGrade {
    ASSOCIATE_DEGREE("کاردانی"),
    BACHELORS_CONTINUOUS("کارشناسی پیوسته"),
    BACHELORS_DISCONTINUOUS("کارشناسی ناپیوسته"),
    MASTERS_CONTINUOUS("کارشناسی ارشد پیوسته"),
    MASTERS_DISCONTINUOUS("کارشناسی ارشد ناپیوسته"),
    PROFESSIONAL_DOCTORATE("دکترای حرفه‌ای"),
    DOCTORATE_CONTINUOUS("دکترای پیوسته"),
    PHD_DISCONTINUOUS("دکتری تخصصی ناپیوسته");

    private final String persianName;

    // Constructor
    EducationGrade(String persianName) {
        this.persianName = persianName;
    }

    // Getter
    public String getPersianName() {
        return persianName;
    }
}
