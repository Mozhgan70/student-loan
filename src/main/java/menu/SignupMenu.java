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

            String choice = INPUT.scanner.next();
            switch (choice) {
                case "1" -> registerStudent();
                case "2" -> {
                    break SignupMenu;
                }
                default -> System.out.println(MESSAGE.getInvalidInputMessage());
            }
        }
    }


    public void registerStudent() {
        try{
        Student student=null;
        do{

        String nationalCode= COMMON.getInputData("National Code");
        Student studentByNatCode = STUDENT_SERVICE.findStudentByNatCode(nationalCode);
        if(studentByNatCode!=null){
            System.out.println("this student registered before");
            return;
        }

        String name = COMMON.getInputData("First Name");
        String lastName = COMMON.getInputData("Last Name");
        String fatherName = COMMON.getInputData("Father Name");
        String motherName =COMMON.getInputData("Mother Name");
        String idNumber= COMMON.getInputData("Id Number");
        String persianBirthDate= COMMON.getInputData("BirthDate In This Format YYYY/MM/DD For Example 1403/05/28");
        Date birthDate =COMMON.convertStringPersianDateToDate(persianBirthDate);
        String studentNumber=COMMON.getInputData("studentNumber");
        String universityName= COMMON.getInputData("University Name");
        UniversityType universityType =COMMON.getEnumChoice(UniversityType.class);
        City residenceCity =COMMON.getEnumChoice(City.class);
        String persianEntryYear=COMMON.getInputData("Entry Year In This Format YYYY/MM/DD For Example 1403/05/28");
        Date entryYear =COMMON.convertStringPersianDateToDate(persianEntryYear);
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
            System.out.println("Registration failed Please correct the errors and try again.");
        }
    } while (student == null);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
}
}
