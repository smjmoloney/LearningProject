package fleacircus.com.learningproject.Utils;

/**
 * Tools for manipulating strings.
 */
public class StringUtils {
    public static String toLowerCase(String temp) {
        if (temp != null)
            return temp.toLowerCase();

        return null;
    }

    public static String toUpperCase(String temp) {
        if (temp != null)
            return temp.toUpperCase();

        return null;
    }

    public static String capitaliseEach(String temp) {
        temp = toLowerCase(temp);
        String[] words = temp.split(" ");

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();

            if (i > 0)
                result.append(" ");

            result.append(words[i]);
        }

        return result.toString();
    }

    public static boolean hasMatch(String a, String b) {
        a = toLowerCase(a);
        b = toLowerCase(b);
        return a.equals(b);
    }
}
