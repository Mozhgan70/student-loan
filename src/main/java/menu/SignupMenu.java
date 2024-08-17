package menu;
import menu.util.Input;
import menu.util.Message;

public class SignupMenu {

    private final Input INPUT;
    private final Message MESSAGE;



    public SignupMenu(Input INPUT, Message MESSAGE) {
        this.INPUT = INPUT;
        this.MESSAGE = MESSAGE;


    }

    public void show(){
        SignupMenu:
        while (true) {
            System.out.println("""
                    1 -> Enter Information
                    2 -> Previous Menu
                    """);

            switch (INPUT.scanner.next()) {
                case "1":
                    System.out.println(MESSAGE.getInputMessage("Username"));
                    String username = INPUT.scanner.next();
                    System.out.println(MESSAGE.getInputMessage("password"));
                    String password = INPUT.scanner.next();
//                    if (USER_SERVICE.signUp(username, password, LocalDate.now())) {
//                        System.out.println(MESSAGE.getSuccessfulMessage("sign up"));
//                        break signup;
//                    }
                    System.out.println(MESSAGE.getExistMessage("username"));
                    break;
                case "2": {
                    break SignupMenu;
                }
                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());

            }

        }
    }
}
