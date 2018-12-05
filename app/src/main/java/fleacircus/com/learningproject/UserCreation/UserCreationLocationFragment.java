package fleacircus.com.learningproject.UserCreation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.UserCreationActivity;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.FragmentUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class UserCreationLocationFragment extends Fragment {

    public UserCreationLocationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_creation_location_fragment, container, false);

        if (CustomUser.getInstance().getCollegeSchool() == null)
            return rootView;

        TextView temp = rootView.findViewById(R.id.question_text);
        temp.setText(getString(R.string.user_creation_location_question,
                StringUtils.toUpperCase(CustomUser.getInstance().getCollegeSchool())));

        final Spinner locations = rootView.findViewById(R.id.location_spinner);
        CustomDatabaseUtils.read("user_creation", CustomUser.getInstance().getCollegeSchool(), new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                List<String> list = (List<String>) ((DocumentSnapshot) object).get("names");
                if (list != null)
                    locations.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list));
            }


            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Log.e("FirebaseFirestoreEx", databaseError.toString());
            }
        });

        Button button = rootView.findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomUser.getInstance().setLocation(locations.getSelectedItem().toString());

                int temp = (CustomUser.getInstance().getCollegeSchool().contains("college")) ? 1 : 2;
                ((UserCreationActivity) getActivity()).getViewPager().setCurrentItem(
                        ((UserCreationActivity) getActivity()).getViewPager().getCurrentItem() + temp
                );
            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        FragmentUtils.refreshFragment(getFragmentManager(), this);
    }
}