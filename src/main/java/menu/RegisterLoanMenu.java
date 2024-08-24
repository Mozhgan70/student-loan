package menu;

import entity.Card;
import entity.Loan;
import entity.LoanTypeCondition;
import entity.enumration.LoanType;
import entity.enumration.UniversityType;
import menu.util.Input;
import menu.util.Message;
import service.LoanService;
import service.LoanTypeConditionService;
import util.UserSession;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class RegisterLoanMenu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final UserSession USER_SESSION;
    private final LoanTypeConditionService LOAN_TYPE_CONDITION_SERVICE;
    private final LoanService LOAN_SERVICE;

    public RegisterLoanMenu(Input input, Message message, UserSession userSession, LoanTypeConditionService loanTypeConditionService, LoanService loanService) {
        INPUT = input;
        MESSAGE = message;
        this.USER_SESSION = userSession;
        LOAN_TYPE_CONDITION_SERVICE = loanTypeConditionService;
        LOAN_SERVICE = loanService;
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
                    if (getRegisterTuitionLoan() == 1)
                        registerLoan(LoanType.TUITION_FEE_LOAN);
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
        } else {
            //System.out.println("در حال حاضر ثبت‌ نام وام امکان‌پذیر نیست.");
            return true;
        }
    }


    public void registerLoan(LoanType loanType) {
        System.out.println(MESSAGE.getInputMessage("Card Number"));
        String cardNumber = INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("Expire Date"));
        String expireDate = INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("CVV2"));
        int cvv2 = INPUT.scanner.nextInt();
        Card card = Card.builder().cardNumber(cardNumber).expireDate(expireDate).cvv2(cvv2).build();
      //  Loan loan = Loan.builder().card(card).loanType(loanType)
        //LOAN_SERVICE.registerLoan()





    }

    ;

    public int getRegisterTuitionLoan() {
       if(USER_SESSION.getUniversityType()!= UniversityType.Rozane){

        if (checkRequestDateIsValid()) {

            LoanTypeCondition tuitionFeeLoan =
                    LOAN_TYPE_CONDITION_SERVICE.findByEducationandLoanType(USER_SESSION.getEducationGrade().toString(),
                            "TUITION_FEE_LOAN");
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

}
