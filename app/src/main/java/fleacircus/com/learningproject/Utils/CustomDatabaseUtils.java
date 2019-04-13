package fleacircus.com.learningproject.Utils;

import android.util.Log;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

import fleacircus.com.learningproject.Classes.CustomDocument;
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

                        listener.onSuccess(null, false);
                    } else {
                        listener.onFailed(null);
                    }
                });
    }

    public static void copyDocument(String[] from, String[] to, String classTypeInCollection) {
        final DocumentReference originalDocument = (DocumentReference) retrieveCollectionOrDocument(from);
        final CollectionReference destinationCollection = (CollectionReference) retrieveCollectionOrDocument(to);
        originalDocument.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot == null)
                return;

            try {
                Object customDocument = documentSnapshot.toObject(Class.forName(
                        "fleacircus.com.learningproject." + classTypeInCollection));

                if (customDocument == null)
                    return;

                DocumentReference duplicateDocument = destinationCollection.document(documentSnapshot.getId());
                duplicateDocument.set(customDocument);

                copyChildCollections(originalDocument, duplicateDocument, documentSnapshot);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
    }

    private static void copyDocument(final DocumentReference originalDocument,
                                     final DocumentReference documentReference,
                                     final String classType) {
        originalDocument.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot == null)
                return;

            try {
                Object customDocument = documentSnapshot.toObject(Class.forName(
                        "fleacircus.com.learningproject." + classType));
                if (customDocument == null)
                    return;

                documentReference.set(customDocument);

                copyChildCollections(originalDocument, documentReference, documentSnapshot);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
    }

    public static void deleteDocument(String[] document) {
        DocumentReference originalDocument = (DocumentReference) retrieveCollectionOrDocument(document);
        originalDocument.delete().addOnSuccessListener(aVoid -> Log.e("SUCCESS", "SUCCESS"));
    }

    private static void copyChildCollections(DocumentReference originalDocument,
                                             DocumentReference duplicateDocument,
                                             DocumentSnapshot documentSnapshot) {
        final CustomDocument customDocument = documentSnapshot.toObject(CustomDocument.class);
        if (customDocument == null)
            return;

        List<String> children = customDocument.getChildren();
        for (String child : children) {
            final CollectionReference originalCollection = originalDocument.collection(child);
            final CollectionReference collectionReference = duplicateDocument.collection(child);

            originalCollection.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult() == null)
                        return;

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference originalDocument1 = originalCollection.document(document.getId());
                        DocumentReference documentReference = collectionReference.document(document.getId());
                        copyDocument(originalDocument1, documentReference, customDocument.getClassTypeInCollection());
                    }
                }
            });
        }
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

    private static Object retrieveCollectionOrDocument(String[] location) {
        FirebaseFirestore instance = FirebaseFirestore.getInstance();

        CollectionReference collectionReference = instance.collection(location[0]);
        DocumentReference documentReference = null;
        for (int i = 1; i < location.length; i++) {
            if (i % 2 == 0)
                collectionReference = documentReference.collection(location[i]);
            else
                documentReference = collectionReference.document(location[i]);
        }

        return (location.length % 2) - 1 == 0 ? collectionReference : documentReference;
    }
}
