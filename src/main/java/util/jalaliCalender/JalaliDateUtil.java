package util.jalaliCalender;

import java.util.Calendar;
import java.util.Date;


public class JalaliDateUtil {
    private static final int KW_NATURAL = 0;
    private static final int KW_KABISSE = 1;
    static int[] Khayyam_Table = new int[]{
            5, 9, 13, 17, 21, 25, 29,
            34, 38, 42, 46, 50, 54, 58, 62,
            67, 71, 75, 79, 83, 87, 91, 95,
            100, 104, 108, 112, 116, 120, 124
    };
    static short[] month = new short[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static Calendar ShamsyToMilady(JalaliDate jalaliDateTime)        //Shamsy to milady conversion
    {
        int aYear = jalaliDateTime.Year;
        int aMonth = jalaliDateTime.Month;
        int aDay = jalaliDateTime.Day;
        int aHour = jalaliDateTime.Hour;
        int aMin = jalaliDateTime.Minute;
        int aSec = jalaliDateTime.Second;

        int Year = aYear, Month, Day;

        int EY = ExceptionYear(Year);

        int yDay = aMonth < 7 ? ((aMonth - 1) * 31 + aDay) : (186 + (aMonth - 7) * 30 + aDay);
        if (yDay > 286 - EY) {
            Year += 622;
            yDay -= 286 - EY;
        } else {
            Year += 621;
            yDay += 79 + EY + (Miladi_Kabisse(Year) == KW_KABISSE ? 1 : 0);
        }

        short MK = Miladi_Kabisse(Year) == KW_KABISSE ? (short) 1 : (short) 0;
        short myDay = (short) (MK + 28);
        short[] newMonthArray = new short[month.length];
        for (int newMonthIdx = 0; newMonthIdx < newMonthArray.length; newMonthIdx++)
            newMonthArray[newMonthIdx] = month[newMonthIdx];
        newMonthArray[1] = myDay;

        for (Month = 0; yDay > newMonthArray[Month]; Month++)
            yDay -= newMonthArray[Month];

        Month++;
        Day = yDay;
        Calendar cl = Calendar.getInstance();
        cl.set(Year, Month - 1, Day, aHour, aMin, aSec);
        return cl;
    }

    public static int DaysOfShamsyYear(int aYear) {
        return Shamsi_Kabisse(aYear) == KW_NATURAL ? 365 : 366;
    }

    private static Calendar DecDate(Calendar baseDateTime, int aYear, int aMonth, int aDay) {
        int year, month, day;
        JalaliDate shDateTime = MiladyToShamsy(baseDateTime);
        year = shDateTime.Year;
        month = shDateTime.Month;
        day = shDateTime.Day;

        boolean bMnthLastDay = false;
        if (day == DaysOfShamsyMonth(year, month))
            bMnthLastDay = true;

        year -= aYear;
        month -= aMonth;
        while (month < 1) {
            month += 12;
            year--;
        }
        if (day > DaysOfShamsyMonth(year, month) || bMnthLastDay)
            day = DaysOfShamsyMonth(year, month);

        while (aDay >= day) {
            aDay -= day;
            if (--month < 1) {
                year--;
                month = 12;
            }
            day = DaysOfShamsyMonth(year, month);
        }

        day -= aDay;
        JalaliDate result = new JalaliDate();
        result.Year = year;
        result.Month = month;
        result.Day = day;
        result.Second = baseDateTime.get(Calendar.SECOND);
        result.Hour = baseDateTime.get(Calendar.HOUR_OF_DAY);
        result.Minute = baseDateTime.get(Calendar.MINUTE);

        return ShamsyToMilady(result);
    }

    private static Calendar IncDate(Calendar baseDateTime, int aYear, int aMonth, int aDay) {

        int year, month, day;
        JalaliDate shDateTime = MiladyToShamsy(baseDateTime);
        year = shDateTime.Year;
        month = shDateTime.Month;
        day = shDateTime.Day;

        boolean isMonthLastDay = false;
        if (day == DaysOfShamsyMonth(year, month))
            isMonthLastDay = true;

        year += aYear;
        month += aMonth;
        while (month > 12) {
            month -= 12;
            year++;
        }
        if (day > DaysOfShamsyMonth(year, month) || isMonthLastDay)
            day = DaysOfShamsyMonth(year, month);

        while (aDay > (DaysOfShamsyMonth(year, month) - day)) {
            aDay -= DaysOfShamsyMonth(year, month) - day + 1;
            if (++month > 12) {
                year++;
                month = 1;
            }
            day = 1;
        }
        day += aDay;
        JalaliDate resultDateTime = new JalaliDate();
        resultDateTime.Year = year;
        resultDateTime.Month = month;
        resultDateTime.Day = day;
        resultDateTime.Second = baseDateTime.get(Calendar.SECOND);
        resultDateTime.Hour = baseDateTime.get(Calendar.HOUR_OF_DAY);
        resultDateTime.Minute = baseDateTime.get(Calendar.MINUTE);

        return ShamsyToMilady(resultDateTime);
    }

    private static int Calc_DM(int ydays, char aDM) {
        //ASSERT( aDM == 'D' || aDM == 'M' );

        int k;
        if (aDM == 'D') {
            if (ydays < 187) {
                k = ydays % 31;
                if (k == 0) k = 31;
            } else {
                ydays -= 186;
                k = (ydays % 30);
                if (k == 0) k = 30;
            }
            return k;
        }

        // month number calculation
        if (ydays < 187) {
            return (ydays % 31) != 0 ? (1 + ydays / 31) : (ydays / 31);
        }

        ydays -= 186;
        return (ydays % 30) != 0 ? (7 + ydays / 30) : (6 + ydays / 30);
    }

    public static Calendar IncrementDateTime(Calendar baseDateTime, int year, int month, int day) {
        return IncDate(baseDateTime, year, month, day);
    }

    public static Calendar DecrementDateTime(Calendar baseDateTime, int year, int month, int day) {
        return DecDate(baseDateTime, year, month, day);
    }

    public static int DaysOfShamsyMonth(int aYear, int aMonth) {
        return aMonth <= 6 ? 31 : aMonth < 12 ? 30 : Shamsi_Kabisse(aYear) == KW_NATURAL ? 29 : 30;
    }

    public static Calendar GetFirstDateTimeOf(Calendar baseDate) {
        JalaliDate shamsyDateTime = MiladyToShamsy(baseDate);
        shamsyDateTime.Month = 1;
        shamsyDateTime.Day = 1;
        shamsyDateTime.Hour = 0;
        shamsyDateTime.Minute = 0;
        shamsyDateTime.Second = 0;
        return ShamsyToMilady(shamsyDateTime);
    }

    public static Calendar GetLastDateTimeOf(Calendar baseDate) {
        JalaliDate shamsyDateTime = MiladyToShamsy(baseDate);
        shamsyDateTime.Year = shamsyDateTime.Year + 1;
        shamsyDateTime.Month = 1;
        shamsyDateTime.Day = 1;
        shamsyDateTime.Hour = 23;
        shamsyDateTime.Minute = 59;
        shamsyDateTime.Second = 59;
        Calendar dateTime = ShamsyToMilady(shamsyDateTime);
        return DecrementDateTime(dateTime, 0, 0, 1);
    }

    public static double MonthsBetween(Calendar fromDate, Calendar toDate, boolean needRound) {
        fromDate = Calendar.getInstance();
        fromDate.set(fromDate.get(Calendar.YEAR), fromDate.get(Calendar.MONTH), fromDate.get(Calendar.DATE));

        toDate = Calendar.getInstance();
        toDate.set(toDate.get(Calendar.YEAR), toDate.get(Calendar.MONTH), toDate.get(Calendar.DATE));

        if (toDate.compareTo(fromDate) == 0)
            return 0;
        if (toDate.compareTo(fromDate) < 0)
            return -MonthsBetween(toDate, fromDate, needRound);


        long totalDay = (toDate.getTimeInMillis() - fromDate.getTimeInMillis()) / 1000 / 3600 / 24;
        double dMonths = Math.round((float) totalDay / 30);

        Calendar tmpToDate1, tmpToDate2;

        while (true) {

            tmpToDate1 = IncDate(fromDate, 0, (int) dMonths, 0);

            tmpToDate2 = IncDate(fromDate, 0, (int) (dMonths) + 1, 0);

            if (tmpToDate1.compareTo(toDate) <= 0 && toDate.compareTo(tmpToDate2) < 0)

                break;

            if (toDate.compareTo(tmpToDate1) < 0)

                dMonths -= 1;

            else

                dMonths += 1;

        }

        long totalDay2 = (toDate.getTimeInMillis() - fromDate.getTimeInMillis()) / 1000 / 3600 / 24;


        dMonths += (double) (totalDay2) / 31;

        if (!needRound)

            return dMonths;


        int fday = MiladyToShamsy(fromDate).Day;

        int tday = MiladyToShamsy(toDate).Day;

        if (fday >= 29 && fday <= tday)

            return Math.round(dMonths);

        return dMonths;

    }

    public static JalaliDate MiladyToShamsy(int year, int month, int day, int hour, int minute, int second) {
        JalaliDate shamsyDateTime = new JalaliDate();
        int i_Year = year;
        int i_Month = month;
        int i_Day = day;

        int MK = Miladi_Kabisse(i_Year) == KW_KABISSE ? 1 : 0;
        int SK = Shamsi_Kabisse(i_Year - 622) == KW_KABISSE ? 1 : 0;
        int EY = ExceptionYear(i_Year - 622);


        short myDay = (short) (MK + 28);
        short[] newMonthArray = new short[JalaliDateUtil.month.length];
        for (int newMonthIdx = 0; newMonthIdx < newMonthArray.length; newMonthIdx++)
            newMonthArray[newMonthIdx] = JalaliDateUtil.month[newMonthIdx];
        newMonthArray[1] = myDay;

        int Sday = 0;
        for (int i = 0; i < i_Month - 1; i++)
            Sday += newMonthArray[i];
        Sday += i_Day;

        if (Sday > (79 + EY + SK)) {
            shamsyDateTime.Year = i_Year - 621;
            Sday -= 79 + EY + SK;
        } else {
            shamsyDateTime.Year = i_Year - 622;
            Sday += 286 - EY;
        }

        shamsyDateTime.Month = Calc_DM(Sday, 'M');
        shamsyDateTime.Day = Calc_DM(Sday, 'D');
        shamsyDateTime.Hour = hour;
        shamsyDateTime.Minute = minute;
        shamsyDateTime.Second = second;
        return shamsyDateTime;

    }

    public static JalaliDate MiladyToShamsy(Calendar dateTime) {
        return MiladyToShamsy(dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH) + 1, dateTime.get(Calendar.DATE), dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), dateTime.get(Calendar.SECOND));
    }

    public static JalaliDate MiladyToShamsy(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        return MiladyToShamsy(calendar);
    }

    private static int Shamsi_Kabisse(int year) {
        int dd;
        if (year >= 474) {
            dd = (year - 474) % 128;
            if (dd == 0)
                return KW_KABISSE;
        } else
            dd = (year >= 342) ? (year - 342) : (128 - (374 - year) % 128);

        for (int i = 0; i < 30; i++) {
            if (Khayyam_Table[i] == dd)
                return KW_KABISSE;
        }
        return KW_NATURAL;
    }

    private static int Miladi_Kabisse(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))
            return KW_KABISSE;
        return KW_NATURAL;
    }

    private static int ExceptionYear(int Year) {
        if (Year == 1380)
            return 0;

        int MK = 0, SK = 0;
        if (Year > 1380) {
            for (int i = 1380; i < Year; i++) {
                if (Shamsi_Kabisse(i) == KW_KABISSE)
                    SK++;
                if (Miladi_Kabisse(i + 622) == KW_KABISSE)
                    MK++;
            }
            return SK - MK;
        }
        // Year < 1380
        for (int i = Year; i < 1380; i++) {
            if (Shamsi_Kabisse(i) == KW_KABISSE)
                SK++;
            if (Miladi_Kabisse(i + 622) == KW_KABISSE)
                MK++;
        }
        return MK - SK;
    }


}
