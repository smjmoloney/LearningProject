package fleacircus.com.learningproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Flashcard_create_NameDialogBox extends AppCompatDialogFragment {

    private EditText newFCName;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String flashcardName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.flashcard_create_namedialogbox, null);

        builder.setView(view)
                .setTitle("Flashcard Set Name:")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // get the Flashcard Name from the input String
                        flashcardName = newFCName.getText().toString();

                        // get the User ID
                        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        // include a basic zero count for number of questions
                        Map<String, Object> card = new HashMap<>();
                        card.put("count", 0);
                        // save the flashcard to user's main collection_userID using Flashcard Name
                        db.collection("FlashcardSets").document(uid)
                                .collection(flashcardName+"_"+uid).document(flashcardName)
                                .set(card);

                        // add the Flashcard to the user's FlashcardListing using Flashcard Name
                        Map<String, Object> flashcardList = new HashMap<>();
                        flashcardList.put("name", flashcardName);
                        db.collection("FlashcardList").document(uid)
                                .collection("flashcardList_"+uid).document(flashcardName)
                                .set(flashcardList);

                        // start the Flashcard creation activity
                        Intent startFlashcardCreation = new Intent(getActivity(), Flashcard_create_Cards.class);
                        startFlashcardCreation.putExtra("Flashcard_Name", String.valueOf(flashcardName));
                        getActivity().startActivity(startFlashcardCreation);

                    }
                });

        newFCName = view.findViewById(R.id.newFlashCardName);

        return builder.create();
    }
}
