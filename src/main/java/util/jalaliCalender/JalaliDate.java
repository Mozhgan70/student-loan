package util.jalaliCalender;


public class JalaliDate {
    public Integer Year;
    public Integer Month;
    public Integer Day;
    public Integer Hour;
    public Integer Minute;
    public Integer Second;
    public JalaliDate() {
    }
    public JalaliDate(int year, int month, int day, int hour, int minute, int second) {
        Year = year;
        Month = month;
        Day = day;
        Hour = hour;
        Minute = minute;
        Second = second;
    }

    /*public ShamsyDateTime AddDays(int days)
    {
        DateTime dt = JalaliDateUtil.ShamsyToMilady(this);
        //dt.ad
        dt = JalaliDateUtil.IncrementDateTime(dt, 0, 0, days);
        return JalaliDateUtil.MiladyToShamsy(dt);
    }*/
    public static String padleft(String s, int len, char c) {
        if (s.length() > len)
            return s;
        StringBuilder d = new StringBuilder(len);
        int fill = len - s.length();
        while (fill-- > 0)
            d.append(c);
        d.append(s);
        return d.toString();
    }


    public String getFullDateString() {
        return toString("YYYY/MM/dd HH:mm:ss");
    }

    @Override
    public String toString() {
        return toString("YYYY/MM/dd");
    }

    public String toString(String pattern) {
        String rVal = pattern;
        if (pattern.indexOf("YYYY") >= 0) {
            rVal = rVal.replace("YYYY", padleft(Year.toString(), 4, '0'));
        }
        if (pattern.indexOf("YY") >= 0) {
            rVal = rVal.replace("YY", Year.toString().substring(2));
        }
        if (pattern.indexOf("yy") >= 0) {
            rVal = rVal.replace("yy", Year.toString().substring(2));
        }

        if (pattern.indexOf("yyyy") >= 0) {
            rVal = rVal.replace("yyyy", padleft(Year.toString(), 4, '0'));
        }
        if (pattern.indexOf("MM") >= 0) {
            rVal = rVal.replace("MM", padleft(Month.toString(), 2, '0'));
        }
        if (pattern.indexOf("dd") >= 0) {
            rVal = rVal.replace("dd", padleft(Day.toString(), 2, '0'));
        }
        if (pattern.indexOf("HH") >= 0) {
            rVal = rVal.replace("HH", padleft(Hour.toString(), 2, '0'));
        }
        if (pattern.indexOf("hh") >= 0) {
            rVal = rVal.replace("hh", padleft(Hour >= 12 ? ((Integer) (Hour - 12)).toString() : Hour.toString(), 2, '0'));
        }

        if (pattern.indexOf("mm") >= 0) {
            rVal = rVal.replace("mm", padleft(Minute.toString(), 2, '0'));
        }
        if (pattern.indexOf("ss") >= 0) {
            rVal = rVal.replace("ss", padleft(Second.toString(), 2, '0'));
        }
        return rVal;
    }

}
