package menu;

import menu.util.Input;
import menu.util.Message;
import service.StudentService;

import java.sql.SQLException;

public class PaymentMenu {
    private final Input INPUT;
    private final Message MESSAGE;
//    private final StudentService STUDENT_SERVICE;
    public PaymentMenu(Input input, Message message) {
        INPUT = input;
        MESSAGE = message;
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
                    break;
                case "2":
                    break;

                case "3":
                    break ;
                case "4":
                    break PaymentMenu;

                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());
            }
        }
    }


}
