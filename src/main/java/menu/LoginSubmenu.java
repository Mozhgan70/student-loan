package menu;
import menu.util.Input;
import menu.util.Message;
import java.sql.SQLException;


public class LoginSubmenu {

    private final Input INPUT;
    private final Message MESSAGE;
    private final RegisterLoanMenu REGISTER_LOAN_MENU;
    private final PaymentMenu PAYMENT_MENU;

    //private final UserService USER_SERVICE;

    public LoginSubmenu(Input INPUT, Message MESSAGE, RegisterLoanMenu registerLoanMenu, PaymentMenu paymentMenu
    ) {
        this.INPUT = INPUT;
        this.MESSAGE = MESSAGE;
        this.REGISTER_LOAN_MENU = registerLoanMenu;
        this.PAYMENT_MENU = paymentMenu;
    }

    public void show() {
        LoginSubmenu:
        while (true) {
            System.out.println("""
                    Enter one of the following options:
                    1 -> Register Loan
                    2 -> Payment Loan
                    3 ->Previous
                   
                    """);
            switch (INPUT.scanner.next()) {
                case "1":
                    REGISTER_LOAN_MENU.show();
                break ;
                case "2":
                    PAYMENT_MENU.show();
                break ;
                case "3":
                    break LoginSubmenu;

                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());
            }
        }
    }
}