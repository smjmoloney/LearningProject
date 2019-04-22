package fleacircus.com.learningproject.Helpers;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.LayoutAnimationController;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;

public class RecyclerHelper {
    public static void setRecyclerView(Context context, View recycler, RecyclerView.Adapter adapter) {
        RecyclerView recyclerView = (RecyclerView) recycler;
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public static void recyclerEntryAnimation(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() != null) {
            Parcelable recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

            /*
             * Animate items within our recycler view
             */
            Context context = recyclerView.getContext();
            LayoutAnimationController controller = CustomAnimationUtils.loadLayoutAnimation(context, R.anim.layout_up);

            recyclerView.setLayoutAnimation(controller);
            recyclerView.scheduleLayoutAnimation();

            if (recyclerViewState != null)
                recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }
}
