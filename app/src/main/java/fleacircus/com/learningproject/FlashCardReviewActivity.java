package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import fleacircus.com.learningproject.FlashCard.CardReviewBackFragment;
import fleacircus.com.learningproject.FlashCard.CardReviewFrontFragment;
import fleacircus.com.learningproject.Helpers.AnimationHelper;
import fleacircus.com.learningproject.Interpolators.CustomBounceInterpolator;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;
import fleacircus.com.learningproject.Utils.StringUtils;
import fleacircus.com.learningproject.Utils.ToastUtils;

public class FlashCardReviewActivity extends AppCompatActivity {

    private static final String FRONT_DATA = "front_data";
    private static final String BACK_DATA = "back_data";

    CardReviewFrontFragment cardReviewFrontFragment;
    CardReviewBackFragment cardReviewBackFragment;
    String textTitle, textFront, textBack;
    int currentFlashCardCount = 1;
    int totalFlashCardCount = 1;

    DocumentSnapshot documentSnapshot;

    private void flashcardSetup(Bundle savedInstanceState) {
        String path = getIntent().getStringExtra("DocumentSnapshot");
        DocumentReference documentReference = FirebaseFirestore.getInstance().document(path);
        documentReference.get().addOnCompleteListener(task -> {
            documentSnapshot = task.getResult();
            if (documentSnapshot == null) return;
            if (!documentSnapshot.exists()) return;

            Long value = documentSnapshot.getLong("count");
            if ((value != null) && totalFlashCardCount >= 1) totalFlashCardCount = value.intValue();

            textTitle = StringUtils.toUpperCase(documentSnapshot.getString("name"));
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(textTitle);

            Bundle bundle = new Bundle();
            bundle.putString(FRONT_DATA, documentSnapshot.getString("card_front" + "_" + currentFlashCardCount));
            bundle.putString(BACK_DATA, documentSnapshot.getString("card_back" + "_" + currentFlashCardCount));

            cardReviewBackFragment = new CardReviewBackFragment();
            cardReviewFrontFragment = new CardReviewFrontFragment();
            cardReviewFrontFragment.setArguments(bundle);
            cardReviewBackFragment.setArguments(bundle);
            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.constraintLayoutCard, cardReviewFrontFragment);
                transaction.commit();
            }
        });
    }

    public void flipCard(boolean isFront) {
        AnimationHelper.popAnimation(findViewById(R.id.fabViewSwap));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in, R.animator.card_flip_left_out);
        transaction.replace(R.id.constraintLayoutCard, (isFront) ? cardReviewFrontFragment : cardReviewBackFragment);

        if (isFront) {
            fragmentManager.popBackStack();
        } else {
            fragmentManager.popBackStack();
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void generateNextFlashCard(int direction) {
        if (currentFlashCardCount + direction < 1) return;
        if (currentFlashCardCount + direction > totalFlashCardCount) return;

        currentFlashCardCount += direction;
        textFront = documentSnapshot.getString("card_front" + "_" + currentFlashCardCount);
        textBack = documentSnapshot.getString("card_back" + "_" + currentFlashCardCount);
        cardReviewBackFragment = CardReviewBackFragment.newInstance(textBack);
        CardReviewFrontFragment c = CardReviewFrontFragment.newInstance(textFront);
        getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutCard, c).commit();

        ToastUtils.show(this, textTitle + " - " + currentFlashCardCount, R.integer.duration_card_flip_half);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_review);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(view -> NavigationUtils.startActivity(this, new Intent(this, HomeActivity.class)));
        }

        flashcardSetup(savedInstanceState);
    }
}
