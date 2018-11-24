package fleacircus.com.learningproject.Utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import fleacircus.com.learningproject.Listeners.OnGetDataListener;

public class CustomDatabaseUtils {
    private static final CustomDatabaseUtils ourInstance = new CustomDatabaseUtils();

    static CustomDatabaseUtils getInstance() {
        return ourInstance;
    }

    private CustomDatabaseUtils() {
    }

//
//    public static void AddString(String table, String value) {
//        DatabaseReference databaseReference = firebaseDatabase.getReference(table);
//
//        String id = databaseReference.push().getKey();
//        if (id != null)
//            databaseReference.setValue(value);
//    }
//
//    public static void AddInt(String table, int value) {
//        DatabaseReference databaseReference = firebaseDatabase.getReference(table);
//
//        String id = databaseReference.push().getKey();
//        if (id != null)
//            databaseReference.child(table).setValue(value);
//    }
//
//    public static void AddObject(String table, Object value) {
//        DatabaseReference databaseReference = firebaseDatabase.getReference(table);
//
//        String id = databaseReference.push().getKey();
//        if (id != null)
//            databaseReference.child(id).setValue(value);
//    }

    /* Must implement Firebase Authentication - SAMPLE ONLY */
    public static void addObject(Object o, String collection, String document) {
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

    public static void addTest(String a, String b) {
        Map<String, Object> note = new HashMap<>();
        note.put("One", a);
        note.put("Two", b);

        FirebaseFirestore.getInstance().collection("Test Collection").document("First Sample").set(note)
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

    public static ArrayList getArrayFromDocument(DocumentSnapshot data, String array) {
        return (ArrayList) data.get(array);
    }

//    public static void read(String collection, final String document, final String field) {
//        readValue = null;
//
//        FirebaseFirestore.getInstance().collection(collection).document(document).get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            readValue = documentSnapshot.getString(field);
//                            Log.e("MESSAGE", documentSnapshot.getString(field));
//                        }
//                    }
//                });
//
////        FirebaseFirestore.getInstance().collection(collection).document(document).get()
////                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
////                    @Override
////                    public void onSuccess(DocumentSnapshot documentSnapshot) {
////                        if (documentSnapshot.exists()) {
////                            readValue = documentSnapshot.getString(field);
////                            Log.e("MESSAGE", documentSnapshot.getString(field));
////                        }
////                    }
////                });
//    }

//    public static void readToSpinner(final Spinner spinner, String collection, final String document, final String field) {
//        FirebaseFirestore.getInstance().collection(collection).document(document).get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
//                            adapter.a
//                            textView.setText(documentSnapshot.getString(field));
//                            Log.e("MESSAGE", documentSnapshot.getString(field));
//                        }
//                    }
//                });
//    }
}
