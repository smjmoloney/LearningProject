package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

    public class ResultActivity extends AppCompatActivity {

        TextView totalQuestions, correctQuestions, incorrectQuestions;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_result);

            totalQuestions = findViewById(R.id.totalTxtBox);
            correctQuestions = findViewById(R.id.correctTxtBox);
            incorrectQuestions = findViewById(R.id.incorrectTxtBox);

            // get Intent from Quiz Activity page
            Intent results = getIntent();

            // Assign the variables stored in named Items
            String questions = results.getStringExtra("total");
            String correctAns = results.getStringExtra("correct");
            String incorrectAns = results.getStringExtra("incorrect");

            // Set the text to the TextView fields
            totalQuestions.setText(questions);
            correctQuestions.setText(correctAns);
            incorrectQuestions.setText(incorrectAns);

        }
}

