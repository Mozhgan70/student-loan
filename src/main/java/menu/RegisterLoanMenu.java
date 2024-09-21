package menu;

import dto.CardDto;
import dto.CardDtoWithId;
import dto.LoanRegistrationDto;
import dto.SpouseDto;
import dto.mapStruct.CardMapper;
import entity.*;
import entity.enumration.Bank;
import entity.enumration.LoanType;
import menu.util.Input;
import menu.util.Message;
import service.*;
import util.Common;
import util.UserSession;

import java.util.Date;
import java.util.List;

public class RegisterLoanMenu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final UserSession USER_SESSION;
    private final LoanTypeConditionService LOAN_TYPE_CONDITION_SERVICE;
    private final StudentService STUDENT_SERVICE;
    private final Common COMMON;
    private final LoanService LOAN_SERVICE;
    private final CardService CARD_SERVICE;
    private final CardMapper cardMapper;


    public RegisterLoanMenu(Input input, Message message, UserSession userSession, LoanTypeConditionService loanTypeConditionService, StudentService studentService, Common common, LoanService loanService, CardService cardService, CardMapper cardMapper) {
        INPUT = input;
        MESSAGE = message;
        USER_SESSION = userSession;
        LOAN_TYPE_CONDITION_SERVICE = loanTypeConditionService;
        STUDENT_SERVICE = studentService;
        COMMON = common;
        LOAN_SERVICE = loanService;
        CARD_SERVICE = cardService;
        this.cardMapper = cardMapper;
    }

    public void show() {
        Student student = STUDENT_SERVICE.findStudentById(USER_SESSION.getTokenId());
        Date startDate = LOAN_SERVICE.calcInstallmentStartDate(student);
        Date currentDateTime = new Date();

        if (!LOAN_SERVICE.checkIsOpenRegisterDate()) {
            System.out.println("در حال حاضر پنجره ثبت‌ نام وام بسته است.");
            return;
        }

        if (!currentDateTime.before(startDate)) {
            System.out.println("شما فارغ تحصیل شده اید و امکان ثبت نام در وام های دانشجویی را ندارید");
            return;
        }

        while (true) {
            displayLoanMenu();
            String input = INPUT.scanner.next();

            if (!processLoanInput(input)) {
                System.out.println(MESSAGE.getInvalidInputMessage());
            } else if (input.equals("4")) {
                break;
            }
        }
    }

    private void displayLoanMenu() {
        System.out.println("""
        Enter one of the following options:
        1 -> Tuition loan
        2 -> Education loan
        3 -> Housing loan
        4 -> Previous
    """);
    }

    private boolean processLoanInput(String input) {
        LoanTypeCondition loanCondition = null;

        switch (input) {
            case "1":
                loanCondition = LOAN_TYPE_CONDITION_SERVICE.findByEducationandLoanType(
                        USER_SESSION.getEducationGrade(), LoanType.TUITION_FEE_LOAN, null);
                break;
            case "2":
                loanCondition = LOAN_TYPE_CONDITION_SERVICE.findByEducationandLoanType(
                        USER_SESSION.getEducationGrade(), LoanType.EDUCATION_LOAN, null);
                break;
            case "3":
                loanCondition = LOAN_TYPE_CONDITION_SERVICE.findByEducationandLoanType(
                        null, LoanType.HOUSING_LOAN, USER_SESSION.getCity());
                break;
            case "4":
                return true;
            default:
                return false;
        }

        if (loanCondition != null && LOAN_SERVICE.getCheckLoanCondition(loanCondition) == 1) {
            registerLoan(loanCondition);
        }
        return true;
    }
    public void registerLoan(LoanTypeCondition loanType) {
        try {
            SpouseDto spouseDto = null;
            String address = null;
            String contractNumber = null;

            if (loanType.getLoanType() == LoanType.HOUSING_LOAN) {
                spouseDto = getSpouseData();
                if (spouseDto == null) {
                    return;
                }
                address =COMMON.getInputData("Your Address");
                contractNumber =COMMON.getInputData("Your Contract Number");
            }

            CardDtoWithId cardDto = getCard();

            LoanRegistrationDto loanRegistrationDto = new LoanRegistrationDto(
                    loanType, spouseDto, cardDto, address, contractNumber
            );

            if (LOAN_SERVICE.registerLoan(loanRegistrationDto)) {
                System.out.println("Loan registered successfully.");
            } else {
                System.out.println("Loan registration failed.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
    }

    private SpouseDto getSpouseData() {
        System.out.println("Please insert spouse data for loan registration:");

        String nationalCode =COMMON.getInputData("Spouse National Code");
        if (!LOAN_SERVICE.checkSpouseLoan(nationalCode)) {
            return null;
        }

        String firstName =COMMON.getInputData("Spouse First Name");
        String lastName =COMMON.getInputData("Spouse Last Name");

        return new SpouseDto(firstName, lastName, nationalCode);
    }



    public CardDtoWithId getCard() {
        List<Card> cards = CARD_SERVICE.selectAllStudentCard(USER_SESSION.getTokenId());
        if (!cards.isEmpty() ) {
        System.out.println("شماره کارت هایی که در ارتباط با تسهیلات دیگر در سیستم ثبت شده است. آیا مایل به انتخاب از میان آن ها می باشید؟");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int selection=INPUT.scanner.nextInt();
            if(selection==1){
            return selectExistingCard(cards);}
        }

        System.out.println("کارت باید متعلق به یکی از بانک های زیر باشد لطفا بانک مورد نظر خود را انتخاب کنید:");
        Bank bank = COMMON.getEnumChoice(Bank.class);
        String cardNumber = getValidCardNumber(bank);
        String expireDate =COMMON.getInputData("Expire Date (format: YY/MM)");
        int cvv2 =COMMON.getValidCvv2();
        return new CardDtoWithId(null, expireDate, cardNumber, cvv2, bank);
    }


    private CardDtoWithId selectExistingCard(List<Card> cards) {
        System.out.println("Select a Bank Card number:");
        cards.forEach(card -> System.out.println(card.getId() + " ----> " + card.getCardNumber()));

        int selectedCardId = INPUT.scanner.nextInt();
        return cards.stream()
                .filter(card -> card.getId() == selectedCardId)
                .map(card -> cardMapper.toDTOId(card))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid card selection."));
    }

    private String getValidCardNumber(Bank bank) {
        String cardNumber;

        while (true) {
            cardNumber =COMMON.getInputData("Card Number");
            if (cardNumber.startsWith(bank.getPreNumber())) {
                break;
            } else {
                System.out.println("شماره کارت بانک " + bank.getBankName() + " باید با " + bank.getPreNumber() + " شروع شود.");
                System.out.println("لطفا شماره کارت صحیح را وارد کنید.");
            }
        }

        return cardNumber;
    }




}









