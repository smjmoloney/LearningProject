package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import fleacircus.com.learningproject.Classes.CustomCourse;
import fleacircus.com.learningproject.FlashCard.CardCreateBackFragment;
import fleacircus.com.learningproject.FlashCard.CardCreateFrontFragment;
import fleacircus.com.learningproject.Helpers.AnimationHelper;
import fleacircus.com.learningproject.Listeners.FragmentBackListener;
import fleacircus.com.learningproject.Listeners.FragmentFrontListener;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FirebaseUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;

public class FlashCardCreateActivity extends AppCompatActivity implements FragmentFrontListener, FragmentBackListener {

    CardCreateFrontFragment cardCreateFrontFragment;
    CardCreateBackFragment cardCreateBackFragment;
    String textTitle, textFront, textBack;
    int currentFlashCardCount = 1;

    public void reset(boolean increment) {
        if (increment) currentFlashCardCount++;
        textFront = textBack = null;
        cardCreateFrontFragment = new CardCreateFrontFragment();
        cardCreateBackFragment = new CardCreateBackFragment();
        flipCard(true);
    }

    private void flashcardSetUp(Bundle savedInstanceState) {
        cardCreateBackFragment = new CardCreateBackFragment();
        cardCreateFrontFragment = new CardCreateFrontFragment();
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.constraintLayoutCard, cardCreateFrontFragment);
            transaction.commit();
        }
    }

    public void flipCard(boolean isFront) {
        AnimationHelper.popAnimation(findViewById(R.id.fabViewSwap));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in, R.animator.card_flip_left_out);
        transaction.replace(R.id.constraintLayoutCard, (isFront) ? cardCreateFrontFragment : cardCreateBackFragment);

        fragmentManager.popBackStack();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void saveFlashcard() {
        AnimationHelper.popAnimation(findViewById(R.id.fabViewCheckmark));

        EditText editTextSetName = findViewById(R.id.editTextSetName);
        textTitle = editTextSetName.getText().toString();
        if (textFront != null && textBack != null) {
            if (textFront.isEmpty() || textBack.isEmpty() || textTitle.isEmpty()) {
                Toast.makeText(this, getString(R.string.prompt_failure), Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseUser user = FirebaseUtils.getCurrentUser();
            String courseID = textTitle + "_" + user.getUid();
            CustomCourse customCourse = new CustomCourse();
            customCourse.setName(textTitle);
            customCourse.setCourseID(courseID);
            customCourse.setCreatorID(user.getUid());
            customCourse.setType("flashcard");
            customCourse.setDescription("Test description");
            customCourse.setDatetime(Long.toString(System.currentTimeMillis()));

            Random rand = new Random();
            int randomNum = rand.nextInt((5000 - 1000) + 1) + 1000;
            customCourse.setCurrentScore(randomNum);

            CustomDatabaseUtils.addFlashCard(customCourse, textFront, textBack, new OnGetDataListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(Object object, boolean isQuery) {
                    Toast.makeText(FlashCardCreateActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    reset(true);
                }

                @Override
                public void onFailed(FirebaseFirestoreException databaseError) {
                    Toast.makeText(FlashCardCreateActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    reset(false);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_create);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(view -> NavigationUtils.startActivity(this, new Intent(this, HomeActivity.class)));
        }

        flashcardSetUp(savedInstanceState);
    }

    @Override
    public void userInputBackSent(String input) {
        textBack = input;
    }

    @Override
    public void userInputFrontSent(String input) {
        textFront = input;
    }
}
