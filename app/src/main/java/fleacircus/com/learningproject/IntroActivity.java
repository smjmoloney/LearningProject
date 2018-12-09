package fleacircus.com.learningproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {

    private Button enterApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        enterApp = findViewById(R.id.enterButton);
        enterApp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openLogIn();
            }

        });
    }

        public void openLogIn() {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
}
