package menu;

import entity.*;
import entity.enumration.Bank;
import entity.enumration.LoanType;
import entity.enumration.UniversityType;
import menu.util.Input;
import menu.util.Message;
import service.LoanService;
import service.LoanTypeConditionService;
import service.StudentService;
import util.Common;
import util.UserSession;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
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
    private final LoanService LOAN_SERVICE;
    private final StudentService STUDENT_SERVICE;
    private final Common COMMON;

    public RegisterLoanMenu(Input input, Message message, UserSession userSession, LoanTypeConditionService loanTypeConditionService, LoanService loanService, StudentService studentService, Common common) {
        INPUT = input;
        MESSAGE = message;
        this.USER_SESSION = userSession;
        LOAN_TYPE_CONDITION_SERVICE = loanTypeConditionService;
        LOAN_SERVICE = loanService;
        STUDENT_SERVICE = studentService;
        COMMON = common;
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
            switch (INPUT.scanner.next()) {
                case "1":
                    LoanTypeCondition tuitionFeeLoan =
                            LOAN_TYPE_CONDITION_SERVICE.findByEducationandLoanType(USER_SESSION.getEducationGrade(),
                                    LoanType.TUITION_FEE_LOAN);
                    if (getRegisterTuitionLoan(tuitionFeeLoan) == 1)
                        registerLoan(tuitionFeeLoan);
                    break;
                case "2":

                    break;
                case "3":
                    break;
                case "4":
                    break RegisterLoanMenu;

                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());
            }
        }
    }


    public boolean checkRequestDateIsValid() {

        // تاریخ‌های شروع و پایان دوره‌های ثبت‌نام
        Calendar cal = Calendar.getInstance();

        // دوره اول: 1 آبان تا 8 آبان
        cal.set(2024, Calendar.OCTOBER, 22, 0, 0, 0); // 1 آبان (ماه‌ها از 0 شروع می‌شوند)
        Date startPeriod1 = cal.getTime();
        cal.set(2024, Calendar.OCTOBER, 29, 23, 59, 59); // 8 آبان
        Date endPeriod1 = cal.getTime();

        // دوره دوم: 25 بهمن تا 2 اسفند
        cal.set(2024, Calendar.FEBRUARY, 14, 0, 0, 0); // 25 بهمن
        Date startPeriod2 = cal.getTime();
        cal.set(2024, Calendar.FEBRUARY, 21, 23, 59, 59); // 2 اسفند
        Date endPeriod2 = cal.getTime();

        // تاریخ جاری سیستم
        Date currentDateTime = new Date();

        // چک کردن تاریخ جاری با دوره‌های ثبت‌نام
        if ((currentDateTime.after(startPeriod1) && currentDateTime.before(endPeriod1)) ||
                (currentDateTime.after(startPeriod2) && currentDateTime.before(endPeriod2))) {
            return true;
            // چک کنید که آیا دانشجو قبلاً ثبت‌نام کرده یا خیر
//                    boolean alreadyRegistered = checkIfStudentAlreadyRegistered(); // تابع برای بررسی ثبت‌نام قبلی
//
//                    if (alreadyRegistered) {
//                        System.out.println("شما قبلاً در این دوره ثبت‌نام کرده‌اید و نمی‌توانید دوباره ثبت‌نام کنید.");
//                    } else {
//                        // ثبت‌نام دانشجو
//                        System.out.println("ثبت‌نام شما موفقیت‌آمیز بود.");
//                    }

        } else
        {
            //System.out.println("در حال حاضر ثبت‌ نام وام امکان‌پذیر نیست.");
            return true;
        }
    }


    public void registerLoan(LoanTypeCondition loanType) {
        Student student=STUDENT_SERVICE.findStudentById(USER_SESSION.getTokenId());
        Loan studentLoans=LOAN_SERVICE.FindStudentLoan(student,loanType.getLoanType());
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
        calculateInstallments(loanType.getAmount(),100,loan);

//        LOAN_SERVICE.registerLoan(loan);

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



    public int getRegisterTuitionLoan(LoanTypeCondition tuitionFeeLoan) {
       if(USER_SESSION.getUniversityType()!= UniversityType.Rozane){

        if (checkRequestDateIsValid()) {
            if (tuitionFeeLoan != null) {
                System.out.println("""
                        تسهیلاتی که در قالب تسهیلات شهریه دانشجویی در اختیار شما قرار خواهد گرفت به مبلغ %s می باشد
                        آیا مایل به دریافت این تسهیلات می باشید؟
                        1 .تایید
                        2 .انصراف
                        """.formatted(tuitionFeeLoan.getAmount()));
                return INPUT.scanner.nextInt();
            } else {
                System.out.println("وام درخواستی در حال حاضر در سیستم تعریف نشده است.");
                return 0;
            }
        } else {
            System.out.println("در حال حاضر ثبت‌ نام وام امکان‌پذیر نیست.");
            return 0;
        }
    }
       else{
           System.out.println("این وام متعلق به دانشجویان شهریه پرداز است");
           return 0;
       }
    }

    public Set<Installment> calculateInstallments(double loanAmount, double annualIncreasePercentage,Loan loan) {

        Set<Installment> installments=new HashSet<>();
        double monthlyInterestRate = (4.0 / 100) / 12;
        double initialInstallment = ( loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, 60))
                / (Math.pow(1 + monthlyInterestRate, 60) - 1);


        LocalDate startDate = LocalDate.now();
        int count=0;
        for (int year = 1; year <= 5; year++) {
            for (int i = 0; i < 12; i++) {

                startDate = startDate.plusMonths(1);
                count++;
                Installment installment=Installment.builder().installmentAmount(initialInstallment)
                        .installmentDate( Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
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
