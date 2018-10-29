package fleacircus.com.learningproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import fleacircus.com.learningproject.UserCreation.UserCreationActivity;

public class HomeActivity extends AppCompatActivity {
    public static final String PREF_USER_FIRST_TIME = "true";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (PREF_USER_FIRST_TIME.equals("true"))
            startActivity(new Intent(this, UserCreationActivity.class));
    }
}