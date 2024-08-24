package menu;

import menu.util.Input;
import menu.util.Message;

import service.StudentService;
import util.UserSession;

public class LoginMenu {

    private final Input INPUT;
    private final Message MESSAGE;
    private final LoginSubmenu LOGIN_SUBMENU;
    private final UserSession USER_SESSION;
    private final StudentService STUDENT_SERVICE;

    public LoginMenu(Input INPUT, Message MESSAGE, LoginSubmenu loginSubmenu
            , UserSession userSession, StudentService studentService) {
        this.INPUT = INPUT;
        this.MESSAGE = MESSAGE;
        this.LOGIN_SUBMENU = loginSubmenu;
        this.USER_SESSION = userSession;
        STUDENT_SERVICE = studentService;
    }

    public void show() {
        LoginMenu:
        while (true) {
            System.out.println("""
                    1 -> Enter Information
                    2 -> Previous Menu
                    """);
            switch (INPUT.scanner.next()) {
                case "1": {

                    getLogin();
                    USER_SESSION.reset();
                    break LoginMenu;

                }
                case "2": {

                    break LoginMenu;
                }
                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());
            }

        }


    }

    private void getLogin() {
        System.out.println(MESSAGE.getInputMessage("userName"));
        String username = INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("password"));
        String password = INPUT.scanner.next();
        if (STUDENT_SERVICE.login(username, password)) {
            System.out.println(MESSAGE.getSuccessfulMessage("login "));
            LOGIN_SUBMENU.show();
        } else {
            System.out.println("user not found");
        }

    }
}
