package fleacircus.com.learningproject.Utils;

import android.util.Log;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fleacircus.com.learningproject.Classes.CustomCourse;
import fleacircus.com.learningproject.Classes.CustomDocument;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;

public class CustomDatabaseUtils {
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_OPTION1 = "option1";
    private static final String KEY_OPTION2 = "option2";
    private static final String KEY_OPTION3 = "option3";
    private static final String KEY_OPTION4 = "option4";

    private CustomDatabaseUtils() {
    }

    public static void addOrUpdateUserDocument(Object o) {
        String uid = FirebaseUtils.getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .set(o)
                .addOnSuccessListener(aVoid -> Log.e("Success", "Object added/updated successfully."))
                .addOnFailureListener(e -> Log.e("Exception", e.toString()));
    }

    public static void addFlashCard(CustomCourse customCourse, String textFront, String textBack,
                                    OnGetDataListener listener) {
        String[] location = new String[]{"flashcard_sets", customCourse.getCreatorID(),
                customCourse.getCourseID(), customCourse.getName()};
        DocumentReference documentReference = (DocumentReference) retrieveCollectionOrDocument(location);
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult() == null)
                    return;

                if (task.getResult().get("name") != null) {
                    updateFlashCard(task.getResult(), documentReference, textFront, textBack, listener);
                    return;
                }

                int count = 1;
                Map<String, Object> flashcardData = new HashMap<>();
                flashcardData.put("card_front" + "_" + count, textFront);
                flashcardData.put("card_back" + "_" + count, textBack);
                flashcardData.put("count", count);
                documentReference.set(customCourse, SetOptions.merge());
                documentReference.set(flashcardData, SetOptions.merge());

                String[] locationSecondary = new String[]{"flashcard_list", customCourse.getCreatorID(),
                        "flashcard_list" + "_" + customCourse.getCreatorID(), customCourse.getName()};
                Map<String, Object> flashcardList = new HashMap<>();
                flashcardList.put("name", customCourse.getName());
                DocumentReference documentReferenceSecondary = (DocumentReference) retrieveCollectionOrDocument(locationSecondary);
                documentReferenceSecondary.set(flashcardList)
                        .addOnSuccessListener(aVoid -> listener.onSuccess(null, false))
                        .addOnFailureListener(x -> listener.onFailed(null));
            }
        });
    }

    public static void addQuiz(CustomCourse customCourse, String[] quizData, OnGetDataListener listener) {
        String[] location = new String[]{"quizzes", customCourse.getCreatorID(),
                customCourse.getCourseID(), customCourse.getName()};
        DocumentReference documentReference = (DocumentReference) retrieveCollectionOrDocument(location);
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult() == null)
                    return;

                if (task.getResult().get("name") != null) {
                    updateQuiz(task.getResult(), documentReference, quizData, listener);
                    return;
                }

                int count = 1;
                Map<String, Object> qd = new HashMap<>();
                qd.put(KEY_QUESTION + "_" + count, quizData[0]);
                qd.put(KEY_ANSWER + "_" + count, quizData[1]);
                qd.put(KEY_OPTION1 + "_" + count, quizData[2]);
                qd.put(KEY_OPTION2 + "_" + count, quizData[3]);
                qd.put(KEY_OPTION3 + "_" + count, quizData[4]);
                qd.put(KEY_OPTION4 + "_" + count, quizData[5]);
                qd.put("count", count);
                documentReference.set(customCourse, SetOptions.merge());
                documentReference.set(qd, SetOptions.merge());

                String[] locationSecondary = new String[]{"quiz_list", customCourse.getCreatorID(),
                        "quiz_list" + "_" + customCourse.getCreatorID(), customCourse.getName()};
                Map<String, Object> quizList = new HashMap<>();
                quizList.put("name", customCourse.getName());
                DocumentReference documentReferenceSecondary = (DocumentReference) retrieveCollectionOrDocument(locationSecondary);
                documentReferenceSecondary.set(quizList)
                        .addOnSuccessListener(aVoid -> listener.onSuccess(null, false))
                        .addOnFailureListener(x -> listener.onFailed(null));
            }
        });
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

    public static void copyDocument(String[] from, String[] to) {
        final DocumentReference originalDocument = (DocumentReference) retrieveCollectionOrDocument(from);
        final CollectionReference destinationCollection = (CollectionReference) retrieveCollectionOrDocument(to);
        originalDocument.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot == null)
                return;

            if (documentSnapshot.getData() == null)
                return;

            DocumentReference duplicateDocument = destinationCollection.document(documentSnapshot.getId());
            duplicateDocument.set(documentSnapshot.getData());
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

    private static void copyDocument(final DocumentReference originalDocument,
                                     final DocumentReference documentReference) {
        originalDocument.addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot == null)
                return;

            documentReference.set(originalDocument);
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

    public static Object retrieveCollectionOrDocument(String[] location) {
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

    private static void updateFlashCard(DocumentSnapshot documentSnapshot, DocumentReference documentReference,
                                        String textFront, String textBack, OnGetDataListener listener) {
        Long value = documentSnapshot.getLong("count");
        int count = 1;
        if ((value != null) && value >= 1)
            count = value.intValue() + 1;

        Map<String, Object> flashcardData = new HashMap<>();
        flashcardData.put("card_front" + "_" + count, textFront);
        flashcardData.put("card_back" + "_" + count, textBack);
        flashcardData.put("count", count);

        documentReference.set(flashcardData, SetOptions.merge())
                .addOnSuccessListener(aVoid -> listener.onSuccess(null, false))
                .addOnFailureListener(x -> listener.onFailed(null));
    }

    private static void updateQuiz(DocumentSnapshot documentSnapshot, DocumentReference documentReference,
                                   String[] quizData, OnGetDataListener listener) {
        Long value = documentSnapshot.getLong("count");
        int count = 1;
        if ((value != null) && value >= 1)
            count = value.intValue() + 1;

        Map<String, Object> qd = new HashMap<>();
        qd.put(KEY_QUESTION + "_" + count, quizData[0]);
        qd.put(KEY_ANSWER + "_" + count, quizData[1]);
        qd.put(KEY_OPTION1 + "_" + count, quizData[2]);
        qd.put(KEY_OPTION2 + "_" + count, quizData[3]);
        qd.put(KEY_OPTION3 + "_" + count, quizData[4]);
        qd.put(KEY_OPTION4 + "_" + count, quizData[5]);
        qd.put("count", count);
        documentReference.set(qd, SetOptions.merge())
                .addOnSuccessListener(aVoid -> listener.onSuccess(null, false))
                .addOnFailureListener(x -> listener.onFailed(null));
    }
}
