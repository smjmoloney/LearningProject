package fleacircus.com.learningproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import fleacircus.com.learningproject.Classes.CustomCourse;
import fleacircus.com.learningproject.Interpolators.CustomBounceInterpolator;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;
import fleacircus.com.learningproject.Utils.ToastUtils;

public class QuizCreateActivity extends AppCompatActivity {

    @BindView(R.id.editTextQuestion) EditText editTextQuestion;
    @BindView(R.id.editTextOptionOne) EditText editTextOption1;
    @BindView(R.id.editTextOptionTwo) EditText editTextOption2;
    @BindView(R.id.editTextOptionThree) EditText editTextOption3;
    @BindView(R.id.editTextOptionFour) EditText editTextOption4;
    private EditText editTextCurrentlySelected;

    public void reset() {
        editTextQuestion.setText("");
        editTextOption1.setText("");
        editTextOption2.setText("");
        editTextOption3.setText("");
        editTextOption4.setText("");
        editTextCurrentlySelected = null;

        editTextOption1.setTextColor(getResources().getColor(R.color.blue_off_black));
        editTextOption1.setTypeface(null, Typeface.NORMAL);
        editTextOption2.setTextColor(getResources().getColor(R.color.blue_off_black));
        editTextOption2.setTypeface(null, Typeface.NORMAL);
        editTextOption3.setTextColor(getResources().getColor(R.color.blue_off_black));
        editTextOption3.setTypeface(null, Typeface.NORMAL);
        editTextOption4.setTextColor(getResources().getColor(R.color.blue_off_black));
        editTextOption4.setTypeface(null, Typeface.NORMAL);
    }

    @OnLongClick({R.id.editTextOptionOne, R.id.editTextOptionTwo, R.id.editTextOptionThree, R.id.editTextOptionFour})
    boolean onLongClick(EditText editTextCurrentlySelected) {
        if (this.editTextCurrentlySelected != null) {
            this.editTextCurrentlySelected.setTextColor(getResources().getColor(R.color.blue_off_black));
            this.editTextCurrentlySelected.setTypeface(null, Typeface.NORMAL);
        }

        editTextCurrentlySelected.setTextColor(getResources().getColor(R.color.green));
        editTextCurrentlySelected.setTypeface(null, Typeface.BOLD);
        this.editTextCurrentlySelected = editTextCurrentlySelected;

        ToastUtils.show(this, "Answer has been selected.", R.integer.duration_default);

        return true;
    }

    @OnClick(R.id.fabViewCheckmark)
    public void saveQuizQuestion() {
        CustomBounceInterpolator interpolator = new CustomBounceInterpolator(0.2, 10);
        FloatingActionButton fabViewSwap = findViewById(R.id.fabViewCheckmark);

        Animation fabPress = CustomAnimationUtils.loadAnimation(this, R.anim.animation_pop);
        fabPress.setInterpolator(interpolator);
        fabViewSwap.startAnimation(fabPress);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) return;

        if (editTextCurrentlySelected == null) {
            ToastUtils.show(this, getString(R.string.quiz_answer_choose), R.integer.duration_default);
            return;
        }

        if (editTextQuestion == null || editTextOption1 == null || editTextOption2 == null || editTextOption3 == null || editTextOption4 == null)
            return;

        String question = editTextQuestion.getText().toString();
        String answer = editTextCurrentlySelected.getText().toString();
        String option1 = editTextOption1.getText().toString();
        String option2 = editTextOption2.getText().toString();
        String option3 = editTextOption3.getText().toString();
        String option4 = editTextOption4.getText().toString();

        EditText editTextQuizName = findViewById(R.id.editTextQuizName);
        String textTitle = editTextQuizName.getText().toString();
        if (textTitle.isEmpty() || question.isEmpty() || answer.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty()) {
            Toast.makeText(this, getString(R.string.prompt_failure), Toast.LENGTH_SHORT).show();
            return;
        }

        String courseID = textTitle + "_" + user.getUid();
        CustomCourse customCourse = new CustomCourse();
        customCourse.setName(textTitle);
        customCourse.setCourseID(courseID);
        customCourse.setCreatorID(user.getUid());
        customCourse.setType("quiz");
        customCourse.setDescription("Test description");
        customCourse.setDatetime(Long.toString(System.currentTimeMillis()));

        Random rand = new Random();
        int randomNum = rand.nextInt((5000 - 1000) + 1) + 1000;
        customCourse.setCurrentScore(randomNum);

        String[] quizData = new String[]{question, answer, option1, option2, option3, option4};
        CustomDatabaseUtils.addQuiz(customCourse, quizData, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                Toast.makeText(QuizCreateActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                reset();
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Toast.makeText(QuizCreateActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                reset();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_create);

        ButterKnife.bind(this, findViewById(android.R.id.content));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(view -> NavigationUtils.startActivity(this, new Intent(this, HomeActivity.class)));
        }
    }
}
