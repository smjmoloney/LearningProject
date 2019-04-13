package fleacircus.com.learningproject.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import fleacircus.com.learningproject.Helpers.GridImageAdapterHelper;

public class GridImageAdapter extends BaseAdapter {

    private Context context;

    public GridImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return GridImageAdapterHelper.getDrawables().length;
    }

    @Override
    public Object getItem(int position) {
        return GridImageAdapterHelper.getDrawable(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource((int) getItem(position));
        return imageView;
    }
}