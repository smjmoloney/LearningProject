package fleacircus.com.learningproject.Helpers;

import fleacircus.com.learningproject.R;

public class GridImageAdapterHelper {
    private static Integer[] drawables = new Integer[]{
            R.drawable.profile_01,
            R.drawable.profile_02,
            R.drawable.profile_03,
            R.drawable.profile_04,
            R.drawable.profile_05
    };

    public static Integer[] getDrawables() {
        return drawables;
    }

    public static int getDrawable(int position) {
        return drawables[position];
    }
}
