package entity.enumration;

public enum City
{
    TEHRAN("Tehran", "Tehran"),
    GILAN("Gilan", "Metropolitan"),
    ISFAHAN("Isfahan", "Metropolitan"),
    EAST_AZERBAIJAN("East Azerbaijan", "Metropolitan"),
    FARS("Fars", "Metropolitan"),
    KHUZESTAN("Khuzestan", "Metropolitan"),
    QOM("Qom", "Metropolitan"),
    KHORASAN_RAZAVI("Khorasan Razavi", "Metropolitan"),
    ALBORZ("Alborz", "Metropolitan"),
    OTHER("Other Cities", "Other");

    private final String cityName;
    private final String category;

    City(String cityName, String category) {
        this.cityName = cityName;
        this.category = category;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCategory() {
        return category;
    }
}
