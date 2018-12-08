package fleacircus.com.learningproject.Listeners;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public abstract class AbstractFragment extends Fragment {

    protected IActivityEnabledListener aeListener;

    protected interface IActivityEnabledListener {
        void onActivityEnabled(FragmentActivity activity);
    }

    protected void getAvailableActivity(IActivityEnabledListener listener) {
        if (getActivity() == null)
            aeListener = listener;
        else
            listener.onActivityEnabled(getActivity());
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        if (aeListener != null) {
//            aeListener.onActivityEnabled((FragmentActivity) activity);
//            aeListener = null;
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (aeListener != null) {
            aeListener.onActivityEnabled((FragmentActivity) context);
            aeListener = null;
        }
    }
}