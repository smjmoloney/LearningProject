package fleacircus.com.learningproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import fleacircus.com.learningproject.Flashcard.Flashcard_CardBackFragment;
import fleacircus.com.learningproject.Flashcard.Flashcard_CardFrontFragment;


public class Flashcard_create_Cards extends AppCompatActivity implements Flashcard_CardFrontFragment.FragmentFrontListener, Flashcard_CardBackFragment.FragmentBackListener {

    private Flashcard_CardBackFragment cardBack;
    private String flashcardName,front, back;

    private int count;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcard_card);

        // create Front and Back Fragments for Flashcard
        cardBack = new Flashcard_CardBackFragment();
        Flashcard_CardFrontFragment cardFront = new Flashcard_CardFrontFragment();

        // assign front of the card for initial set up
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.container, cardFront);
            transaction.commit();
        }

        // intent used in connection with Flashcard_create_NameDialogBox Activity
        // data passed in from Flashcard_create_NameDialogBox Activity
        flashcardName = getIntent().getStringExtra("Flashcard_Name");

        // set flashcard count to 1 to start adding flashcards below
        count = 1;


    }

    public void flipToFrontSideCard() {

        // use fragment manager to access front of the card from the Stack.
        FragmentManager fragmentManager = getSupportFragmentManager();

        // return the Front of the Card with saved State
        fragmentManager.popBackStack();
    }


    public void flipToReverseSideCard() {

        // Create and commit a new fragment transaction that adds the fragment for the back of
        // the card, uses custom animations, and is part of the fragment manager's back stack.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(
                R.anim.card_flip_right_in, R.anim.card_flip_right_out,
                R.anim.card_flip_left_in, R.anim.card_flip_left_out);

        transaction.replace(R.id.container, cardBack);

        fragmentManager.popBackStack();
        // Add this transaction to the back stack, allowing users to press Back
        // to get to the front of the card.
        transaction.addToBackStack(null);

        // Commit the transaction.
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.flashcard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle exit of Flashcard Set Up
        switch (item.getItemId()) {
            case R.id.exit_flashcard:
                exitFlashcard();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveFlashcard() {

        // add the Input Strings to the relevant sections
        Map<String, Object> newFlashcard = new HashMap<>();
        newFlashcard.put("Card_Front"+"_"+count, front);
        newFlashcard.put("Card_Back"+"_"+count, back);

        // get the User ID
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // save the quiz to user's main collection_userID with Quiz Name
        db.collection("FlashcardSets").document(uid)
                .collection(flashcardName+"_"+uid).document(flashcardName)
                .set(newFlashcard)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Flashcard_create_Cards.this, "Flashcard saved & added to Set", Toast.LENGTH_SHORT).show();

                        // update the question count
                        db.collection("FlashcardSets").document(uid)
                                .collection(flashcardName+"_"+uid).document(uid)
                                .update("count",count);
                        count++;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Flashcard_create_Cards.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void exitFlashcard() {
        // return to Home Activity screen upon exiting Flashcard set up
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    // user input passed from Flashcard_CardBackFragment and assigned to variable to be used in SaveFlashcard method
    @Override
    public void userInputBackSent(String input) {
        back = input;
    }

    // user input passed from Flashcard_CardFrontFragment and assigned to variable to be used in SaveFlashcard method
    @Override
    public void userInputFrontSent(String input) {
        front = input;
    }
}