package fleacircus.com.learningproject.CustomList;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CustomListView {
    public static void setRecyclerView(Context context, View recycler, RecyclerView.Adapter adapter) {
        RecyclerView recyclerView = (RecyclerView) recycler;
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
