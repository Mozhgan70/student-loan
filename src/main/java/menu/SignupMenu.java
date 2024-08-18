package menu;
import entity.enumration.EducationGrade;
import entity.enumration.MaritalStatus;
import entity.enumration.UniversityType;
import menu.util.Input;
import menu.util.Message;
import util.jalaliCalender.JalaliDate;
import util.jalaliCalender.JalaliDateUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class SignupMenu {

    private final Input INPUT;
    private final Message MESSAGE;



    public SignupMenu(Input INPUT, Message MESSAGE) {
        this.INPUT = INPUT;
        this.MESSAGE = MESSAGE;


    }

    public void show(){
        SignupMenu:
        while (true) {
            System.out.println("""
                    1 -> Enter Information
                    2 -> Previous Menu
                    """);

            switch (INPUT.scanner.next()) {
                case "1":
                    registerStudent();
                    break;
                case "2": {
                    break SignupMenu;
                }
                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());

            }

        }
    }


    public Date convertPersianDateToDate(String persianDateStr) {
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
        else{
            System.out.println("Invalid Persian Date Format");
            return null;
        }
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



    public void registerStudent() {
        System.out.println(MESSAGE.getInputMessage("First Name"));
        String name = INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("Last Name"));
        String lastName = INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("Father Name"));
        String fatherName = INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("Mother Name"));
        String motherName = INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("Id Number"));
        String idNumber= INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("National Code"));
        String nationalCode= INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("BirthDate In This Format YYYY/MM/DD For Example 1403/05/28"));
        String persianBirthDate= INPUT.scanner.next();
        Date birthDate = convertPersianDateToDate(persianBirthDate);
        System.out.println(MESSAGE.getInputMessage("studentNumber"));
        String studentNumber= INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("University Name"));
        String universityName= INPUT.scanner.next();
        UniversityType universityType = getEnumChoice(UniversityType.class);
        System.out.println(MESSAGE.getInputMessage("Entry Year In This Format YYYY/MM/DD For Example 1403/05/28"));
        String persianEntryYear= INPUT.scanner.next();
        Date entryYear = convertPersianDateToDate(persianEntryYear);
        EducationGrade educationGrade=getEnumChoice(EducationGrade.class);
        MaritalStatus maritalStatus=getEnumChoice(MaritalStatus.class);
        System.out.println("Are You Dormitory Resident ");
        System.out.println("1.Yes");
        System.out.println("2.No");
        int choice = INPUT.scanner.nextInt();
        if (choice == 1) {boolean isDormitoryResident=true;}
        else if (choice == 2) {boolean isDormitoryResident=false;}



    }
}
