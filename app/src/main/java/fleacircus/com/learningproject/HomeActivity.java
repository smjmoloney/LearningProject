package fleacircus.com.learningproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.CustomClasses.CustomUser;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.MenuUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

//        FirebaseAuth.getInstance().signOut();
        checkIfLoggedInAndHasSetupAccount();
    }

    private void checkIfLoggedInAndHasSetupAccount() {
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                else
                    applyCurrentUserOrSetup();
            }
        });
    }

    private void applyCurrentUserOrSetup() {
        CustomDatabaseUtils.read("users", FirebaseAuth.getInstance().getCurrentUser().getUid(), new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                if (!isQuery) {
                    CustomUser user = ((DocumentSnapshot) object).toObject(CustomUser.class);

                    if (user != null)
                        CustomUser.updateInstance(user);
                    else
                        FirebaseAuth.getInstance().signOut();

                    if (CustomUser.getInstance().getName() == null)
                        startActivity(new Intent(HomeActivity.this, UserCreationActivity.class));
                } else
                    Log.e("OnSuccess", "Must not be a query.");
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Log.e("FirebaseFirestoreEx", databaseError.toString());
            }
        });
    }

    /**
     * When the back button, available on Android devices, is pressed.
     */
    @Override
    public void onBackPressed() {
        NavigationUtils.onBackPressed(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuUtils.onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuUtils.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }
}