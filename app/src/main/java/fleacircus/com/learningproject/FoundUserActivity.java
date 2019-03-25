package fleacircus.com.learningproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.Helpers.GridImageAdapterHelper;
import fleacircus.com.learningproject.Helpers.MenuHelper;

public class FoundUserActivity extends AppCompatActivity {

    private void setSelectedUser() {
        CustomUser user = (CustomUser) getIntent().getSerializableExtra("user");
        ImageView image = findViewById(R.id.image_profile);

        int imageID = user.getImageID();
        if (imageID != 0)
            image.setImageResource(GridImageAdapterHelper.getDrawable(imageID));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_user);
        setSupportActionBar(findViewById(R.id.toolbar));

        setSelectedUser();
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
