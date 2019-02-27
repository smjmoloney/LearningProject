package fleacircus.com.learningproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Quiz_create_NewQuestions extends AppCompatActivity {

    private static final String TAG = "Quiz_create_NewQuestions";

    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_OPTION1 = "option1";
    private static final String KEY_OPTION2 = "option2";
    private static final String KEY_OPTION3 = "option3";
    private static final String KEY_OPTION4 = "option4";

    private String quizName;
    private EditText questionTxt;
    private EditText answerTxt;
    private EditText option1Txt;
    private EditText option2Txt;
    private EditText option3Txt;
    private EditText option4Txt;
    private int count;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_create_newquestions);

        questionTxt = findViewById(R.id.quizQuestion);
        answerTxt = findViewById(R.id.quizAnswer);
        option1Txt = findViewById(R.id.quizOpt1);
        option2Txt = findViewById(R.id.quizOpt2);
        option3Txt = findViewById(R.id.quizOpt3);
        option4Txt = findViewById(R.id.quizOpt4);

        // intent used in connection with Quiz_create_NameDialogBox Activity
        Intent results = getIntent();
        // data passed in from Quiz_create_NameDialogBox Activity
        quizName = results.getStringExtra("QuizName");

        // set question count to 1 to start adding questions below
        count = 1;

        // use Floating Action Button to add questions
        FloatingActionButton fab = findViewById(R.id.saveQuestionBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveQuiz();
            }
        });

    }

    public void saveQuiz() {

        String question = questionTxt.getText().toString();
        String answer = answerTxt.getText().toString();
        String option1 = option1Txt.getText().toString();
        String option2 = option2Txt.getText().toString();
        String option3 = option3Txt.getText().toString();
        String option4 = option4Txt.getText().toString();

        // add the Input Strings to the relevant sections
        // - include count so as to not overwrite data already in database
        Map<String, Object> quiz = new HashMap<>();
        quiz.put(KEY_QUESTION+"_"+count, question);
        quiz.put(KEY_ANSWER+"_"+count, answer);
        quiz.put(KEY_OPTION1+"_"+count, option1);
        quiz.put(KEY_OPTION2+"_"+count, option2);
        quiz.put(KEY_OPTION3+"_"+count, option3);
        quiz.put(KEY_OPTION4+"_"+count, option4);

        // get the User ID
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // save the quiz to user's main collection_userID with Quiz Name
        db.collection("Quizzes").document(uid)
                .collection(quizName+"_"+uid).document(quizName)
                .set(quiz)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Quiz_create_NewQuestions.this, "Question saved & added to Quiz", Toast.LENGTH_SHORT).show();

                        // update the question count
                        db.collection("Quizzes").document(uid)
                                .collection(quizName+"_"+uid).document(quizName)
                                .update("count",count);
                        count++;

                        // make all the text fields blank after save completed
                        questionTxt.setText("");
                        answerTxt.setText("");
                        option1Txt.setText("");
                        option2Txt.setText("");
                        option3Txt.setText("");
                        option4Txt.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Quiz_create_NewQuestions.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}