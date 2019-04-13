package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Flashcard_directory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcard_directory);

        TextView selectCreate = findViewById(R.id.layout_create_text);
        selectCreate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CreateAction();
            }
        });

        TextView selectCollege = findViewById(R.id.layout_college_library_text);
        selectCollege.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CollegeAction();
            }

        });

        TextView selectUser = findViewById(R.id.layout_user_library_text);
        selectUser.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                UserAction();
            }
        });

    }

    private void UserAction() {
        Toast.makeText(Flashcard_directory.this, "You selected User Library", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Flashcard_ListView.class);
        startActivity(intent);
    }

    private void CollegeAction() {
        Toast.makeText(Flashcard_directory.this, "You selected College Library", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Flashcard_ListView_College.class);
        startActivity(intent);
    }

    private void CreateAction() {
        Toast.makeText(Flashcard_directory.this, "You selected Create Flashcard", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Flashcard_create_New.class);
        startActivity(intent);
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