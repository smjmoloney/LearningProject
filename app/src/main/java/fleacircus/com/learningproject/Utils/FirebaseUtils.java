package fleacircus.com.learningproject.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUtils {
    public static FirebaseUser getCurrentUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser();
    }

    public static String getUid() {
        return getCurrentUser().getUid();
    }

    public static String getUserEmail() {
        return getCurrentUser().getEmail();
    }
}
