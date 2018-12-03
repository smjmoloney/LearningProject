package fleacircus.com.learningproject.Listeners;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public interface OnGetDataListener {
    public void onStart();
    /**
     * DocumentSnapshot
     * QuerySnapshot
     * @param object
     * @param isQuery
     */
    public void onSuccess(Object object, boolean isQuery);
    public void onFailed(FirebaseFirestoreException databaseError);
}
