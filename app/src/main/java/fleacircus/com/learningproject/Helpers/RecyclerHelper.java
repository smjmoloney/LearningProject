package fleacircus.com.learningproject.Helpers;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerHelper {
    public static void setRecyclerView(Context context, View recycler, RecyclerView.Adapter adapter) {
        RecyclerView recyclerView = (RecyclerView) recycler;
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
