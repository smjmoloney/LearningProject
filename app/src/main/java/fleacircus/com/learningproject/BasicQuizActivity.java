package fleacircus.com.learningproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class BasicQuizActivity extends AppCompatActivity {


    Button btn_opt1, btn_opt2, btn_opt3, btn_opt4;
    TextView questionTxt;

    private Questions question = new Questions();

    private String answer;
    private int questionLength = question.questions.length;

    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_quiz);

        random = new Random();


        btn_opt1 = (Button)findViewById(R.id.option1);
        btn_opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_opt1.getText() == answer){
                    Toast.makeText(BasicQuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                    NextQuestion(random.nextInt(questionLength));
                }else{
                    GameOver();
                }
            }
        });
        btn_opt2 = (Button)findViewById(R.id.option2);
        btn_opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_opt2.getText() == answer){
                    Toast.makeText(BasicQuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                    NextQuestion(random.nextInt(questionLength));
                }else{
                    GameOver();
                }
            }
        });
        btn_opt3 = (Button)findViewById(R.id.option3);
        btn_opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_opt3.getText() == answer){
                    Toast.makeText(BasicQuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                    NextQuestion(random.nextInt(questionLength));
                }else{
                    GameOver();
                }
            }
        });
        btn_opt4 = (Button)findViewById(R.id.option4);
        btn_opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_opt4.getText() == answer){
                    Toast.makeText(BasicQuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                    NextQuestion(random.nextInt(questionLength));
                }else{
                    GameOver();
                }
            }
        });

        questionTxt = (TextView)findViewById(R.id.questionText);

        NextQuestion(random.nextInt(questionLength));


    }

    private void GameOver(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BasicQuizActivity.this);
        alertDialogBuilder
                .setMessage("Game Over")
                .setCancelable(false)
                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
        alertDialogBuilder.show();

    }

    private void NextQuestion(int num){
        questionTxt.setText(question.getQuestion(num));
        btn_opt1.setText(question.getchoice1(num));
        btn_opt2.setText(question.getchoice2(num));
        btn_opt3.setText(question.getchoice3(num));
        btn_opt4.setText(question.getchoice4(num));

        answer = question.getCorrectAnswer(num);
    }
}
