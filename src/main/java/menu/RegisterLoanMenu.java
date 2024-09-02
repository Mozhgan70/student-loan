package menu;

import dto.CardDto;
import dto.LoanRegistrationDto;
import dto.SpouseDto;
import entity.*;
import entity.enumration.Bank;
import entity.enumration.LoanType;
import menu.util.Input;
import menu.util.Message;
import service.*;
import util.Common;
import util.UserSession;

import java.util.Date;

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
        Student student = STUDENT_SERVICE.findStudentById(USER_SESSION.getTokenId());
        Date startDate = LOAN_SERVICE.calcInstallmentStartDate(student);
        Date currentDateTime = new Date();
        if (currentDateTime.before(startDate)) {
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

                    LoanTypeCondition tuitionFeeLoan = LOAN_TYPE_CONDITION_SERVICE
                            .findByEducationandLoanType(USER_SESSION.getEducationGrade(), LoanType.TUITION_FEE_LOAN, null);
                    if (LOAN_SERVICE.getCheckLoanCondition(tuitionFeeLoan) == 1)
                        registerLoan(tuitionFeeLoan);
                    break;
                case "2":

                    LoanTypeCondition educationLoan =
                            LOAN_TYPE_CONDITION_SERVICE.findByEducationandLoanType(USER_SESSION.getEducationGrade(),
                                    LoanType.EDUCATION_LOAN, null);
                    if (LOAN_SERVICE.getCheckLoanCondition(educationLoan) == 1)
                        registerLoan(educationLoan);
                    break;
                case "3":
                    LoanTypeCondition housingLoan =
                            LOAN_TYPE_CONDITION_SERVICE.findByEducationandLoanType(null, LoanType.HOUSING_LOAN,USER_SESSION.getCity());
                    if (LOAN_SERVICE.getCheckLoanCondition(housingLoan) == 1)
                        registerLoan(housingLoan);
                    break;
                case "4":
                    break RegisterLoanMenu;

                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());
            }

        }
    }
        else{
            System.out.println("شما فارغ تحصیل شده اید و امکان ثبت نام در وام های دانشجویی را ندارید");
        }

    }

   public void registerLoan(LoanTypeCondition loanType) {
       SpouseDto spouseDTO = null;
       String address = null;
       String contractNumber = null;

       if (loanType.getLoanType() == LoanType.HOUSING_LOAN) {
           System.out.println("Please insert spouse data for loan registration:");

           // Get spouse data
           System.out.println(MESSAGE.getInputMessage("Spouse National Code"));
           String nationalCode = INPUT.scanner.next();
           System.out.println(MESSAGE.getInputMessage("Spouse First Name"));
           String name = INPUT.scanner.next();
           System.out.println(MESSAGE.getInputMessage("Spouse Last Name"));
           String lastName = INPUT.scanner.next();

           spouseDTO = new SpouseDto(name, lastName, nationalCode);

           System.out.println(MESSAGE.getInputMessage("Your Address"));
           address = INPUT.scanner.next();
           System.out.println(MESSAGE.getInputMessage("Your Contract Number"));
           contractNumber = INPUT.scanner.next();
       }

       // Get card data
       System.out.println(MESSAGE.getInputMessage("Card Number"));
       String cardNumber = INPUT.scanner.next();
       System.out.println(MESSAGE.getInputMessage("Expire Date (format: YYYY-MM)"));
       String expireDate = INPUT.scanner.next();
       System.out.println(MESSAGE.getInputMessage("CVV2"));
       int cvv2 = INPUT.scanner.nextInt();
       System.out.println(MESSAGE.getInputMessage("Bank Name (Choose from the list)"));
       Bank bank = COMMON.getEnumChoice(Bank.class);

       CardDto cardDTO = new CardDto(expireDate,cardNumber,cvv2,bank);

       // Create LoanRegistrationDto
       LoanRegistrationDto loanRegistrationDTO = new LoanRegistrationDto(
               loanType, spouseDTO, cardDTO, address, contractNumber
       );

       // Register loan
       try {
           LOAN_SERVICE.registerLoan(loanRegistrationDTO);
           System.out.println("Loan registered successfully.");
       } catch (IllegalArgumentException e) {
           System.out.println(e.getMessage());
       }
   }
   }









