package fleacircus.com.learningproject.Listeners;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This interface is used in conjunction with database tools.
 * Data is retrieved asynchronously and is not instantly presented
 * when accessed. OnGetDataListener will provide this data to
 * the method that originally began this process.
 */
public interface OnGetDataListener {
    public void onStart();
    /**
     * @param object This parameter may be a DocumentSnapshot
     *               or QuerySnapshot and is passed a generic
     *               object to support this possibility. These
     *               snapshot are selections of data that have
     *               been retrieved from our database.
     * @param isQuery This parameter determines which type of
     *                snapshot will be passed. This value is available
     *                to allow a simple check for methods in which
     *                this listener is used.
     */
    public void onSuccess(Object object, boolean isQuery);
    public void onFailed(FirebaseFirestoreException databaseError);
}
