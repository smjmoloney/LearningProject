package fleacircus.com.learningproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Flashcard_save_to_library extends AppCompatDialogFragment {

    private EditText newFCName;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userFlashcardName;
    private int count;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.flashcard_create_namedialogbox, null);

        // retrieve intent from previous activity (Flashcard_mainActivity_College) and store data in variables
        Bundle mArgs = getArguments();
        String collegeFlashcardName = mArgs.getString("flashcard_Name");
        String collegeLocation = mArgs.getString("college_location");

        // retrieve flashcards from college library
        final DocumentReference collegeRef = db.collection("FlashcardSets").document(collegeLocation)
                .collection(collegeFlashcardName + " - " + collegeLocation).document(collegeFlashcardName);

        // set up the User's library
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DocumentReference userRef = db.collection("FlashcardSets").document(uid);

        builder.setView(view)
                .setTitle("Save Flashcard")
                .setMessage("Do you wish to save to your personal library?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        collegeRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot snapshot) {

                                // get the Flashcard Name from the input String
                                userFlashcardName = newFCName.getText().toString();
                                count = snapshot.getDouble("count").intValue();

                                // add the Flashcard to the user's FlashcardSets using Flashcard Name
                                Map<String, Object> newFlashcard = new HashMap<>();
                                newFlashcard.put("count", count);

                                int i;
                                for (i = 1; i <=count; i++) {

                                    // get Card Front and Back from Firestore
                                    // use i as counter for flashcard number
                                    String front = snapshot.getString("Card_Front_" + i);
                                    String back = snapshot.getString("Card_Back_" + i);

                                    // add Card Front and Back from firestore to HashMap
                                    // use i as counter for flashcard number
                                    newFlashcard.put("Card_Front" + "_" + i, front);
                                    newFlashcard.put("Card_Back" + "_" + i, back);
                                }

                                // add the Flashcard to the user's FlashcardListing using new Flashcard Name
                                Map<String, Object> flashcardList = new HashMap<>();
                                flashcardList.put("name", userFlashcardName);
                                db.collection("FlashcardList").document(uid)
                                        .collection("flashcardList_"+uid).document(userFlashcardName)
                                        .set(flashcardList);

                                // add the Flashcard to the user's FlashcardSet using new Flashcard Name
                                userRef.collection(userFlashcardName + "_" + uid).document(userFlashcardName)
                                        .set(newFlashcard)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                Log.d("Save_to_Library", "Success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Save_to_Library", "Error!");
                                            }
                                        });
                                }
                        });
                    }
                });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        newFCName = view.findViewById(R.id.newFlashCardName);

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
