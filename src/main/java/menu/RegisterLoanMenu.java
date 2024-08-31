package menu;

import entity.*;
import entity.enumration.Bank;
import entity.enumration.LoanType;
import entity.enumration.MaritalStatus;
import entity.enumration.UniversityType;
import menu.util.Input;
import menu.util.Message;
import service.*;
import util.Common;
import util.UserSession;
import util.jalaliCalender.JalaliDate;
import util.jalaliCalender.JalaliDateUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RegisterLoanMenu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final UserSession USER_SESSION;
    private final LoanTypeConditionService LOAN_TYPE_CONDITION_SERVICE;
    private final StudentService STUDENT_SERVICE;
    private final Common COMMON;
    private final LoanService  LOAN_SERVICE;
    private final InstallmentService INSTALLMENT_SERVICE;
    private final SpouseService SPOUSE_SERVICE;

    public RegisterLoanMenu(Input input, Message message, UserSession userSession, LoanTypeConditionService loanTypeConditionService, StudentService studentService, Common common, LoanService loanService, InstallmentService installmentService, SpouseService spouseService) {
        INPUT = input;
        MESSAGE = message;
        USER_SESSION = userSession;
        LOAN_TYPE_CONDITION_SERVICE = loanTypeConditionService;
        STUDENT_SERVICE = studentService;
        COMMON = common;
        LOAN_SERVICE = loanService;
        INSTALLMENT_SERVICE = installmentService;
        SPOUSE_SERVICE = spouseService;
    }

    public void show() {
        RegisterLoanMenu:
        while (true) {
            System.out.println("""
                    Enter one of the following options:
                    1 ->Tuition loan
                    2 ->Education loan
                    3 ->Housing Loan
                    4 ->Previous
                    """);
            Student student=STUDENT_SERVICE.findStudentById(USER_SESSION.getTokenId());
            switch (INPUT.scanner.next()) {

                case "1":

                    LoanTypeCondition tuitionFeeLoan = LOAN_TYPE_CONDITION_SERVICE
                            .findByEducationandLoanType(USER_SESSION.getEducationGrade(), LoanType.TUITION_FEE_LOAN,null);
                    if (getCheckLoanCondition(tuitionFeeLoan,student) == 1)
                        registerLoan(tuitionFeeLoan);
                    break;
                case "2":

                    LoanTypeCondition educationLoan =
                            LOAN_TYPE_CONDITION_SERVICE.findByEducationandLoanType(USER_SESSION.getEducationGrade(),
                                    LoanType.EDUCATION_LOAN,null);
                    if (getCheckLoanCondition(educationLoan,student) == 1)
                        registerLoan(educationLoan);
                    break;
                case "3":
                    LoanTypeCondition housingLoan =
                            LOAN_TYPE_CONDITION_SERVICE.findByEducationandLoanType(null,LoanType.HOUSING_LOAN,student.getResidenceCity());
                    if (getCheckLoanCondition(housingLoan,student) == 1)
                        registerLoan(housingLoan);
                    break;
                case "4":
                    break RegisterLoanMenu;

                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());
            }
        }
    }


    public boolean checkRequestDateIsValid(LoanType loanType,Student student) {


        // تاریخ‌های شروع و پایان دوره‌های ثبت‌نام
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        // دوره اول: 1 آبان تا 8 آبان
        cal.set(currentYear, Calendar.AUGUST, 22, 0, 0, 0); // 1 آبان (ماه‌ها از 0 شروع می‌شوند)
        Date startPeriod1 = cal.getTime();
        cal.set(currentYear, Calendar.SEPTEMBER, 29, 23, 59, 59); // 8 آبان
        Date endPeriod1 = cal.getTime();

        // دوره دوم: 25 بهمن تا 2 اسفند
        cal.set(currentYear, Calendar.FEBRUARY, 14, 0, 0, 0); // 25 بهمن
        Date startPeriod2 = cal.getTime();
        cal.set(currentYear, Calendar.FEBRUARY, 21, 23, 59, 59); // 2 اسفند
        Date endPeriod2 = cal.getTime();

        // تاریخ جاری سیستم
        Date currentDateTime = new Date();



        // چک کردن تاریخ جاری با دوره‌های ثبت‌نام
        if ((currentDateTime.after(startPeriod1) && currentDateTime.before(endPeriod1)) ||
                (currentDateTime.after(startPeriod2) && currentDateTime.before(endPeriod2))) {

            Loan studentLoans = LOAN_SERVICE.FindStudentLoan(student, loanType);

            if (studentLoans != null) {
                if (loanType != LoanType.HOUSING_LOAN){
                if ((currentDateTime.after(startPeriod1) && currentDateTime.before(endPeriod1)) &&
                        (studentLoans.getRegisterLoanDate().after(startPeriod1) && studentLoans.getRegisterLoanDate().before(endPeriod1))
                ) {

                    System.out.println("شما قبلاً در این نیمسال ثبت ‌نام کرده‌اید و نمی‌توانید دوباره ثبت ‌نام کنید.");

                    return false;
                } else if ((currentDateTime.after(startPeriod2) && currentDateTime.before(endPeriod2)) &&
                        (studentLoans.getRegisterLoanDate().after(startPeriod2) && studentLoans.getRegisterLoanDate().before(endPeriod2))
                ) {
                    System.out.println("شما قبلاً در این نیمسال ثبت ‌نام کرده‌اید و نمی‌توانید دوباره ثبت ‌نام کنید.");
                    return false;
                }
            }
                else
                {
                    System.out.println("شما قبلا تسهیلات ودیعه مسکن را دریافت کرده اید و قادر به دریافت مجدد نیستید");
                    return false;

                }
        }
            return true;

        } else
        {
            System.out.println("در حال حاضر پنجره ثبت‌ نام وام بسته است.");
            return false;
        }
    }



    public void registerLoan(LoanTypeCondition loanType) {
        Student student=STUDENT_SERVICE.findStudentById(USER_SESSION.getTokenId());
        System.out.println("please insert spouse data for register loan:");
        if(loanType.getLoanType()==LoanType.HOUSING_LOAN){
            System.out.println(MESSAGE.getInputMessage("Spouse National Code"));
            String nationalCode= INPUT.scanner.next();
            Loan loanByNationalCode = LOAN_SERVICE.findLoanByNationalCode(nationalCode);
            if(loanByNationalCode!=null){
                System.out.println("همسر شما وام ودیعه مسکن دریافت کرده است و شما قادر به دریافت این وام نخواهید بود");
                return;
            }

            System.out.println(MESSAGE.getInputMessage("Spouse First Name"));
            String name = INPUT.scanner.next();
            System.out.println(MESSAGE.getInputMessage("Spouse Last Name"));
            String lastName = INPUT.scanner.next();
            Spouse spouse=Spouse.builder().name(name)
                    .lastName(lastName)
                    .nationalCode(nationalCode)
                    .build();
           student.setSpouse(spouse);
        }

        System.out.println(MESSAGE.getInputMessage("Card Number"));
        String cardNumber = INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("Expire Date"));
        String expireDate = INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("CVV2"));
        int cvv2 = INPUT.scanner.nextInt();
        System.out.println(MESSAGE.getInputMessage("Bank Name Your Bank Must Be one Of Following Banks"));
        Bank bank =COMMON.getEnumChoice(Bank.class);
        Card card = Card.builder()
                .cardNumber(cardNumber)
                .expireDate(expireDate)
                .cvv2(cvv2)
                .bank(bank)
                .build();


        Loan loan = Loan.builder()
                .card(card)
                .loanType(loanType)
                .paymentDate(new Date())
                .registerLoanDate(new Date())
                .remainLoanAmount(loanType.getAmount())
                .startInstallments(calcInstallmentStartDate(student))
                .student(student).build();
        Date startDate = calcInstallmentStartDate(student);
        Set<Installment> installments = calculateInstallments(loanType.getAmount(), 100, loan,startDate);
        INSTALLMENT_SERVICE.setInstallment(installments);


    }


    public Date calcInstallmentStartDate(Student student)

    {
        Date entryYear=student.getEntryYear();
//        Date sysDate = new Date();
//        LocalDate localSysDate = sysDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDbDate = entryYear.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        Period period = Period.between(localDbDate, localSysDate);
//        int diffYears = period.getYears();
        // Add the difference in years to the entry year date
        LocalDate updatedEntryYearDate=localDbDate;
        switch (student.getEducationGrade()){
            case ASSOCIATE_DEGREE:
            case MASTERS_DISCONTINUOUS:
                updatedEntryYearDate = localDbDate.plusYears(2);
                break;
            case BACHELORS_CONTINUOUS:
            case BACHELORS_DISCONTINUOUS:
                 updatedEntryYearDate = localDbDate.plusYears(4);
                break;
            case MASTERS_CONTINUOUS:
                updatedEntryYearDate = localDbDate.plusYears(6);
                break;
            case PROFESSIONAL_DOCTORATE:
            case DOCTORATE_CONTINUOUS:
            case PHD_DISCONTINUOUS:
                updatedEntryYearDate = localDbDate.plusYears(5);
                break;

        }


        // Convert back to java.util.Date if needed
        return Date.from(updatedEntryYearDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    }

    public int getCheckLoanCondition(LoanTypeCondition loanTypeCondition, Student student) {
       if(loanTypeCondition.getLoanType()==LoanType.TUITION_FEE_LOAN && USER_SESSION.getUniversityType()== UniversityType.Rozane){
           System.out.println("این وام متعلق به دانشجویان شهریه پرداز است");
           return 0;
       }
      else if(loanTypeCondition.getLoanType()==LoanType.HOUSING_LOAN
               && USER_SESSION.getMaritalStatus()== MaritalStatus.SINGLE && USER_SESSION.getIsDormitoryResident()){
           System.out.println("این وام متعلق به دانشجویان متاهلی است که ساکن خوابگاه نیستند");
           return 0;
       }
       else{

        if (checkRequestDateIsValid(loanTypeCondition.getLoanType(),student)) {
            if (loanTypeCondition != null) {
                System.out.println("""
                        تسهیلاتی که در قالب تسهیلات دانشجویی در اختیار شما قرار خواهد 
                        گرفت به مبلغ %s می باشدآیا مایل به دریافت این تسهیلات می باشید؟
                        1 .تایید
                        2 .انصراف
                        """.formatted(loanTypeCondition.getAmount()));
                return INPUT.scanner.nextInt();
            } else {
                System.out.println("وام درخواستی در حال حاضر در سیستم تعریف نشده است.");
                return 0;
            }
        } else {

            return 0;
        }
    }
    }

    public Set<Installment> calculateInstallments(double loanAmount, double annualIncreasePercentage,Loan loan,Date startDate)

    {
        Set<Installment> installments=new HashSet<>();
        double monthlyInterestRate = (4.0 / 100) / 12;
        double initialInstallment = ( loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, 60))
                / (Math.pow(1 + monthlyInterestRate, 60) - 1);
        int count=0;
        JalaliDate installmentStartDate = JalaliDateUtil.MiladyToShamsy(startDate);
        int year_ = installmentStartDate.Year;
        int month = installmentStartDate.Month;
        int day = installmentStartDate.Day;
        for (int year = 1; year <= 5; year++) {
            for (int i = 0; i < 12; i++) {
                System.out.printf("Installment %d: %d/%02d/%02d%n", (year * 12 + i + 1), year_, month, day);

                // Increment the month by 1
                month++;

                // Handle the transition from one year to the next
                if (month > 12) {
                    month = 1;
                    year_++;
                }

                System.out.println("ssss"+installmentStartDate);
                System.out.println(year_+" "+month+" "+day);
                String idate=year_+"/"+month+"/"+day;
              //  JalaliDateUtil.ShamsyToMilady(JalaliDate(idate))







              // startDate = startDate.plusMonths(1);
                count++;
                Installment installment=Installment.builder().installmentAmount(initialInstallment)
                     //   .installmentDate( Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                        .installmentNumber(count)
                        .loan(loan)
                        .build();
                installments.add(installment);
            }

            initialInstallment *= (1 + annualIncreasePercentage / 100);
        }
      return installments;
    }

}

//public class JalaliInstallmentDates {
//
//    public static void main(String[] args) {
//        // Starting Jalali date (1402/07/01)
//        int year = 1402;
//        int month = 7;
//        int day = 1;
//
//        int totalYears = 5;  // 5 years
//        int totalMonthsPerYear = 12;  // 12 months per year
//
//        for (int i = 0; i < totalYears; i++) {  // Loop through each year
//            for (int j = 0; j < totalMonthsPerYear; j++) {  // Loop through each month
//                System.out.printf("Installment %d: %d/%02d/%02d%n", (i * totalMonthsPerYear + j + 1), year, month, day);
//
//                // Increment the month by 1
//                month++;
//
//                // Handle the transition from one year to the next
//                if (month > 12) {
//                    month = 1;
//                    year++;
//                }
//            }
//        }
//    }
//}
