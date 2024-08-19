package menu;

import menu.util.Input;
import menu.util.Message;

import service.StudentService;
import util.AuthHolder;

import java.sql.SQLException;

public class LoginMenu {

    private final Input INPUT;
    private final Message MESSAGE;
    private final LoginSubmenu LOGIN_SUBMENU;
    private final AuthHolder authHolder;
    private final StudentService STUDENT_SERVICE;

    public LoginMenu(Input INPUT, Message MESSAGE, LoginSubmenu loginSubmenu
            , AuthHolder authHolder, StudentService studentService) {
        this.INPUT = INPUT;
        this.MESSAGE = MESSAGE;
        this.LOGIN_SUBMENU = loginSubmenu;
        this.authHolder = authHolder;
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
                    login();
                    System.out.println(MESSAGE.getInvalidInputMessage());
                    break;
                }
                case "2": {
                    break LoginMenu;
                }
                default:
                    System.out.println(MESSAGE.getInvalidInputMessage());
            }

        }


    }

    private void login() {
        System.out.println(MESSAGE.getInputMessage("userName"));
        String username = INPUT.scanner.next();
        System.out.println(MESSAGE.getInputMessage("password"));
        String password = INPUT.scanner.next();
                    if (STUDENT_SERVICE.login(username, password)) {
                        System.out.println(MESSAGE.getSuccessfulMessage("login "));
        LOGIN_SUBMENU.show();
                        authHolder.reset();

//                    }
    }
}
}
