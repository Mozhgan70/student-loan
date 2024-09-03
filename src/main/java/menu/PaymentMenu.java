package menu;

import dto.CardDto;
import entity.Installment;
import entity.Loan;
import entity.enumration.LoanType;
import menu.util.Input;
import menu.util.Message;
import service.InstallmentService;
import service.LoanService;
import service.StudentService;
import util.UserSession;
import util.jalaliCalender.JalaliDate;
import util.jalaliCalender.JalaliDateUtil;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class PaymentMenu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final LoanService LOAN_SERVICE;
    private final UserSession USER_SESSION;
    private final InstallmentService INSTALLMENT_SERVICE;

    public PaymentMenu(Input input, Message message, LoanService loanService, UserSession userSession, InstallmentService installmentService) {
        INPUT = input;
        MESSAGE = message;
        LOAN_SERVICE = loanService;
        USER_SESSION = userSession;
        INSTALLMENT_SERVICE = installmentService;
    }


    public void show() {
        PaymentMenu:
        while (true) {
            System.out.println("""
                    Enter one of the following options:
                    1 -> Paid Installments 
                    2 -> Unpaid installments
                    3 -> Payment Installment  
                    4 -> Previous     
                    """);
            switch (INPUT.scanner.next()) {
                case "1":
                    showLoanList(1);
                    break;
                case "2":
                    showLoanList(2);
                    break;
                case "3":
                    showLoanList(3);
                    ;

                    break;
                case "4":
                    break PaymentMenu;
                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());
            }

        }
    }

    private void paymentInstallment(Loan loan) {
        List<Installment> installments = showUnpaidInstallments(loan);
        System.out.println("Select installment number that you want to pay ");
        int installmentChoice = INPUT.scanner.nextInt();
        Optional<Installment> choosenInstallment = installments.stream().filter(a -> a.getInstallmentNumber() == installmentChoice).findFirst();
        if (choosenInstallment.isPresent()) {
            Installment installment = choosenInstallment.get();
            System.out.println(MESSAGE.getInputMessage("Card Number"));
            String cardNumber = INPUT.scanner.next();
            System.out.println(MESSAGE.getInputMessage("Expire Date (format: YY/MM)"));
            String expireDate = INPUT.scanner.next();
            System.out.println(MESSAGE.getInputMessage("CVV2"));
            int cvv2 = INPUT.scanner.nextInt();
//            System.out.println(MESSAGE.getInputMessage("payment Amount"));
//            Double amount=INPUT.scanner.nextDouble();
            CardDto cardDTO = new CardDto(expireDate, cardNumber, cvv2, null);
            INSTALLMENT_SERVICE.installmentPayment(cardDTO,installment);


        } else {
            System.out.println("Installment not found.");


        }
    }

    private void showLoanList(int show) {
        while (true) {
            // Fetch the loans for the current student
            List<Loan> loans = LOAN_SERVICE.getAllStudentLoan(USER_SESSION.getTokenId());
            if (loans.isEmpty()) {
                System.out.println("No loans found.");
                return;
            }
//            + (showPaid ? "paid" : "unpaid") + " installments:"
            System.out.println("Select a loan to view ");
            for (int i = 0; i < loans.size(); i++) {
                System.out.println((i + 1) + " -> " + loans.get(i).getLoanType().getLoanType() + " |PaymentDate: " + JalaliDateUtil.MiladyToShamsy(loans.get(i).getPaymentDate()));
            }
            System.out.println((loans.size() + 1) + " -> Previous");

            String loanChoice = INPUT.scanner.next();
            int loanChoiceNumber;

            try {
                loanChoiceNumber = Integer.parseInt(loanChoice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (loanChoiceNumber == loans.size() + 1) {
                break;
            } else if (loanChoiceNumber > 0 && loanChoiceNumber <= loans.size()) {
                Loan selectedLoan = loans.get(loanChoiceNumber - 1);

                if (show == 1) {
                    showPaidInstallments(selectedLoan);
                } else if (show == 2) {
                    showUnpaidInstallments(selectedLoan);
                } else {
                    paymentInstallment(selectedLoan);
                }

                System.out.println("1 -> Back to Loan List");
                System.out.println("2 -> Previous Menu");

                String postViewChoice = INPUT.scanner.next();

                if ("2".equals(postViewChoice)) {
                    break;
                }
                // If "1" is chosen, the loop will continue and show the loan list again
            } else {
                System.out.println("Invalid choice. Please select a valid loan.");
            }
        }
    }

    private void showPaidInstallments(Loan loan) {
        List<Installment> paidInstallments = INSTALLMENT_SERVICE.getInstallmentsByLoanIdAndPaidStatus(loan.getId(), true);
        if (paidInstallments.isEmpty()) {
            System.out.println("No paid installments found for this loan.");
        } else {
            System.out.println("Paid Installments:");
            for (Installment installment : paidInstallments) {
                System.out.println(installment.getInstallmentNumber()
                        + "- " + JalaliDateUtil.MiladyToShamsy(installment.getPaymentDate()));

            }
        }
    }

    private List<Installment> showUnpaidInstallments(Loan loan) {
        List<Installment> unpaidInstallments = INSTALLMENT_SERVICE.getInstallmentsByLoanIdAndPaidStatus(loan.getId(), false);
        if (unpaidInstallments.isEmpty()) {
            System.out.println("No unpaid installments found for this loan.");
            return null;
        } else {
            System.out.println("Unpaid Installments:");
            for (Installment installment : unpaidInstallments) {
                System.out.println(installment.getInstallmentNumber()
                        + "- " + JalaliDateUtil.MiladyToShamsy(installment.getInstallmentDate())
                        + " " + installment.getInstallmentAmount());

            }
            return unpaidInstallments;
        }
    }


}
