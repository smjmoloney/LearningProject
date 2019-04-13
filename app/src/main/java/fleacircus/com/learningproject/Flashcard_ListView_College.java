package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import fleacircus.com.learningproject.Flashcard.Flashcard_ListAdapter;
import fleacircus.com.learningproject.Flashcard.Flashcard_Listing;
import fleacircus.com.learningproject.UserCreation.CustomUser;

public class Flashcard_ListView_College extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Flashcard_ListAdapter fAdapter;
    private String front, back;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcard_listview);

        String collegeLocation = CustomUser.getInstance().getLocation();

        setUpFlashcardListView(collegeLocation);
    }

    private void setUpFlashcardListView(final String collegeLocation) {

        CollectionReference flashcardRef = db.collection("FlashcardList").document(collegeLocation)
                .collection("flashcardList_"+collegeLocation);

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
                DocumentReference docRef = db.collection("FlashcardSets").document(collegeLocation)
                        .collection(flashcardName + " - " + collegeLocation).document(flashcardName);
                docRef.get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot snapshot = task.getResult();
                                if (snapshot.exists()) {
                                    front = snapshot.getString("Card_Front_1");
                                    back = snapshot.getString("Card_Back_1");
                                    count = snapshot.getDouble("count").intValue();

                                    Toast.makeText(Flashcard_ListView_College.this,
                                            " FlashcardName: " + flashcardName, Toast.LENGTH_SHORT).show();

                                    // create a new intent to start new activity and send flashcard data to next activity
                                    final Intent intent = new Intent(Flashcard_ListView_College.this, Flashcard_mainActivity_college.class);
                                    // store and pass over quizName to new activity
                                    intent.putExtra("flashcard_Name", String.valueOf(flashcardName));
                                    intent.putExtra("front_data", String.valueOf(front));
                                    intent.putExtra("back_data", String.valueOf(back));
                                    intent.putExtra("count", count);
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
        Intent intent = new Intent(this, WorkspaceActivity.class);
        startActivity(intent);
    }
}