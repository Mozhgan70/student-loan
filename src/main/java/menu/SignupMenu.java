package menu;
import dto.RegisterStudentDto;
import entity.Student;
import entity.enumration.City;
import entity.enumration.EducationGrade;
import entity.enumration.MaritalStatus;
import entity.enumration.UniversityType;
import menu.util.Input;
import menu.util.Message;
import service.StudentService;
import util.Common;
import util.jalaliCalender.JalaliDate;
import util.jalaliCalender.JalaliDateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class SignupMenu {

    private final Input INPUT;
    private final Message MESSAGE;
    private final StudentService STUDENT_SERVICE;
    private final Common COMMON;



    public SignupMenu(Input INPUT, Message MESSAGE, StudentService studentService, Common common) {
        this.INPUT = INPUT;
        this.MESSAGE = MESSAGE;


        STUDENT_SERVICE = studentService;
        COMMON = common;
    }

    public void show()
    {
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



    public Date convertPersianDateToDate(String persianDateStr)
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




    public void registerStudent() {
        try{
        Student student=null;
        do{
        System.out.println(MESSAGE.getInputMessage("National Code"));
        String nationalCode= INPUT.scanner.next();
        Student studentByNatCode = STUDENT_SERVICE.findStudentByNatCode(nationalCode);
        if(studentByNatCode!=null){
            System.out.println("this student registered before");
            return;
        }
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
        System.out.println(MESSAGE.getInputMessage("BirthDate In This Format YYYY/MM/DD For Example 1403/05/28"));
        String persianBirthDate= INPUT.scanner.next();
        Date birthDate = convertPersianDateToDate(persianBirthDate);
        System.out.println(MESSAGE.getInputMessage("studentNumber"));
        String studentNumber= INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("University Name"));
        String universityName= INPUT.scanner.next();
        UniversityType universityType =COMMON.getEnumChoice(UniversityType.class);
        City residenceCity =COMMON.getEnumChoice(City.class);
        System.out.println(residenceCity);
        System.out.println(MESSAGE.getInputMessage("Entry Year In This Format YYYY/MM/DD For Example 1403/05/28"));
        String persianEntryYear= INPUT.scanner.next();
        Date entryYear = convertPersianDateToDate(persianEntryYear);
        EducationGrade educationGrade=COMMON.getEnumChoice(EducationGrade.class);
        MaritalStatus maritalStatus=COMMON.getEnumChoice(MaritalStatus.class);
        System.out.println("Are You Dormitory Resident ");
        System.out.println("1.Yes");
        System.out.println("2.No");
        int choice = INPUT.scanner.nextInt();
        boolean isDormitoryResident=false;
        if (choice == 1) { isDormitoryResident=true;}
        else if (choice == 2) { isDormitoryResident=false;}

        RegisterStudentDto studentParam=new RegisterStudentDto(
                name,
                lastName,
                fatherName,
                motherName,
                idNumber,
                nationalCode,
                birthDate,
                studentNumber,
                universityName,
                universityType,
                entryYear,
                educationGrade,
                maritalStatus,
                residenceCity,
                isDormitoryResident);


         student = STUDENT_SERVICE.registerStudent(studentParam);
        if (student != null) {
            System.out.println("Student is registered successfully.");
        } else {
            System.out.println("Registration failed");
            System.out.println("Please correct the errors and try again.");
        }
    } while (student == null);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
}
}
