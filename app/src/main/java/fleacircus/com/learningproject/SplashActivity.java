package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;

public class SplashActivity extends AppCompatActivity {

    long duration = 2000;

    private void splash() {
        Intent intent = new Intent(this, HomeActivity.class);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null)
            intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void alpha() {
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        CustomAnimationUtils.alphaAnimation(constraintLayout, 1, 0, (long) (duration * .35));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        CustomAnimationUtils.progressBarAnimation(progressBar, 0, 100, duration);

        Handler handler = new Handler();
        handler.postDelayed(this::alpha, (long) (duration * .65));
        handler.postDelayed(this::splash, duration);
    }
}
