package fleacircus.com.learningproject.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreation.CustomUser;
import fleacircus.com.learningproject.UserCreationActivity;

/**
 * Utility class to assist with database queries and editing tools.
 */
public class CustomDatabaseUtils {
    public static void addOrUpdateObject(String collection, String document, Object o, final ProgressDialog progressDialog) {
        FirebaseFirestore.getInstance()
                .collection(collection)
                .document(document)
                .set(o)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Log.e("MESSAGE", "SUCCESS");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.e("MESSAGE", "FAIL");
                    }
                });
    }

    public static void addUser(final Context context, final ProgressDialog progressDialog,
                               final TextView confirmPrompt, final String emailText, String passwordText) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            CustomUser.getInstance().setEmail(emailText);
                            addOrUpdateObject("users", FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    CustomUser.getInstance(), progressDialog);
                            context.startActivity(new Intent(context, UserCreationActivity.class));
                        } else {
                            progressDialog.dismiss();
                            confirmPrompt.setText(R.string.setup_confirm_prompt_email);
                        }
                    }
                });
    }

    public static void login(final ProgressDialog progressDialog, String emailText, String passwordText, final OnGetDataListener listener) {
        listener.onStart();
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            listener.onSuccess(task.getResult(), false);
                        else
                            listener.onFailed(null);

                        progressDialog.dismiss();
                    }
                });
    }

    public static void read(String collection, final String document, final OnGetDataListener listener) {
        listener.onStart();
        FirebaseFirestore.getInstance()
                .collection(collection)
                .document(document)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null)
                            listener.onSuccess(documentSnapshot, false);
                        else
                            listener.onFailed(e);
                    }
                });
    }

    public static void readMultipleUsersWhere(String location, String course, final OnGetDataListener listener) {
        listener.onStart();
        FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("location", location)
                .orderBy("course")
                .startAt(course)
                .endAt(course)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null)
                            listener.onSuccess(queryDocumentSnapshots, true);
                        else
                            listener.onFailed(e);
                    }
                });
    }

    public static void readMultipleCourses(String createLearn, final OnGetDataListener listener) {
        listener.onStart();
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection(createLearn)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null)
                            listener.onSuccess(queryDocumentSnapshots, true);
                        else
                            listener.onFailed(e);
                    }
                });
    }

    public static void readMultipleTopics(String course, final OnGetDataListener listener) {
        listener.onStart();
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("learn")
                .document(course)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null)
                            listener.onSuccess(documentSnapshot, false);
                        else
                            listener.onFailed(e);
                    }
                });
    }

    public static void testRead(String course, String topic, String question, final OnGetDataListener listener) {
        listener.onStart();
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("learn")
                .document(course)
                .collection(topic)
                .document(question)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null)
                            listener.onSuccess(documentSnapshot, false);
                        else
                            listener.onFailed(e);
                    }
                });
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                        if (queryDocumentSnapshots != null)
//                            listener.onSuccess(queryDocumentSnapshots, true);
//                        else
//                            listener.onFailed(e);
//                    }
//                });
    }
}
