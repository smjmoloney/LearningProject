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

public class Flashcard_create_New extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcard_create_new);

        // use Floating Action Button to create Flashcards and take input for Flashcard Set Name
        FloatingActionButton fab = findViewById(R.id.createNewFlashcardBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Flashcard_create_New.this, "Create New Flashcards", Toast.LENGTH_SHORT).show();
                openFlashcardNameDialogAction();
            }
        });

    }

    private void openFlashcardNameDialogAction() {
        // start new activity when button clicked
        Flashcard_create_NameDialogBox dialog = new Flashcard_create_NameDialogBox();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.flashcard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle exit of Flashcard Set Up
        switch (item.getItemId()) {
            case R.id.exit_flashcard:
                exitFlashcard();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exitFlashcard() {
        // return to Home Activity screen upon exiting Flashcard set up
        Intent intent = new Intent(this, WorkspaceActivity.class);
        startActivity(intent);
    }
}