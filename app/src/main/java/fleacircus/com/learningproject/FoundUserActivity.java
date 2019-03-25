package fleacircus.com.learningproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import fleacircus.com.learningproject.Helpers.MenuHelper;

public class FoundUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_user);
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuHelper.onCreateOptionsMenu(this, menu, true);
        MenuHelper.onCreateOptionsMenuSearch(menu, menu.findItem(R.id.action_search), this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHelper.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }
}
