package menu;

import menu.util.Input;
import menu.util.Message;

public class Menu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final SignupMenu SIGNUP_MENU;
    private final LoginMenu LOGIN_MENU;
    public Menu(Input input, Message message, SignupMenu signupMenu, LoginMenu loginMenu)
    {
        INPUT = input;
        MESSAGE = message;
        SIGNUP_MENU = signupMenu;
        LOGIN_MENU = loginMenu;
    }

    public void show(){
        System.out.println("welcome to the our program");
        while (true) {
            System.out.println("""
                Choose your menu option:
                1 -> Signup
                2 -> Login
                3 -> Exit
                """);
            switch (INPUT.scanner.next()) {
                case "1":
                    SIGNUP_MENU.show();
                    break;
                case "2":
                    LOGIN_MENU.show();
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());
            }
        }
    }
}
