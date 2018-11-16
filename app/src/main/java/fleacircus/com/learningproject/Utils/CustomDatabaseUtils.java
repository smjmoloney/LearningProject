package fleacircus.com.learningproject.Utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CustomDatabaseUtils {
    private static final CustomDatabaseUtils ourInstance = new CustomDatabaseUtils();
    private static FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();

    static CustomDatabaseUtils getInstance() {
        return ourInstance;
    }

    private CustomDatabaseUtils() {
    }

//        public static void AddString(String table, String value) {
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
    public static void AddObject(Object o, String document) {
        firebaseDatabase.collection("Users").document(document).set(o)
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

    public static void AddTest(String a, String b) {
        Map<String, Object> note = new HashMap<>();
        note.put("One", a);
        note.put("Two", b);

        firebaseDatabase.collection("Test Collection").document("First Sample").set(note)
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
}
