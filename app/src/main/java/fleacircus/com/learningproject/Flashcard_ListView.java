package fleacircus.com.learningproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


import fleacircus.com.learningproject.Flashcard.Flashcard_ListAdapter;
import fleacircus.com.learningproject.Flashcard.Flashcard_Listing;


public class Flashcard_ListView extends AppCompatActivity {

    private static final String TAG = "Flashcard_ListView";

    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference flashcardRef = db.collection("FlashcardList").document(uid)
            .collection("flashcardList_"+uid);
    private Flashcard_ListAdapter fAdapter;



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

                // retrieve document ID (quizName)
                String id = documentSnapshot.getId();
                Toast.makeText(Flashcard_ListView.this,
                        " FlashcardName: " + id, Toast.LENGTH_SHORT).show();
                // create a new intent to start new activity
                Intent intent = new Intent(Flashcard_ListView.this, Flashcard_mainActivity.class);
                // store and pass over quizName to new activity
                intent.putExtra("flashcard_Name", String.valueOf(id));
                startActivity(intent);
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
}