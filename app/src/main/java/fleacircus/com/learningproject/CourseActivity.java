package fleacircus.com.learningproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import fleacircus.com.learningproject.Listeners.OnGetDataListener;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.MenuUtils;

public class CourseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);

        questionTest("example", "topic");
    }

    private void questionTest(String course, String topic) {
        final String question = "example question";

        CustomDatabaseUtils.testRead(course, topic, question, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object object, boolean isQuery) {
                TextView questionView = findViewById(R.id.question);
                questionView.setText(question);

                Map<String, Object> map = ((DocumentSnapshot) object).getData();
                LinearLayout answers = findViewById(R.id.answers);

                List<Map.Entry<String, Object>> list = new ArrayList<>(map.entrySet());
                Collections.shuffle(list);

                int i = 0;
                for (Map.Entry<String, Object> entry : list) {
                    Map<String, Object> values = (Map<String, Object>) entry.getValue();

                    TextView answer = (TextView) answers.getChildAt(i);
                    answer.setText((String) values.get("answer"));

                    if ((boolean) values.get("isCorrect"))
                        answer.setTextColor(getResources().getColor(R.color.green));

                    i++;
                }
            }

            @Override
            public void onFailed(FirebaseFirestoreException databaseError) {
                Log.e("FirebaseFirestoreEx", databaseError.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuUtils.onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuUtils.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }
}
