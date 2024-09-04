package menu;

import dto.CardDto;

import entity.Card;
import entity.Installment;
import entity.Loan;

import menu.util.Input;
import menu.util.Message;
import service.CardService;
import service.InstallmentService;
import service.LoanService;

import util.UserSession;

import util.jalaliCalender.JalaliDateUtil;


import java.util.List;
import java.util.Optional;


public class PaymentMenu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final LoanService LOAN_SERVICE;
    private final UserSession USER_SESSION;
    private final InstallmentService INSTALLMENT_SERVICE;
    private final CardService CARD_SERVICE;

    public PaymentMenu(Input input, Message message, LoanService loanService, UserSession userSession, InstallmentService installmentService, CardService cardService) {
        INPUT = input;
        MESSAGE = message;
        LOAN_SERVICE = loanService;
        USER_SESSION = userSession;
        INSTALLMENT_SERVICE = installmentService;

        CARD_SERVICE = cardService;
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
                    break;
                case "4":
                    break PaymentMenu;
                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());
            }

        }
    }



    private void showLoanList(int show) {
        List<Loan> loans = LOAN_SERVICE.getAllStudentLoan(USER_SESSION.getTokenId());
        if (loans.isEmpty()) {
            System.out.println("No loans found.");
            return;
        }
        while (true) {
            System.out.println("Select a loan to view ");
            for (int i = 0; i < loans.size(); i++) {
                System.out.println((i + 1) + " -> " + loans.get(i).getLoanType().getLoanType() + " |PaymentDate: " + JalaliDateUtil.MiladyToShamsy(loans.get(i).getPaymentDate()));
            }
            System.out.println((loans.size() + 1) + " -> Previous");


            int loanChoiceNumber = INPUT.scanner.nextInt();
            if (loanChoiceNumber == loans.size() + 1) {
                break;
            } else if (loanChoiceNumber > 0 && loanChoiceNumber <= loans.size()) {
                Loan selectedLoan = loans.get(loanChoiceNumber - 1);

                switch (show) {
                    case 1 -> showPaidInstallments(selectedLoan);
                    case 2 -> showUnpaidInstallments(selectedLoan);
                    default -> paymentInstallment(selectedLoan);
                }

                System.out.println("1 -> Back to Loan List");
                System.out.println("2 -> Previous Menu");

                String postViewChoice = INPUT.scanner.next();

                if ("2".equals(postViewChoice)) {
                    break;
                }
            } else {
                System.out.println("Invalid choice. Please select a valid loan.");
            }
        }
    }

    private void paymentInstallment(Loan loan) {
        try{

        List<Installment> installments = showUnpaidInstallments(loan);
        if (installments == null && installments.size()==0) {
            System.out.println("installments not found ");
            return;
        }
        System.out.println("Select installment number that you want to pay ");
        int installmentChoice = INPUT.scanner.nextInt();
        Optional<Installment> chosenInstallment = installments.stream().filter(a -> a.getInstallmentNumber() == installmentChoice).findFirst();
        if (chosenInstallment.isPresent()) {
            if (chosenInstallment.get().getIsPaid()) {
                System.out.println("installment already paid");
                return;
            }



            CardDto cardDTO = getCardDetailsFromUser();
            Card findCard = CARD_SERVICE.findCardRelatedLoan(cardDTO,chosenInstallment.get());
            if (findCard != null) {
                System.out.println("card is valid please insert amount:");

                System.out.println("your installment amount is:"
                        +chosenInstallment.get().getInstallmentAmount()+
                        "do you want pay it?");
                System.out.println("1-Yes");
                System.out.println("2-No");
                if(INPUT.scanner.nextInt()==1){
                INSTALLMENT_SERVICE.installmentPayment(findCard, chosenInstallment.get(),chosenInstallment.get().getInstallmentAmount());
                //check this again
                 System.out.println("Payment successful.");
                }
            }
            else {
                System.out.println("card not found try again");
            }

        } else {
            System.out.println("Installment not found.");
        }}
        catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }



    private void showPaidInstallments(Loan loan) {

        List<Installment> paidInstallments = INSTALLMENT_SERVICE.getInstallmentsByLoanIdAndPaidStatus(loan.getId(), true);
        if (paidInstallments.isEmpty()) {
            System.out.println("No paid installments found for this loan.");
        } else {
            System.out.println("Paid Installments:");
            paidInstallments.forEach(installment ->
                    System.out.println(installment.getInstallmentNumber() + "- " +
                            JalaliDateUtil.MiladyToShamsy(installment.getPaymentDate())));
        }
        }


    private List<Installment> showUnpaidInstallments(Loan loan) {
        List<Installment> unpaidInstallments = INSTALLMENT_SERVICE.getInstallmentsByLoanIdAndPaidStatus(loan.getId(), false);
        if (unpaidInstallments.isEmpty()) {
            System.out.println("No unpaid installments found for this loan.");
            return null;
        } else {
            System.out.println("Unpaid Installments:");
            unpaidInstallments.forEach(installment ->
                    System.out.println(installment.getInstallmentNumber() + "- " +
                            JalaliDateUtil.MiladyToShamsy(installment.getInstallmentDate()) + " " +
                            installment.getInstallmentAmount()));
            return unpaidInstallments;
        }
    }

    private CardDto getCardDetailsFromUser() {
        System.out.println(MESSAGE.getInputMessage("Card Number"));
        String cardNumber = INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("Expire Date (format: YY/MM)"));
        String expireDate = INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("CVV2"));
        int cvv2 = INPUT.scanner.nextInt();
        return new CardDto(expireDate, cardNumber, cvv2, null);
    }


}
