package fleacircus.com.learningproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


import fleacircus.com.learningproject.Flashcard.Flashcard_ListAdapter;
import fleacircus.com.learningproject.Flashcard.Flashcard_Listing;


public class Flashcard_ListView extends AppCompatActivity {

    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference flashcardRef = db.collection("FlashcardList").document(uid)
            .collection("flashcardList_"+uid);
    private Flashcard_ListAdapter fAdapter;
    private String front, back;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.flashcard_listview);

            setUpFlashcardListView();

        }

    private void setUpFlashcardListView() {

        // query using name field in the collection documents
        Query query = flashcardRef.orderBy("name", Query.Direction.DESCENDING);

        // create recycler options
        FirestoreRecyclerOptions<Flashcard_Listing> options = new FirestoreRecyclerOptions.Builder<Flashcard_Listing>()
                .setQuery(query, Flashcard_Listing.class)
                .build();

        fAdapter = new Flashcard_ListAdapter(options);

        RecyclerView flashcardListView = findViewById(R.id.flashcardListing);
        flashcardListView.setHasFixedSize(true);
        flashcardListView.setLayoutManager(new LinearLayoutManager(this));
        flashcardListView.setAdapter(fAdapter);

        fAdapter.setOnItemClickListener(new Flashcard_ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                // retrieve document (flashcardName)
                final String flashcardName = documentSnapshot.getId();
                DocumentReference docRef = db.collection("FlashcardSets").document(uid)
                        .collection(flashcardName + "_" + uid).document(flashcardName);
                docRef.get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot snapshot = task.getResult();
                                if (snapshot.exists()) {
                                    front = snapshot.getString("Card_Front_1");
                                    back = snapshot.getString("Card_Back_1");

                        Toast.makeText(Flashcard_ListView.this,
                                " FlashcardName: " + flashcardName, Toast.LENGTH_SHORT).show();

                        // create a new intent to start new activity and send flashcard data to next activity
                        final Intent intent = new Intent(Flashcard_ListView.this, Flashcard_mainActivity.class);
                        // store and pass over quizName to new activity
                        intent.putExtra("flashcard_Name", String.valueOf(flashcardName));
                        intent.putExtra("front_data", String.valueOf(front));
                        intent.putExtra("back_data", String.valueOf(back));
                        startActivity(intent);
                                }
                            }
                        });
            }
        });
    }

    protected void onStart() {
        super.onStart();
        fAdapter.startListening();
    }

    protected void onStop() {
        super.onStop();
        fAdapter.stopListening();
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

    private void exitFlashcard() {
        // return to Home Activity screen upon exiting Flashcard set up
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}