package util;
import menu.util.Input;
import menu.util.Message;
import util.jalaliCalender.JalaliDate;
import util.jalaliCalender.JalaliDateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Common {
    private final Input INPUT;
    private final Message MESSAGE;

    public Common(Input input, Message message) {
        INPUT = input;
        MESSAGE = message;
    }

    public  <T extends Enum<T>> T getEnumChoice(Class<T> enumClass) {

        System.out.println("Please select a " + enumClass.getSimpleName() + ":");
        T[] enumConstants = enumClass.getEnumConstants();
        for (int i = 0; i < enumConstants.length; i++) {
            System.out.println((i + 1) + ". " + enumConstants[i]);
        }
        System.out.println(MESSAGE.getInputMessage("Number Of Your Choice"));
        int choice = INPUT.scanner.nextInt();

        if (choice > 0 && choice <= enumConstants.length) {
            return enumConstants[choice - 1];
        } else {
            System.out.println(MESSAGE.getInvalidInputMessage());
            return null;
        }
    }

    public String getInputData(String prompt) {
        System.out.println(MESSAGE.getInputMessage(prompt));
        return INPUT.scanner.next();
    }

    public int getValidCvv2() {
        int cvv2 = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.println(MESSAGE.getInputMessage("CVV2"));
            if (INPUT.scanner.hasNextInt()) {
                cvv2 = INPUT.scanner.nextInt();
                validInput = true;
            } else {
                System.out.println("Invalid input! Please enter a numeric value for CVV2.");
                INPUT.scanner.next();
            }
        }

        return cvv2;
    }


    public Date convertStringPersianDateToDate(String persianDateStr)
    {
        Pattern persianDatePattern = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");
        if(persianDatePattern.matcher(persianDateStr).matches()){
            String[] parts = persianDateStr.split("/");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            JalaliDate jalaliDate = new JalaliDate(year, month, day, 0, 0, 0);
            Calendar gregorianCalendar = JalaliDateUtil.ShamsyToMilady(jalaliDate);
            Date gregorianDate = gregorianCalendar.getTime();
            return gregorianDate;}
        else
        {
            System.out.println("Invalid Persian Date Format");
            return null;
        }
    }
}
