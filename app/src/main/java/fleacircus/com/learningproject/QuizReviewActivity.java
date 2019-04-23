package fleacircus.com.learningproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import fleacircus.com.learningproject.Utils.NavigationUtils;

public class QuizReviewActivity extends AppCompatActivity {

    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_OPTION1 = "option1";
    private static final String KEY_OPTION2 = "option2";
    private static final String KEY_OPTION3 = "option3";
    private static final String KEY_OPTION4 = "option4";

    private int answerTotal = 0;
    private int answerCorrect = 0;
    private int answerIncorrect = 0;
    private int questionCount = -1;
    private int currentQuestionCount = 1;

    @BindView(R.id.textViewQuestion) TextView textViewQuestion;
    @BindView(R.id.buttonOptionOne) Button buttonOption1;
    @BindView(R.id.buttonOptionTwo) Button buttonOption2;
    @BindView(R.id.buttonOptionThree) Button buttonOption3;
    @BindView(R.id.buttonOptionFour) Button buttonOption4;
    private Button[] buttons;

    DocumentSnapshot documentSnapshot;

    private void generateQuestion() {
        if (answerTotal == questionCount) {
            Intent intent = new Intent(this, QuizResultActivity.class);
            intent.putExtra("total", String.valueOf(questionCount));
            intent.putExtra("correct", String.valueOf(answerCorrect));
            intent.putExtra("incorrect", String.valueOf(answerIncorrect));
            NavigationUtils.startActivity(this, intent);
        }

        String quizQuestionText = documentSnapshot.getString(KEY_QUESTION + "_" + currentQuestionCount);
        String quizOptionText1 = documentSnapshot.getString(KEY_OPTION1 + "_" + currentQuestionCount);
        String quizOptionText2 = documentSnapshot.getString(KEY_OPTION2 + "_" + currentQuestionCount);
        String quizOptionText3 = documentSnapshot.getString(KEY_OPTION3 + "_" + currentQuestionCount);
        String quizOptionText4 = documentSnapshot.getString(KEY_OPTION4 + "_" + currentQuestionCount);
        textViewQuestion.setText(quizQuestionText);
        buttonOption1.setText(quizOptionText1);
        buttonOption2.setText(quizOptionText2);
        buttonOption3.setText(quizOptionText3);
        buttonOption4.setText(quizOptionText4);

        buttons = new Button[]{buttonOption1, buttonOption2, buttonOption3, buttonOption4};

        View.OnClickListener onClickListener = v -> {
            String quizAnswer = documentSnapshot.getString(KEY_ANSWER + "_" + currentQuestionCount);
            if (quizAnswer != null) {
                onButtonClick((Button) v, buttons, quizAnswer);
            }
        };
        buttonOption1.setOnClickListener(onClickListener);
        buttonOption2.setOnClickListener(onClickListener);
        buttonOption3.setOnClickListener(onClickListener);
        buttonOption4.setOnClickListener(onClickListener);
    }

    private void onButtonClick(Button buttonClicked, Button[] buttons, String quizAnswer) {
        answerTotal += 1;
        currentQuestionCount += 1;

        if (quizAnswer.equals(buttonClicked.getText().toString())) {
            Toast.makeText(this, "You Are Correct", Toast.LENGTH_SHORT).show();
            answerCorrect += 1;
            buttonClicked.setBackgroundColor(getResources().getColor(R.color.green));
            buttonClicked.setTextColor(getResources().getColor(R.color.blue_off_white));
        } else {
            Toast.makeText(this, "You Are Incorrect", Toast.LENGTH_SHORT).show();
            answerIncorrect += 1;
            buttonClicked.setBackgroundColor(Color.RED);

            for (Button b : buttons) {
                if (b.getText().toString().equals(quizAnswer)) {
                    b.setBackgroundColor(getResources().getColor(R.color.green));
                    b.setTextColor(getResources().getColor(R.color.blue_off_white));
                }
            }
        }

        long t = (long) 1000;
        new Handler().postDelayed(() -> {
            for (Button b : buttons) {
                b.setBackgroundColor(getResources().getColor(R.color.blue_bright_dark));
                b.setTextColor(getResources().getColor(R.color.blue_off_white));
            }
            generateQuestion();
        }, t);
    }

    private void quizSetup() {
        String path = getIntent().getStringExtra("DocumentSnapshot");
        DocumentReference documentReference = FirebaseFirestore.getInstance().document(path);
        documentReference.get().addOnCompleteListener(task -> {
            documentSnapshot = task.getResult();
            if (documentSnapshot == null) return;
            if (!documentSnapshot.exists()) return;
            if (documentSnapshot.getLong("count") != null) {
                //noinspection ConstantConditions
                questionCount = documentSnapshot.getLong("count").intValue();
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(documentSnapshot.getString("name"));

                generateQuestion();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_review);

        ButterKnife.bind(this, findViewById(android.R.id.content));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(view -> NavigationUtils.startActivity(this, new Intent(this, HomeActivity.class)));
        }

        quizSetup();
    }
}
