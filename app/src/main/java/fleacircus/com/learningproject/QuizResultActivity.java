package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import fleacircus.com.learningproject.Utils.NavigationUtils;

public class QuizResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(view -> NavigationUtils.startActivity(
                    this, new Intent(this, HomeActivity.class)));
        }

        Intent results = getIntent();
        String total = results.getStringExtra("total");
        String correct = results.getStringExtra("correct");
        String incorrect = results.getStringExtra("incorrect");
        TextView totalQuestions = findViewById(R.id.textViewTotal);
        TextView correctQuestions = findViewById(R.id.textViewCorrect);
        TextView incorrectQuestions = findViewById(R.id.textViewIncorrect);
        totalQuestions.setText(getString(R.string.quiz_result_total, total));
        correctQuestions.setText(getString(R.string.quiz_result_correct, correct));
        incorrectQuestions.setText(getString(R.string.quiz_result_incorrect, incorrect));
    }
}
