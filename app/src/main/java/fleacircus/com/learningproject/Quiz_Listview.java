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

import fleacircus.com.learningproject.Quiz.Quiz_Listing;
import fleacircus.com.learningproject.Quiz.Quiz_ListAdapter;


public class Quiz_Listview extends AppCompatActivity {

    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference quizRef = db.collection("QuizList").document(uid).collection("quizList_"+uid);
    private Quiz_ListAdapter qAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_listview);

        setUpQuizListView();

    }

    private void setUpQuizListView() {

        // query using name field in the collection documents
        Query query = quizRef.orderBy("name", Query.Direction.DESCENDING);

        // create recycler options
        FirestoreRecyclerOptions<Quiz_Listing> options = new FirestoreRecyclerOptions.Builder<Quiz_Listing>()
                .setQuery(query, Quiz_Listing.class)
                .build();

        qAdapter = new Quiz_ListAdapter(options);

        RecyclerView quizListView = findViewById(R.id.quizListing);
        quizListView.setHasFixedSize(true);
        quizListView.setLayoutManager(new LinearLayoutManager(this));
        quizListView.setAdapter(qAdapter);

        qAdapter.setOnItemClickListener(new Quiz_ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                // retrieve document ID (quizName)
                String id = documentSnapshot.getId();
                Toast.makeText(Quiz_Listview.this,
                        " QuizName: " + id, Toast.LENGTH_SHORT).show();
                // create a new intent to start new activity
                Intent intent = new Intent(Quiz_Listview.this, Quiz_mainActivity.class);
                // store and pass over quizName to new activity
                intent.putExtra("qName", String.valueOf(id));
                startActivity(intent);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        qAdapter.startListening();
    }

    protected void onStop() {
        super.onStop();
        qAdapter.stopListening();
    }
}
