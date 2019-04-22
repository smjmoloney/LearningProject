package fleacircus.com.learningproject.Adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import fleacircus.com.learningproject.Classes.CustomUser;
import fleacircus.com.learningproject.FlashCardReviewActivity;
import fleacircus.com.learningproject.Interpolators.CustomBounceInterpolator;
import fleacircus.com.learningproject.QuizReviewActivity;
import fleacircus.com.learningproject.R;
import fleacircus.com.learningproject.Utils.CustomAnimationUtils;
import fleacircus.com.learningproject.Utils.CustomDatabaseUtils;
import fleacircus.com.learningproject.Utils.NavigationUtils;
import fleacircus.com.learningproject.Utils.StringUtils;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.Holder> {

    private List<DocumentSnapshot> courses;
    private String uid, foundUid = "";

    static class Holder extends RecyclerView.ViewHolder {
        private Holder(View itemView) {
            super(itemView);
        }
    }

    public CourseAdapter(List<DocumentSnapshot> courses) {
        this.courses = courses;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null)
            this.uid = user.getUid();
    }

    public CourseAdapter(List<DocumentSnapshot> courses, String foundUid) {
        this.courses = courses;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null)
            this.uid = user.getUid();

        this.foundUid = foundUid;
    }

    private void courseClickListeners(View view, int position) {
        if (!foundUid.isEmpty()) {
            view.setOnLongClickListener(v -> {
                if (foundUid.equals(CustomUser.getInstance().getLocation()))
                    copyCourse(view, courses.get(position));
                else
                    sendCourse(view, courses.get(position));

                return true;
            });
        } else {
            view.setOnLongClickListener(v -> {
                editCourse(view, courses.get(position));
                return true;
            });

            view.setOnClickListener(v -> {
                String type = courses.get(position).getString("type");
                String path = courses.get(position).getReference().getPath();
                if (type == null)
                    return;

                Activity activity = (Activity) view.getContext();
                Intent intent = new Intent(activity,
                        (type.equals("flashcard")) ? FlashCardReviewActivity.class : QuizReviewActivity.class);
                intent.putExtra("DocumentSnapshot", path);

                NavigationUtils.startActivity(activity, intent);
            });
        }
    }

    private void copyCourse(View view, DocumentSnapshot documentSnapshot) {
        String type = documentSnapshot.getString("type");
        if (type != null) {
            String[] documentFrom;
            String[] documentTo;
            String[] listFrom;
            String[] listTo;

            String courseID = documentSnapshot.getId();
            if (type.equals("flashcard")) {
                documentFrom = new String[]{"flashcard_sets", foundUid, courseID + "_" + foundUid, courseID};
                documentTo = new String[]{"flashcard_sets", uid, courseID + "_" + uid};
                listFrom = new String[]{"flashcard_list", foundUid, "flashcard_list" + "_" + foundUid, courseID};
                listTo = new String[]{"flashcard_list", uid, "flashcard_list" + "_" + uid};
            } else {
                documentFrom = new String[]{"quizzes", foundUid, courseID + "_" + foundUid, courseID};
                documentTo = new String[]{"quizzes", uid, courseID + "_" + uid};
                listFrom = new String[]{"quiz_list", foundUid, "quiz_list" + "_" + foundUid, courseID};
                listTo = new String[]{"quiz_list", uid, "quiz_list" + "_" + uid};
            }

            CustomDatabaseUtils.copyDocument(documentFrom, documentTo);
            CustomDatabaseUtils.copyDocument(listFrom, listTo);
            Toast.makeText(view.getContext(), R.string.courses_message_addition, Toast.LENGTH_SHORT).show();
        }
    }

    private void sendCourse(View view, DocumentSnapshot documentSnapshot) {
        String type = documentSnapshot.getString("type");
        if (type != null) {
            String[] documentFrom;
            String[] documentTo;
            String[] listFrom;
            String[] listTo;

            String courseID = documentSnapshot.getId();
            if (type.equals("flashcard")) {
                documentFrom = new String[]{"flashcard_sets", uid, courseID + "_" + uid, courseID};
                documentTo = new String[]{"flashcard_sets", foundUid, courseID + "_" + foundUid};
                listFrom = new String[]{"flashcard_list", uid, "flashcard_list" + "_" + uid, courseID};
                listTo = new String[]{"flashcard_list", foundUid, "flashcard_list" + "_" + foundUid};
            } else {
                documentFrom = new String[]{"quizzes", uid, courseID + "_" + uid, courseID};
                documentTo = new String[]{"quizzes", foundUid, courseID + "_" + foundUid};
                listFrom = new String[]{"quiz_list", uid, "quiz_list" + "_" + uid, courseID};
                listTo = new String[]{"quiz_list", foundUid, "quiz_list" + "_" + foundUid};
            }

            CustomDatabaseUtils.copyDocument(documentFrom, documentTo);
            CustomDatabaseUtils.copyDocument(listFrom, listTo);
            Toast.makeText(view.getContext(), R.string.courses_message_addition, Toast.LENGTH_SHORT).show();
        }
    }

    private void editCourse(View view, DocumentSnapshot documentSnapshot) {
        ConstraintLayout constraintLayoutDelete = ((View) view.getParent()).findViewById(R.id.constraintLayoutDelete);
        ConstraintLayout constraintLayoutCancel = ((View) view.getParent()).findViewById(R.id.constraintLayoutCancel);
        ConstraintLayout constraintLayoutSend = ((View) view.getParent()).findViewById(R.id.constraintLayoutSend);
        if (constraintLayoutDelete.getVisibility() == View.VISIBLE
                || constraintLayoutDelete.getVisibility() == View.VISIBLE
                || constraintLayoutSend.getVisibility() == View.VISIBLE)
            return;

        editCourseOnClickListeners(view, documentSnapshot,
                constraintLayoutDelete, constraintLayoutCancel, constraintLayoutSend);

        List<View> views = new ArrayList<>();
        views.add(constraintLayoutDelete);
        views.add(constraintLayoutCancel);

        String c = documentSnapshot.getString("creatorID");
        if (c != null) {
            if (c.equals(uid)) {
                String status = CustomUser.getInstance().getTeacherStudent();
                if (status.equals(StringUtils.toLowerCase(view.getResources().getString(R.string.answer_teacher))))
                    views.add(constraintLayoutSend);
            }
        }

        long duration = view.getResources().getInteger(R.integer.duration_default);
        editCourseAnimate(views, duration, (long) (duration * .75), true);
    }

    private void editCourseOnClickListeners(View view,
                                            DocumentSnapshot documentSnapshot,
                                            ConstraintLayout constraintLayoutDelete,
                                            ConstraintLayout constraintLayoutCancel,
                                            ConstraintLayout constraintLayoutSend) {
        View.OnClickListener onClickListenerDelete = v -> {
            String type = documentSnapshot.getString("type");
            if (type != null) {
                String[] delete;
                String[] deleteList;

                String courseID = documentSnapshot.getId();
                if (type.equals("flashcard")) {
                    delete = new String[]{"flashcard_sets", uid, courseID + "_" + uid, courseID};
                    deleteList = new String[]{"flashcard_list", uid, "flashcard_list" + "_" + uid, courseID};
                } else {
                    delete = new String[]{"quiz_list", uid, "quiz_list" + "_" + uid, courseID};
                    deleteList = new String[]{"quiz_list", uid, "quiz_list" + "_" + uid, courseID};
                }

                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            CustomDatabaseUtils.deleteDocument(delete);
                            CustomDatabaseUtils.deleteDocument(deleteList);
                            Toast.makeText(view.getContext(),
                                    R.string.courses_message_delete,
                                    Toast.LENGTH_SHORT).show();

                            courses.remove(documentSnapshot);
                            notifyDataSetChanged();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        };

        View.OnClickListener onClickListenerCancel = v -> {
            List<View> views = new ArrayList<>();
            views.add(constraintLayoutDelete);
            views.add(constraintLayoutCancel);

            String c = documentSnapshot.getString("creatorID");
            if (c != null) {
                if (c.equals(uid)) {
                    String status = CustomUser.getInstance().getTeacherStudent();
                    if (status.equals(StringUtils.toLowerCase(view.getResources().getString(R.string.answer_teacher))))
                        views.add(constraintLayoutSend);
                }
            }

            long duration = view.getResources().getInteger(R.integer.duration_default);
            editCourseAnimate(views, duration, (long) (duration * .75), false);
        };

        View.OnClickListener onClickListenerSend = v -> {
            String type = documentSnapshot.getString("type");
            if (type != null) {
                String[] documentFrom;
                String[] documentTo;
                String[] listFrom;
                String[] listTo;

                String courseID = documentSnapshot.getId();
                String location = CustomUser.getInstance().getLocation();
                if (type.equals("flashcard")) {
                    documentFrom = new String[]{"flashcard_sets", uid, courseID + "_" + uid, courseID};
                    documentTo = new String[]{"flashcard_sets", location, courseID + "_" + location};
                    listFrom = new String[]{"flashcard_list", uid, "flashcard_list" + "_" + uid, courseID};
                    listTo = new String[]{"flashcard_list", location, "flashcard_list" + "_" + location};
                } else {
                    documentFrom = new String[]{"quizzes", uid, courseID + "_" + uid, courseID};
                    documentTo = new String[]{"quizzes", location, courseID + "_" + location};
                    listFrom = new String[]{"quiz_list", uid, "quiz_list" + "_" + uid, courseID};
                    listTo = new String[]{"quiz_list", location, "quiz_list" + "_" + location};
                }

                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            CustomDatabaseUtils.copyDocument(documentFrom, documentTo);
                            CustomDatabaseUtils.copyDocument(listFrom, listTo);
                            Toast.makeText(view.getContext(),
                                    R.string.courses_message_delete,
                                    Toast.LENGTH_SHORT).show();

                            notifyDataSetChanged();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        };

        constraintLayoutDelete.setOnClickListener(onClickListenerDelete);
        constraintLayoutCancel.setOnClickListener(onClickListenerCancel);
        constraintLayoutSend.setOnClickListener(onClickListenerSend);
    }

    private void editCourseAnimate(List<View> views, long duration, long delay, boolean isEntry) {
        int d = 0;
        for (View v : views) {
            if (!isEntry) {
                CustomAnimationUtils.alphaAnimation(v, 1, 0, delay, true);
            } else {
                Animation fabPress = CustomAnimationUtils.loadAnimation(v.getContext(), R.anim.animation_pop_entry);
                fabPress.setInterpolator(new CustomBounceInterpolator(0.2, 10));
                fabPress.setDuration(duration);
                fabPress.setStartOffset(d);
                fabPress.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        CustomAnimationUtils.alphaAnimation(v, 0, 1, delay, true);
                        v.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                v.startAnimation(fabPress);
                d += delay;
            }
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_course, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        View view = holder.itemView.findViewById(R.id.materialRippleLayout);
        courseClickListeners(view, position);

        String courseCreator = courses.get(position).getString("creatorID");

        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        if (courses.get(position).getLong("currentScore") != null) {
            //noinspection ConstantConditions
            int cs = courses.get(position).getLong("currentScore").intValue();
            if (cs == 0)
                progressBar.setVisibility(View.INVISIBLE);
            else
                progressBar.setProgress(cs);

            if (courseCreator != null) {
                if (courseCreator.equals(uid)) {
                    progressBar.getProgressDrawable().setColorFilter(
                            view.getResources().getColor(R.color.orange_accent), PorterDuff.Mode.SRC_ATOP);

                    ImageView image = view.findViewById(R.id.imageViewCircle);
                    image.setColorFilter(view.getResources().getColor(R.color.orange_light), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }

        String courseName = StringUtils.toUpperCase(courses.get(position).getString("name"));
        TextView textViewCourseName = view.findViewById(R.id.textViewCourseName);
        TextView textViewCreator = view.findViewById(R.id.textViewCourseCreator);
        if (courseName != null && courseCreator != null) {
            textViewCourseName.setText(courseName);
            if (courseCreator.equals(uid))
                textViewCreator.setText(view.getResources().getString(R.string.home_created_by, "YOU"));
            else
                textViewCreator.setText(view.getResources().getString(R.string.home_created_by, courseCreator));
        }
    }

    @Override
    public int getItemCount() {
        return (courses != null) ? courses.size() : 0;
    }
}