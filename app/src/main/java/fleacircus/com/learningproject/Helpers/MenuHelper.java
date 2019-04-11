package fleacircus.com.learningproject.Helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import fleacircus.com.learningproject.Adapters.FindUserAdapter;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.LoginActivity;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;

public class MenuHelper {
    public static void toggleMenuVisibility(Menu menu, boolean isVisible) {
        MenuItem logout = menu.findItem(R.id.action_logout);
        MenuItem search = menu.findItem(R.id.action_search);
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

    public static void onCreateOptionsMenuSearch(Menu menu, MenuItem menuItem, Activity activity) {
        View content = activity.findViewById(android.R.id.content);
        View cover = activity.findViewById(R.id.overlayAlpha);
        RecyclerView find = activity.findViewById(R.id.recyclerViewFind);

        long duration = (long) activity.getResources().getInteger(R.integer.duration_default);
        float alpha = (float) activity.getResources().getInteger(R.integer.alpha_transparent_default) / 100;

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                MenuHelper.toggleMenuVisibility(menu, false);
                CustomAnimationUtils.circleAnimation(
                        content,
                        find,
                        duration,
                        false);

                find.setVisibility(View.VISIBLE);
                CustomAnimationUtils.alphaAnimation(cover, 0, alpha, duration / 2);

                SearchView searchView = (SearchView) menuItem.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText.isEmpty()) {
                            find.setAdapter(new FindUserAdapter(null));
                            return false;
                        }

                        String input = searchView.getQuery().toString();
                        FindHelper.findUsers(input, find, activity);
                        return false;
                    }
                });

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                activity.invalidateOptionsMenu();
                Animator anim = CustomAnimationUtils.circleAnimation(
                        content,
                        find,
                        duration,
                        true);

                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        find.setVisibility(View.INVISIBLE);
                    }
                });

                CustomAnimationUtils.alphaAnimation(cover, alpha, 0, duration * 2);

                return true;
            }
        });

        find.setAdapter(new FindUserAdapter(null));
    }

    public static void onOptionsItemSelected(AppCompatActivity activity, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                CustomUser.updateInstance(new CustomUser());

                activity.startActivity(new Intent(activity, LoginActivity.class));
                break;
        }
    }
}
