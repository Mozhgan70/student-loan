package menu.util;

import entity.enumration.Bank;

import java.util.InputMismatchException;

public class Validation {
    Input INPUT = new Input();
    Message MESSAGE = new Message();
    public String getStringInputWithValidation(String prompt) {
        String input;
        while (true) {
            System.out.println(MESSAGE.getInputMessage(prompt));
            input = INPUT.scanner.next();
            // Add any specific validation logic here
            // For now, we just return the input
            return input;
        }
    }

    public String getValidCardNumber(Bank bank) {
        String cardNumber;
        while (true) {
            System.out.println(MESSAGE.getInputMessage("Card Number"));
            cardNumber = INPUT.scanner.next();
            if (cardNumber.startsWith(bank.getPreNumber())) {
                return cardNumber;
            } else {
                System.out.println("شماره کارت بانک " + bank.getBankName() + " باید با " + bank.getPreNumber() + " شروع شود.");
                System.out.println("لطفا شماره کارت صحیح را وارد کنید.");
            }
        }
    }

    public int getIntInputValidation(String propmt) {
        int input;
        while (true) {
            System.out.println(MESSAGE.getInputMessage(propmt));
            try {
                input = INPUT.scanner.nextInt();
                // Add validation if needed
                return input;
            } catch (InputMismatchException e) {
                //System.out.println("Invalid CVV2. Please enter a numeric value.");
                INPUT.scanner.next(); // Clear the invalid input
            }
        }
    }
}