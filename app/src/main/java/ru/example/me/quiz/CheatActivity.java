package ru.example.me.quiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "ru.example.me.quiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "ru.example.me.quiz.answer_shown";
//    public String apiVersion =  Build.VERSION.BASE_OS;
    private boolean isAnswerTRue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    public static Intent newIntent(Context packageContext, boolean awnswerTrue) {
    Intent intent = new Intent(packageContext, CheatActivity.class);
    intent.putExtra(EXTRA_ANSWER_IS_TRUE, awnswerTrue);
    return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        isAnswerTRue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);
        mShowAnswerButton = (Button)findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(isAnswerTRue){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShown(true);

                hideButton(mShowAnswerButton);
            }
        });
        TextView sdkV = (TextView) findViewById(R.id.sdk_vers);
        sdkV.setText("Версия SDK: " + Build.VERSION.SDK_INT);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void hideButton(final Button btn){
        int cx = btn.getWidth() / 2;
        int cy = btn.getHeight() / 2;
        float radius = btn.getWidth();
        Animator anim = ViewAnimationUtils
                .createCircularReveal(btn, cx, cy, radius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btn.setVisibility(View.INVISIBLE);
            }
        });
        anim.start();
    }

    private void setAnswerShown(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }



}
