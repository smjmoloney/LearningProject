package fleacircus.com.learningproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import fleacircus.com.learningproject.Classes.CustomViewPager;
import fleacircus.com.learningproject.Adapters.SectionsPagerAdapter;
import fleacircus.com.learningproject.Login.LoginFragment;
import fleacircus.com.learningproject.Login.SignFragment;
import fleacircus.com.learningproject.Utils.NavigationUtils;

public class LoginActivity extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private CustomViewPager viewPager;

    public CustomViewPager getViewPager() {
        return viewPager;
    }

    private void setupViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment());
        adapter.addFragment(new SignFragment());

        viewPager = findViewById(R.id.viewPagerLogin);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupViewPager();
    }

    @Override
    public void onBackPressed() {
        NavigationUtils.onBackPressed(this);
    }
}
