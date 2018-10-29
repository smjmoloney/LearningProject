package fleacircus.com.learningproject.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class DatabaseUtils {
    private static final DatabaseUtils ourInstance = new DatabaseUtils();
    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    static DatabaseUtils getInstance() {
        return ourInstance;
    }

    private DatabaseUtils() {
    }

    public static void AddString(String table, String value) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(table);

        String id = databaseReference.push().getKey();
        if (id != null)
            databaseReference.child(table).setValue(value);
    }

    public static void AddInt(String table, int value) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(table);

        String id = databaseReference.push().getKey();
        if (id != null)
            databaseReference.child(table).setValue(value);
    }

    public static void AddObject(String table, Object value) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(table);

        String id = databaseReference.push().getKey();
        if (id != null)
            databaseReference.child(id).setValue(value);
    }
}
