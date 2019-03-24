package fleacircus.com.learningproject.Classes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;

public class CustomSpinner extends androidx.appcompat.widget.AppCompatTextView implements View.OnClickListener {

    private String mPrompt;
    private CharSequence[] mEntries;
    private int mSelection;
    private OnItemSelectedListener mListener;

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    public CustomSpinner(Context context) {
        super(context);
        init(null);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomSpinner);

            mPrompt = typedArray.getString(R.styleable.CustomSpinner_android_prompt);
            mEntries = typedArray.getTextArray(R.styleable.CustomSpinner_android_entries);

            typedArray.recycle();
        }

        mSelection = -1;
        mPrompt = (mPrompt == null) ? "" : mPrompt;

        setText(mPrompt);
        setOnClickListener(this);
    }

    public String getSelectedItem() {
        if (mSelection < 0 || mSelection >= mEntries.length) {
            return null;
        } else {
            return mEntries[mSelection].toString();
        }
    }

    public int getSelectedItemPosition() {
        return mSelection;
    }

    public void setSelection(int selection) {
        mSelection = selection;

        if (selection < 0) {
            setText(mPrompt);
        } else if (selection < mEntries.length) {
            setText(mEntries[mSelection]);
        }
    }

    public void setListener(OnItemSelectedListener listener) {
        mListener = listener;
    }

    void setEntries(List<String> values) {
        mEntries = values.toArray(new CharSequence[0]);
        AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.CustomDialogTheme)
                .setTitle(mPrompt)
                .setItems(mEntries, (dialog, which) -> {
                    mSelection = which;
                    setText(mEntries[mSelection]);
                    setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_text));
                    if (mListener != null)
                        mListener.onItemSelected(which);
                })
                .create();

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        String value = CustomUser.getInstance().getCollegeSchool();
        if (mPrompt.equals(getResources().getString(R.string.course)))
            value = "courses";

        CustomDatabaseUtils.read(new String[]{"user_creation", value}, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                try {
                    if (!isQuery) {
                        DocumentSnapshot documentSnapshot = (DocumentSnapshot) object;
                        if (!documentSnapshot.exists())
                            return;

                        List<String> values = new ArrayList<>();
                        if (mPrompt.equals(getResources().getString(R.string.course))) {
                            String loc = CustomUser.getInstance().getLocation();
                            Object names = documentSnapshot.get(loc);
                            if (!(names instanceof ArrayList))
                                return;

                            for (Object obj : (ArrayList) names)
                                if (obj instanceof String)
                                    values.add((String) obj);
                        } else {
                            Object names = documentSnapshot.get("names");
                            if (!(names instanceof ArrayList))
                                return;

                            for (Object obj : (ArrayList) names)
                                if (obj instanceof String)
                                    values.add((String) obj);
                        }

                        setEntries(values);
                    } else
                        Log.e("OnSuccess", object + " must be a query.");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Log.e("FirebaseFirestoreEx", databaseError.toString());
            }
        });
    }
}