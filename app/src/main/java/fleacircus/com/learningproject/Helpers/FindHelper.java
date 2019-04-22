package fleacircus.com.learningproject.Helpers;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import fleacircus.com.learningproject.Adapters.FindUserAdapter;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;

class FindHelper {
    static void findUsers(String text, RecyclerView find, Activity activity) {
        if (text.length() < 1) return;

        String[] collection = new String[]{"users"};
        CustomDatabaseUtils.read(collection, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                try {
                    if (isQuery) {
                        List<CustomUser> mDataset = new ArrayList<>();

                        /*
                         * The above list is populated by a QueryDocumentSnapshot
                         * which retrieves all documents within a collection. Each
                         * document/object is converted into a CustomUser class
                         * and added.
                         */
                        for (QueryDocumentSnapshot q : (QuerySnapshot) object) {
                            if (q.getId().equals(FirebaseAuth.getInstance().getUid())) continue;

                            CustomUser u = q.toObject(CustomUser.class);
                            if (u.getName() == null) continue;

                            mDataset.add(u);
                        }

                        /*
                         * The {@link RecyclerHelper#setRecyclerView(Context, View, RecyclerView.Adapter)}
                         * method will apply the provided data set to the given adapter which
                         * is then presented using our recycler view. This method is and will be
                         * used frequently, thus it has been placed within a helper class.
                         */
                        RecyclerView.Adapter adapter = new FindUserAdapter(mDataset);
                        ((FindUserAdapter) adapter).getFilter().filter(text);

                        RecyclerHelper.setRecyclerView(activity, find, adapter);
                    } else Log.e("OnSuccess", object + " must be a query.");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Log.e("FirebaseFirestoreEx", databaseError.toString());
            }
        });
    }
}
