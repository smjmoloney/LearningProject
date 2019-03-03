package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.UserCreation.CustomUser;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.MenuUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;

public class HomeActivity extends AppCompatActivity {

    private ImageView selectQuiz, selectFlashcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

//        FirebaseAuth.getInstance().signOut();
        checkIfLoggedInAndHasSetupAccount();

        selectQuiz = findViewById(R.id.QuizImg);
        selectQuiz.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                createNewQuiz();
            }
        });

        selectFlashcard = findViewById(R.id.FlashcardImg);
        selectFlashcard.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                createFlashCardAction();
            }

        });

    }

    private void checkIfLoggedInAndHasSetupAccount() {
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    startActivity(new Intent(HomeActivity.this, IntroActivity.class));
                else
                    applyCurrentUserOrSetup();
            }
        });
    }

    private void applyCurrentUserOrSetup() {
        CustomDatabaseUtils.read("users", FirebaseAuth.getInstance().getCurrentUser().getUid(),
                new OnGetDataListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(DocumentSnapshot data) {
                        FirebaseFirestore.getInstance().collection("users").document(
                                FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(
                                new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        CustomUser.updateInstance(documentSnapshot.toObject(CustomUser.class));
                                    }
                                });

                        if (data.get("name") == null)
                            startActivity(new Intent(HomeActivity.this, UserCreationActivity.class));
                    }

                    @Override
                    public void onFailed(FirebaseFirestoreException databaseError) {
                        Log.e("FirebaseFirestoreEx", databaseError.toString());
                    }
                });
    }

    @Override
    public void onBackPressed() {
        NavigationUtils.onBackPressed(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuUtils.onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuUtils.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }


    // start new activity to access Flashcards - create and learn
    public void createFlashCardAction() {
        Toast.makeText(HomeActivity.this, "You selected Flashcard", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Flashcard_introActivity.class);
        startActivity(intent);
    }


    // start new activity to access Quiz - create and learn
    public void createNewQuiz() {
        Toast.makeText(HomeActivity.this, "You selected Quiz", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Quiz_introActivity.class);
        startActivity(intent);
    }
}