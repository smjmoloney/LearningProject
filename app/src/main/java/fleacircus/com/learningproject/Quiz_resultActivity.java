package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class Quiz_resultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_resultactivity);

        TextView totalQuestions = findViewById(R.id.totalTxtBox);
        TextView correctQuestions = findViewById(R.id.correctTxtBox);
        TextView incorrectQuestions = findViewById(R.id.incorrectTxtBox);

        // get intent from Quiz Activity
        Intent results = getIntent();
        // retrieve data passed from Quiz Activity
        String questions = results.getStringExtra("total");
        String correctAns = results.getStringExtra("correct");
        String incorrectAns = results.getStringExtra("incorrect");
        // assign to text fields
        totalQuestions.setText(questions);
        correctQuestions.setText(correctAns);
        incorrectQuestions.setText(incorrectAns);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.quiz_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.exit_quiz:
                ExitQuiz();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // activity to exit Quiz to Home page
    private void ExitQuiz() {
        Intent intent = new Intent(this, WorkspaceActivity.class);
        startActivity(intent);
    }
}


