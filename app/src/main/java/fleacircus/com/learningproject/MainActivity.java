package fleacircus.com.learningproject;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import fleacircus.com.learningproject.ItemSlider.ItemSlider;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeToolbar();

        ItemSlider.createSlider("classes.json",
                (CoordinatorLayout) findViewById(R.id.coordinator_layout),
                (RecyclerView) findViewById(R.id.recycler_view), getApplicationContext());
    }

    private void makeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        try {
            setSupportActionBar(toolbar);
        } catch (Throwable t) {
            // Egh
        }

        getSupportActionBar().setTitle(getString(R.string.app_name));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.example_menu, menu);
        return true;
    }
}