package menu;

import dto.CardDto;

import entity.Card;
import entity.Installment;
import entity.Loan;

import entity.Student;
import menu.util.Input;
import menu.util.Message;
import service.CardService;
import service.InstallmentService;
import service.LoanService;

import service.StudentService;
import util.Common;
import util.UserSession;

import util.jalaliCalender.JalaliDateUtil;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class PaymentMenu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final LoanService LOAN_SERVICE;
    private final UserSession USER_SESSION;
    private final InstallmentService INSTALLMENT_SERVICE;
    private final CardService CARD_SERVICE;
    private final StudentService STUDENT_SERVICE;
    private final Common COMMON;
    public PaymentMenu(Input input, Message message, LoanService loanService, UserSession userSession, InstallmentService installmentService, CardService cardService, StudentService studentService, Common common) {
        INPUT = input;
        MESSAGE = message;
        LOAN_SERVICE = loanService;
        USER_SESSION = userSession;
        INSTALLMENT_SERVICE = installmentService;

        CARD_SERVICE = cardService;
        STUDENT_SERVICE = studentService;
        COMMON = common;
    }
    public void show() {
        Student student = STUDENT_SERVICE.findStudentById(USER_SESSION.getTokenId());
        Date startDate = LOAN_SERVICE.calcInstallmentStartDate(student);
        Date currentDateTime = new Date();

        if (currentDateTime.after(startDate)) {
            showPaymentMenu();
        } else {
            System.out.println("منوی بازپرداخت را بعد از فارغ تحصیلی میتوانید مشاهده کنید");
        }
    }

    private void showPaymentMenu() {
        while (true) {
            System.out.println("""
                Enter one of the following options:
                1 -> Paid Installments
                2 -> Unpaid Installments
                3 -> Payment Installment
                4 -> Previous
                """);

            String choice = INPUT.scanner.next();
            switch (choice) {
                case "1" -> showLoanList(1);
                case "2" -> showLoanList(2);
                case "3" -> showLoanList(3);
                case "4" -> {
                    return;
                }
                default -> System.out.println(MESSAGE.getInvalidInputMessage());
            }
        }
    }

    private void showLoanList(int action) {
        List<Loan> loans = LOAN_SERVICE.getAllStudentLoan(USER_SESSION.getTokenId());
        if(loans!=null) {

            if (loans.isEmpty()) {
                System.out.println("No loans found.");
                return;
            }

            while (true) {
                System.out.println("Select a loan to view:");
                for (int i = 0; i < loans.size(); i++) {
                    System.out.println((i + 1) + " -> " + loans.get(i).getLoanType().getLoanType() +
                            " | PaymentDate: " + JalaliDateUtil.MiladyToShamsy(loans.get(i).getPaymentDate()));
                }
                System.out.println((loans.size() + 1) + " -> Previous");

                int loanChoice = INPUT.scanner.nextInt();
                if (loanChoice == loans.size() + 1) {
                    return;
                }

                if (loanChoice > 0 && loanChoice <= loans.size()) {
                    Loan selectedLoan = loans.get(loanChoice - 1);
                    processLoanSelection(action, selectedLoan);
                } else {
                    System.out.println("Invalid choice. Please select a valid loan.");
                }
            }
        }
        else{
            System.out.println("شما تسهیلاتی دریافت نکرده اید");
        }
    }

    private void processLoanSelection(int action, Loan selectedLoan) {
        switch (action) {
            case 1 -> showPaidInstallments(selectedLoan);
            case 2 -> showUnpaidInstallments(selectedLoan);
            case 3 -> paymentInstallment(selectedLoan);
        }

        System.out.println("1 -> Back to Loan List");
        System.out.println("2 -> Previous Menu");
        String postViewChoice = INPUT.scanner.next();
        if ("2".equals(postViewChoice)) {
            return;
        }
    }

    private void paymentInstallment(Loan loan) {
        List<Installment> unpaidInstallments = showUnpaidInstallments(loan);
        if (unpaidInstallments == null || unpaidInstallments.isEmpty()) {
            System.out.println("No unpaid installments found.");
            return;
        }

        System.out.println("Select installment number that you want to pay:");
        int installmentChoice = INPUT.scanner.nextInt();
        Optional<Installment> chosenInstallment = unpaidInstallments.stream()
                .filter(installment -> installment.getInstallmentNumber() == installmentChoice)
                .findFirst();

        chosenInstallment.ifPresentOrElse(installment -> {
            if (installment.getIsPaid()) {
                System.out.println("Installment already paid.");
            } else {
                getPayment(installment);
            }
        }, () -> System.out.println("Installment not found."));
    }

    private void getPayment(Installment installment) {
        CardDto cardDto = getCard();
        Card validCard = CARD_SERVICE.findCardRelatedLoan(cardDto, installment);

        if (validCard != null) {
            System.out.println("Card is valid. Insert amount:");
            System.out.println("Your installment amount is: " + installment.getInstallmentAmount() + ". Do you want to pay it?");
            System.out.println("1 -> Yes");
            System.out.println("2 -> No");

            if (INPUT.scanner.nextInt() == 1) {
                INSTALLMENT_SERVICE.installmentPayment(validCard, installment, installment.getInstallmentAmount());
                System.out.println("Payment successful.");
            }
        } else {
            System.out.println("Card not found, try again.");
        }
    }

    private void showPaidInstallments(Loan loan) {
        List<Installment> paidInstallments = INSTALLMENT_SERVICE.getInstallmentsByLoanIdAndPaidStatus(loan.getId(), true);
        if (paidInstallments==null || paidInstallments.isEmpty()) {
            System.out.println("No paid installments found for this loan.");
        } else {
            System.out.println("Paid Installments:");
            paidInstallments.forEach(installment ->
                    System.out.println(installment.getInstallmentNumber() + " - " +
                            JalaliDateUtil.MiladyToShamsy(installment.getPaymentDate())));
        }
    }

    private List<Installment> showUnpaidInstallments(Loan loan) {
        List<Installment> unpaidInstallments = INSTALLMENT_SERVICE.getInstallmentsByLoanIdAndPaidStatus(loan.getId(), false);
        if (unpaidInstallments==null || unpaidInstallments.isEmpty()) {
            System.out.println("No unpaid installments found for this loan.");
            return Collections.emptyList();
        } else {
            System.out.println("Unpaid Installments:");
            unpaidInstallments.forEach(installment ->
                    System.out.println(installment.getInstallmentNumber() + " - " +
                            JalaliDateUtil.MiladyToShamsy(installment.getInstallmentDate()) + " " +
                            installment.getInstallmentAmount()));
            return unpaidInstallments;
        }
    }

    private CardDto getCard() {
        String cardNumber =COMMON.getInputData("Card Number");
        String expireDate =COMMON.getInputData("Expire Date (format: YY/MM)");
        int cvv2 =COMMON.getValidCvv2();
        return new CardDto(expireDate, cardNumber, cvv2, null);
    }


}
