package fleacircus.com.learningproject.Helpers;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.R;

public class MenuHelper {
    public static void toggleMenuVisibility(Menu menu, boolean isVisible) {
        MenuItem find = menu.findItem(R.id.action_find);
        MenuItem logout = menu.findItem(R.id.action_logout);
        MenuItem search = menu.findItem(R.id.action_search);
        find.setVisible(isVisible);
        logout.setVisible(isVisible);
        search.setVisible(isVisible);
    }

    private static void onCreateSearchMenu(AppCompatActivity activity, Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null)
            searchView = (SearchView) searchItem.getActionView();

        if (searchView != null) {
            searchView.setQueryHint(activity.getString(R.string.users_find));
            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));
        }
    }

    public static void onCreateOptionsMenu(AppCompatActivity activity, Menu menu, boolean hasSearch) {
        activity.getMenuInflater().inflate(R.menu.menu, menu);

        if (hasSearch)
            onCreateSearchMenu(activity, menu);
    }

    public static void onOptionsItemSelected(AppCompatActivity activity, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                CustomUser.updateInstance(new CustomUser());
                break;
        }
    }
}
