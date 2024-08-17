package menu;

import menu.util.Input;
import menu.util.Message;

import java.sql.SQLException;

public class RegisterLoanMenu {
    private final Input INPUT;
    private final Message MESSAGE;

    public RegisterLoanMenu(Input input, Message message) {
        INPUT = input;
        MESSAGE = message;
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

                    break ;
                case "2":
                    break ;
                case "3":
                    break ;
                case "4":
                    break RegisterLoanMenu;

                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());
            }
        }
    }
}
