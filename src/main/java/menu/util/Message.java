package menu.util;

public class Message {

    private static final String inputMessage = "please enter %S";
    private static final String existMessage = "%S is already exist";
    private static final String invalidInputMessage = "your input values are invalid";
    private static final String successfulMessage = "%s successful";
    private static final String unSuccessfulMessage = "%s Unsuccessful";
    private static final String notFoundMessage = "%s not found";
    private static final String failedMessage = "%s failed";


    public  String getInputMessage(String input) {
        return String.format(inputMessage, input);
    }

    public  String getExistMessage(String input) {
        return String.format(existMessage, input);
    }

    public  String getInvalidInputMessage() {
        return invalidInputMessage;
    }

    public  String getSuccessfulMessage(String input) {
        return String.format(successfulMessage, input);
    }

    public  String getunSuccessfulMessage(String input) {
        return String.format(unSuccessfulMessage, input);
    }

    public  String getNotFoundMessage(String input) {
        return String.format(notFoundMessage, input);
    }

    public  String getFailedMessage(String input) {
        return String.format(failedMessage, input);
    }
}
