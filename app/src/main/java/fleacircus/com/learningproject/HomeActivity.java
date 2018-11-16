package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fleacircus.com.learningproject.Utils.SharedPreferencesUtils;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        if (SharedPreferencesUtils.readBoolean(this, "firstStart", true))
            startActivity(new Intent(this, UserCreationActivity.class));
    }
}