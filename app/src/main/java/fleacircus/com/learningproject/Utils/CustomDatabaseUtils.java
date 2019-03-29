package fleacircus.com.learningproject.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;

public class CustomDatabaseUtils {

    private CustomDatabaseUtils() {
    }

    public static void addOrUpdateUserDocument(Object o) {
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //noinspection ConstantConditions
        String uid = auth.getCurrentUser().getUid();
        instance.collection("users")
                .document(uid)
                .set(o)
                .addOnSuccessListener(aVoid -> Log.e("Success", "Object added/updated successfully."))
                .addOnFailureListener(e -> Log.e("Exception", e.toString()));
    }

    public static void addUser(EditText email,
                               EditText password,
                               final OnGetDataListener listener) {
        listener.onStart();

        String e = email.getText().toString();
        String p = password.getText().toString();

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(e, p)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        addOrUpdateUserDocument(CustomUser.getInstance());

                        listener.onStart();
                    } else {
                        listener.onFailed(null);
                    }
                });
    }

    public static void loginUser(String email,
                                 String password,
                                 final OnGetDataListener listener) {
        listener.onStart();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        listener.onSuccess(null, false);
                    else
                        listener.onFailed(null);
                });
    }

    public static void read(String[] names, final OnGetDataListener listener) {
        if (names.length < 1)
            return;

        listener.onStart();

        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = instance.collection(names[0]);
        DocumentReference documentReference = null;

        if (names.length == 1) {
            collectionReference.addSnapshotListener((queryDocumentSnapshots, e) -> {
                if (queryDocumentSnapshots != null)
                    listener.onSuccess(queryDocumentSnapshots, true);
                else
                    listener.onFailed(e);
            });

            return;
        }

        for (int i = 1; i < names.length; i++) {
            if (i % 2 == 0)
                collectionReference = documentReference.collection(names[i]);
            else
                documentReference = collectionReference.document(names[i]);
        }

        if ((names.length % 2) - 1 == 0) {
            collectionReference.addSnapshotListener((queryDocumentSnapshots, e) -> {
                if (queryDocumentSnapshots != null)
                    listener.onSuccess(queryDocumentSnapshots, true);
                else
                    listener.onFailed(e);
            });
        } else {
            documentReference.addSnapshotListener((documentSnapshot, e) -> {
                if (documentSnapshot != null)
                    listener.onSuccess(documentSnapshot, false);
                else
                    listener.onFailed(e);
            });
        }
    }

    public static void copyDocument(Object object, String[] to) {
        if (to.length < 1) {
            Log.e("OnSuccess", "No path given (to).");
            return;
        }

        FirebaseFirestore instance = FirebaseFirestore.getInstance();

        CollectionReference collectionReference = instance.collection(to[0]);
        DocumentReference documentReference = null;

        for (int i = 1; i < to.length; i++) {
            if (i % 2 == 0)
                collectionReference = documentReference.collection(to[i]);
            else
                documentReference = collectionReference.document(to[i]);
        }

        collectionReference.add(object);
    }
}
