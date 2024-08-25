package util;
import menu.util.Input;
import menu.util.Message;
public class Common {
    private final Input INPUT;
    private final Message MESSAGE;

    public Common(Input input, Message message) {
        INPUT = input;
        MESSAGE = message;
    }

    public  <T extends Enum<T>> T getEnumChoice(Class<T> enumClass) {

        System.out.println("Please select a " + enumClass.getSimpleName() + ":");
        T[] enumConstants = enumClass.getEnumConstants();
        for (int i = 0; i < enumConstants.length; i++) {
            System.out.println((i + 1) + ". " + enumConstants[i]);
        }
        System.out.println(MESSAGE.getInputMessage("Number Of Your Choice"));
        int choice = INPUT.scanner.nextInt();

        if (choice > 0 && choice <= enumConstants.length) {
            return enumConstants[choice - 1];
        } else {
            System.out.println(MESSAGE.getInvalidInputMessage());
            return null;
        }
    }
}
