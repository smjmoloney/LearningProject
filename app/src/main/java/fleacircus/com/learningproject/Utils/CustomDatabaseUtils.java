package fleacircus.com.learningproject.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

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

import javax.annotation.Nullable;

import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.UserCreation.CustomUser;
import fleacircus.com.learningproject.UserCreationActivity;

public class CustomDatabaseUtils {
    private static final CustomDatabaseUtils ourInstance = new CustomDatabaseUtils();

    static CustomDatabaseUtils getInstance() {
        return ourInstance;
    }

    private CustomDatabaseUtils() {
    }

    public static void addObject(String collection, String document, Object o) {
        FirebaseFirestore.getInstance().collection(collection).document(document).set(o)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("MESSAGE", "SUCCESS");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("MESSAGE", "FAIL");
                    }
                });
    }

    public static void addUser(final Context context, String message,
                               final String emailText, String passwordText) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.show();

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            CustomUser.getInstance().setEmail(emailText);

                            addObject("users", firebaseAuth.getCurrentUser().getUid(),
                                    CustomUser.getInstance());

                            progressDialog.dismiss();
                            SharedPreferencesUtils.saveBoolean(context, "hasSetupAccount", false);

                            context.startActivity(new Intent(context, UserCreationActivity.class));
                        }
                    }
                });
    }

    public static void loginUser(final Context context, String emailText,
                                 String passwordText, final OnGetDataListener listener) {
        listener.onStart();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            listener.onSuccess(null);
                        else
                            listener.onFailed(null);
                    }
                });
    }

    public static void read(String collection, final String document, final OnGetDataListener listener) {
        listener.onStart();
        FirebaseFirestore.getInstance().collection(collection).document(document)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null)
                            listener.onSuccess(documentSnapshot);
                        else
                            listener.onFailed(e);
                    }
                });
    }
}
