package fleacircus.com.learningproject.Flashcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import fleacircus.com.learningproject.Flashcard_create_Cards;
import fleacircus.com.learningproject.Flashcard_mainActivity;
import fleacircus.com.learningproject.R;


/**
 * A fragment representing the front of the card.
 */
@SuppressLint("ValidFragment")
public class Flashcard_CardFrontFragment extends Fragment {

    private EditText cardFrontTxt;
    private String frontTxt;
    private FragmentFrontListener listener;

    // method to be used in connection with user inputted and passing into flashcard activity
    public interface FragmentFrontListener {
        void userInputFrontSent(String input);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.flashcard_card_front, container, false);

        cardFrontTxt = view.findViewById(R.id.card_front_text);
        cardFrontTxt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                if(getActivity() instanceof Flashcard_mainActivity) {

                    String string1= "Card Front";
                    cardFrontTxt.setText(string1);

                    ((Flashcard_mainActivity)getActivity()).flipToReverseSideCard();
                }

                if(getActivity() instanceof Flashcard_create_Cards) {
                    // assign user input text to variable
                    frontTxt = cardFrontTxt.getText().toString();
                    // attach it with method to flashcard activity
                    listener.userInputFrontSent(frontTxt);
                    // flip flashcard method
                    ((Flashcard_create_Cards)getActivity()).flipToReverseSideCard(); }
            }
        });

        return view;
    }

    // method called when fragment attached to activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // check if activity implements this interface
        if(context instanceof FragmentFrontListener) {
            // take listener variable and assign it to activity
            listener = (FragmentFrontListener) context;
        }

        else {
            throw new RuntimeException(context.toString() + "missing FragmentFrontListener");
        }
    }

    // remove fragment from activity
    @Override
    public void onDetach() {
        super.onDetach();
        // reference not required so make it null
        listener = null;
    }
}