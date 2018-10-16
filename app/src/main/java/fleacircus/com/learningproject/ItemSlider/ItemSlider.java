package fleacircus.com.learningproject.ItemSlider;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ItemSlider {
    public static void createSlider(String filename, final CoordinatorLayout cl, RecyclerView rv, Context context) {
        final List<Item> itemList = new ArrayList<>();
        final ItemListAdapter itemAdapter = new ItemListAdapter(context, itemList);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        rv.setAdapter(itemAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                        if (viewHolder instanceof ItemListAdapter.CustomViewHolder) {
                            String name = itemList.get(viewHolder.getAdapterPosition()).getName();

                            final Item deletedItem = itemList.get(viewHolder.getAdapterPosition());
                            final int deletedIndex = viewHolder.getAdapterPosition();

                            itemAdapter.removeItem(viewHolder.getAdapterPosition());

                            Snackbar snackbar = Snackbar.make(cl, name + " has been removed!", Snackbar.LENGTH_LONG);
                            snackbar.setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    itemAdapter.restoreItem(deletedItem, deletedIndex);
                                }
                            });
                            snackbar.setActionTextColor(Color.YELLOW);
                            snackbar.show();
                        }
                    }
                });
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv);

//        try {
//            JSONArray temp = new JSONObject(getJson(filename, context)).getJSONArray("topics");
//            List<Item> items = new Gson().fromJson(temp.toString(),
//                    new TypeToken<List<Item>>() {
//                    }.getType());
//
//            if (!items.isEmpty()) {
//                itemList.clear();
//                itemList.addAll(items);
//
//                itemAdapter.notifyDataSetChanged();
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        List<Item> items = new Gson().fromJson(getJson(filename, context),
                new TypeToken<List<Item>>() {
                }.getType());

        if (!items.isEmpty()) {
            itemList.clear();
            itemList.addAll(items);

            itemAdapter.notifyDataSetChanged();
        }
    }

    private static String getJson(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return json;
    }
}
