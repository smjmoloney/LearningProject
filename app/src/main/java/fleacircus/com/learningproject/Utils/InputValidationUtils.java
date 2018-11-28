package fleacircus.com.learningproject.Utils;

import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Validates user input.
 */
public class InputValidationUtils {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    ".{8,}" +
                    "$");

    public static boolean validateEmail(String emailText) {
        if (emailText.isEmpty())
            return false;

        return Patterns.EMAIL_ADDRESS.matcher(emailText).matches();
    }

    public static boolean validatePassword(String passwordText) {
        if (passwordText.isEmpty())
            return false;

        return PASSWORD_PATTERN.matcher(passwordText).matches();
    }

    public static boolean validateMatch(String inputText, String otherInputText) {
        return inputText.equals(otherInputText);
    }

    public static boolean validateLength(String inputText, int length) {
        return inputText.length() < length;
    }
}
