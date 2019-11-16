package zientek.lukasz.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class Quiz extends AppCompatActivity
{
    private TextView question;
    private TextView scoreText;
    private TextView questionNumber;
    private RadioGroup rbg;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Switch aSwitch;
    private ProgressBar mProgressBar;

    private List<Question> questionList;
    private Question currentQuestion;
    private int position = 0;
    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        question = findViewById(R.id.question);
        questionNumber = findViewById(R.id.question_number);
        scoreText = findViewById(R.id.score);
        rbg = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.option_1);
        rb2 = findViewById(R.id.option_2);
        rb3 = findViewById(R.id.option_3);
        rb4 = findViewById(R.id.option_4);
        aSwitch = findViewById(R.id.switch_id);
        mProgressBar = findViewById(R.id.progress);


        DbHelper dbHelper = new DbHelper(this);
        questionList = dbHelper.getQuestions();
        showQuestion();

    }

    public void showQuestion()
    {
        rbg.clearCheck();
        aSwitch.setChecked(false);

        if(position < questionList.size())
        {
            currentQuestion = questionList.get(position);

            if(currentQuestion.getSwitcherPosition() != -1)
            {
                aSwitch.setVisibility(View.VISIBLE);
                rbg.setVisibility(View.INVISIBLE);
            }

            else
            {
                aSwitch.setVisibility(View.INVISIBLE);
                rbg.setVisibility(View.VISIBLE);
                rb1.setText(currentQuestion.getOption1());
                rb2.setText(currentQuestion.getOption2());
                rb3.setText(currentQuestion.getOption3());
                rb4.setText(currentQuestion.getOption4());
            }

            question.setText(currentQuestion.getQuestion());
            questionNumber.setText("Question " + (position+1) + "/" + questionList.size());
            mProgressBar.setProgress((position+1)*100 / questionList.size());
            position++;
        }

        else
        {
            finish();
        }
    }

    public void clearAnswer(View view)
    {
        rbg.clearCheck();
        aSwitch.setChecked(false);
    }

    public void checkAnswer(View view)
    {

        if(currentQuestion.getSwitcherPosition() != -1)
        {
            int rightAnswer = currentQuestion.getSwitcherPosition();
            int userAnswer = aSwitch.isChecked() ? 1 : 0;

            if(userAnswer == rightAnswer)
                score++;

            else
                score-=1000;

            scoreText.setText("Score: " + score);
        }

        else
        {
            RadioButton rbSelected = findViewById(rbg.getCheckedRadioButtonId());
            int userAnswer = rbg.indexOfChild(rbSelected) + 1;

            if(userAnswer == currentQuestion.getRightAnswer())
                score++;

            else
                score-=1000;

            scoreText.setText("Score: " + score);

        }

        showQuestion();
    }
}
