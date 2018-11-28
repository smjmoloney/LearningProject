package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.UserCreation.CustomUser;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            CustomDatabaseUtils.read("users", FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    new OnGetDataListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(DocumentSnapshot data) {
                            TextView temp = findViewById(R.id.textview);
                            temp.setText(data.get("name").toString());
                        }

                        @Override
                        public void onFailed(FirebaseFirestoreException databaseError) {

                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout)
            FirebaseAuth.getInstance().signOut();

        return super.onOptionsItemSelected(item);
    }
}