package fleacircus.com.learningproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import fleacircus.com.learningproject.Flashcard.Flashcard_CardBackFragment;
import fleacircus.com.learningproject.Flashcard.Flashcard_CardFrontFragment;

public class Flashcard_mainActivity extends AppCompatActivity implements Flashcard_CardBackFragment.FragmentBackListener, Flashcard_CardFrontFragment.FragmentFrontListener {

    // get the User ID
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    // Firebase connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // variables
    private Flashcard_CardFrontFragment cardFront;
    private Flashcard_CardBackFragment cardBack;
    private String flashcardName,front, back;
    private int cardCount;
    private Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcard_card);

        // retrieve intent from previous activity (Flashcard_Listview) and store data in variables
        Intent flashcardList = getIntent();
        flashcardName = flashcardList.getStringExtra("flashcard_Name");
        front = flashcardList.getStringExtra("front_data");
        back = flashcardList.getStringExtra("back_data");

        // create a bundle instance and put data in a string to pass to Fragments
        data = new Bundle();//create bundle instance
        data.putString("front_data", front);
        data.putString("back_data", back);

        // create Front and Back Fragments for Flashcard and assign data to Fragments for display on screen
        cardFront = new Flashcard_CardFrontFragment();
        cardBack = new Flashcard_CardBackFragment();
        cardFront.setArguments(data);
        cardBack.setArguments(data);


        cardCount = 1;

        // assign front of the card for initial set up
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, cardFront);
            transaction.commit();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.flashcard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.exit_flashcard:
                exitFlashcard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void exitFlashcard() {
        // return to Home Activity screen upon exiting Flashcard set up
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void generateFlashcards() {

        cardCount = 2;

        // points towards first question
        // retrieve questions from the user's Quizzes and Quiz Name
        DocumentReference docRef = db.collection("FlashcardSets").document(uid)
                .collection(flashcardName + "_" + uid).document(flashcardName);
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        DocumentSnapshot snapshot = task.getResult();

                        if (snapshot.exists()) {
                                front = snapshot.getString("Card_Front_"+cardCount);
                                back = snapshot.getString("Card_Back_"+cardCount);
                                Log.d("mainactivity", "front data: "+ front);
                                Log.d("mainactivity", "back data: "+ back);
                                data.putString("front_data", front);//put string to pass with a key value
                                data.putString("back_data", back);//put string to pass with a key value
                                cardFront.setArguments(data);//Set bundle data to fragment
                                cardBack.setArguments(data);
                        }
                    }
                });
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
    public void userInputBackSent(String input) {
        // empty as not used in this Activity
        // used in Flashcard_create_Cards Activity
    }

    @Override
    public void userInputFrontSent(String input) {
        // empty as not used in this Activity
        // used in Flashcard_create_Cards Activity
    }
}