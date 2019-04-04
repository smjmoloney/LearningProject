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


public class Flashcard_introActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_flashcard_introactivity);

        ConstraintLayout create = findViewById(R.id.create_layout);
        create.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right));
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Flashcard_introActivity.this, Flashcard_create_New.class));
            }
        });

        ConstraintLayout learn = findViewById(R.id.learn_layout);
        learn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_left));
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Flashcard_introActivity.this, Flashcard_library.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.flashcard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.exit_flashcard:
                exitFlashcard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void exitFlashcard() {
        // return to Home Activity screen upon exiting Flashcard set up
        Intent intent = new Intent(this, WorkspaceActivity.class);
        startActivity(intent);
    }
}