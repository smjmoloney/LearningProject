package fleacircus.com.learningproject;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;


public class Quiz_create_NameDialogBox extends AppCompatDialogFragment {

    private EditText newQName;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String quizName;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.quiz_create_namedialogbox, null);

        builder.setView(view)
                .setTitle("Quiz Name:")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // get the QuizName from the input String
                        quizName = newQName.getText().toString();

                        // get the User ID
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        // include a basic zero count for number of questions
                        Map<String, Object> quiz = new HashMap<>();
                        quiz.put("count", 0);
                        // save the quiz to user's main collection_userID using Quiz Name
                        db.collection("Quizzes").document(uid)
                                .collection(quizName+"_"+uid).document(quizName)
                                .set(quiz);

                        // add the Quiz to the user's QuizListing using Quiz Name
                        Map<String, Object> quizList = new HashMap<>();
                        quizList.put("name", quizName);
                        db.collection("QuizList").document(uid)
                                .collection("quizList_"+uid).document(quizName)
                                .set(quizList);

                        // start the Quiz creation activity
                        Intent startQuizCreation = new Intent(getActivity(), Quiz_create_NewQuestions.class);
                        startQuizCreation.putExtra("QuizName", String.valueOf(quizName));
                        getActivity().startActivity(startQuizCreation);

                    }
                });

        newQName = view.findViewById(R.id.newQuizName);


        return builder.create();
    }
}
