package fleacircus.com.learningproject.ItemSlider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import fleacircus.com.learningproject.R;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.CustomViewHolder> {
    private Context context;
    private List<Item> itemList;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        RelativeLayout viewBackground, viewForeground;

        CustomViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }


    public ItemListAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        final Item item = itemList.get(position);
        holder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void removeItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Item item, int position) {
        itemList.add(position, item);
        notifyItemInserted(position);
    }
}