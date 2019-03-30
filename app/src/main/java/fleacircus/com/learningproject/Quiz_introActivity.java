package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;


public class Quiz_introActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_flashcard_introactivity);

        ConstraintLayout create = findViewById(R.id.create_layout);
        create.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right));
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Quiz_introActivity.this, Quiz_create_New.class));
            }
        });

        ConstraintLayout learn = findViewById(R.id.learn_layout);
        learn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_left));
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Quiz_introActivity.this, Quiz_ListView.class));
            }
        });
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

