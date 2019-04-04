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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.UserCreation.CustomUser;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.MenuUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;

public class WorkspaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workspace_activity);

        checkIfLoggedInAndHasSetupAccount();

        ImageView selectQuiz = findViewById(R.id.QuizImg);
        selectQuiz.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                QuizAction();
            }
        });

        ImageView selectFlashcard = findViewById(R.id.FlashcardImg);
        selectFlashcard.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                FlashCardAction();
            }

        });

        ImageView selectProfile = findViewById(R.id.profileImg);
        selectProfile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ProfileAction();
            }
        });

    }

    private void checkIfLoggedInAndHasSetupAccount() {
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    startActivity(new Intent(WorkspaceActivity.this, IntroActivity.class));
                else
                    applyCurrentUserOrSetup();
            }
        });
    }

    private void applyCurrentUserOrSetup() {
        CustomDatabaseUtils.read("users", FirebaseAuth.getInstance().getCurrentUser().getUid(), new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                if (!isQuery) {
                    CustomUser user = ((DocumentSnapshot) object).toObject(CustomUser.class);

                    if (user != null)
                        CustomUser.updateInstance(user);
                    else
                        FirebaseAuth.getInstance().signOut();

                    if (CustomUser.getInstance().getName() == null)
                        startActivity(new Intent(WorkspaceActivity.this, UserCreationActivity.class));
                } else
                    Log.e("OnSuccess", "Must not be a query.");
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
    public void FlashCardAction() {
        Toast.makeText(WorkspaceActivity.this, "You selected Flashcard", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Flashcard_introActivity.class);
        startActivity(intent);
    }


    // start new activity to access Quiz - create and learn
    public void QuizAction() {
        Toast.makeText(WorkspaceActivity.this, "You selected Quiz", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Quiz_introActivity.class);
        startActivity(intent);
    }

    // start new activity to access user Profile
    public void ProfileAction() {
        Toast.makeText(WorkspaceActivity.this, "You selected Profile", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}