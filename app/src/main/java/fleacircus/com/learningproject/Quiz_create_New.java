package fleacircus.com.learningproject;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Quiz_create_New extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_create_new);

        // use Floating Action Button to create Quiz and take input for Quiz Name
        FloatingActionButton fab = findViewById(R.id.createNewQuizBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Quiz_create_New.this, "Create New Quiz", Toast.LENGTH_SHORT).show();
                openQuizNameDialogAction();
            }
        });

    }

    private void openQuizNameDialogAction() {
        // start new activity when button clicked
        Quiz_create_NameDialogBox dialog = new Quiz_create_NameDialogBox();
        dialog.show(getSupportFragmentManager(), "dialog");
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

    private void ExitQuiz() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
