package fleacircus.com.learningproject.Listeners;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

public interface OnGetDataListener {
    public void onStart();
    public void onSuccess(DocumentSnapshot data);
    public void onFailed(FirebaseFirestoreException databaseError);
}
