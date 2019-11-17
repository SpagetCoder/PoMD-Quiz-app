package zientek.lukasz.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Quiz extends AppCompatActivity
{
    private TextView question;
    private TextView scoreText;
    private TextView questionNumber;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Switch aSwitch;
    private ProgressBar mProgressBar;
    private List<Question> questionList;
    private Question currentQuestion;
    private int position = 0;
    private Integer score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        question = findViewById(R.id.question);
        questionNumber = findViewById(R.id.question_number);
        scoreText = findViewById(R.id.score);
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
        rb1.setChecked(false);
        rb2.setChecked(false);
        rb3.setChecked(false);
        rb4.setChecked(false);
        aSwitch.setChecked(false);

        if(position < questionList.size())
        {
            currentQuestion = questionList.get(position);

            if(currentQuestion.getSwitcherPosition() != -1)
            {
                aSwitch.setVisibility(View.VISIBLE);
                rb1.setVisibility(View.INVISIBLE);
                rb2.setVisibility(View.INVISIBLE);
                rb3.setVisibility(View.INVISIBLE);
                rb4.setVisibility(View.INVISIBLE);
            }

            else
            {
                aSwitch.setVisibility(View.INVISIBLE);
                rb1.setVisibility(View.VISIBLE);
                rb2.setVisibility(View.VISIBLE);
                rb3.setVisibility(View.VISIBLE);
                rb4.setVisibility(View.VISIBLE);
                rb1.setText(currentQuestion.getOption1());
                rb2.setText(currentQuestion.getOption2());
                rb3.setText(currentQuestion.getOption3());
                rb4.setText(currentQuestion.getOption4());
            }

            question.setText(currentQuestion.getQuestion());
            questionNumber.setText(R.string.question + (position+1) + "/" + questionList.size());
            mProgressBar.setProgress((position+1)*100 / questionList.size());
            position++;
        }

        else
        {
            finish();
            Intent intent = new Intent(Quiz.this, QuizResults.class);
            intent.putExtra("RESULTS", score.toString());
            startActivity(intent);
        }
    }

    public void clearAnswer(View view)
    {
        rb1.setChecked(false);
        rb2.setChecked(false);
        rb3.setChecked(false);
        rb4.setChecked(false);
        aSwitch.setChecked(false);
    }

    public void checkAnswer(View view)
    {
        if(currentQuestion.getSwitcherPosition() != -1)
        {
            int rightAnswer = currentQuestion.getSwitcherPosition();
            int userAnswer = aSwitch.isChecked() ? 1 : 0;

            if(userAnswer == rightAnswer)
            {
                score++;
                SweetAlertDialog dialog = new SweetAlertDialog(Quiz.this, SweetAlertDialog.SUCCESS_TYPE);
                dialog.setTitle("Nice!");
                dialog.setContentText("Score: + " + 1);
                dialog.setCancelable(false);
                dialog.setConfirmButton("Next", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog)
                    {
                        showQuestion();
                        sweetAlertDialog.dismiss();
                    }
                });

                dialog.show();
            }

            else
            {
                score-=1000;
                SweetAlertDialog dialog = new SweetAlertDialog(Quiz.this, SweetAlertDialog.ERROR_TYPE);
                dialog.setTitle("Wrong!");
                dialog.setContentText("Score: - " + 1000);
                dialog.setCancelable(false);
                dialog.setConfirmButton("Next", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog)
                    {
                        showQuestion();
                        sweetAlertDialog.dismiss();
                    }
                });

                dialog.show();
            }

            scoreText.setText(R.string.score + score);
        }

        else
        {
            String userAnswer = "";

            if(rb1.isChecked())
                userAnswer += "A";

            if(rb2.isChecked())
                userAnswer += "B";

            if(rb3.isChecked())
                userAnswer += "C";

            if(rb4.isChecked())
                userAnswer += "D";

            if(userAnswer.equals(currentQuestion.getRightAnswer()))
            {
                score++;
                SweetAlertDialog dialog = new SweetAlertDialog(Quiz.this, SweetAlertDialog.SUCCESS_TYPE);
                dialog.setTitle("Nice!");
                dialog.setContentText("Score: + " + 1);
                dialog.setCancelable(false);
                dialog.setConfirmButton("Next", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog)
                    {
                        showQuestion();
                        sweetAlertDialog.dismiss();
                    }
                });

                dialog.show();
            }

            else
            {
                score-=1000;
                SweetAlertDialog dialog = new SweetAlertDialog(Quiz.this, SweetAlertDialog.ERROR_TYPE);
                dialog.setTitle("Wrong!");
                dialog.setContentText("Score: - " + 1000);
                dialog.setCancelable(false);
                dialog.setConfirmButton("Next", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog)
                    {
                        showQuestion();
                        sweetAlertDialog.dismiss();
                    }
                });

                dialog.show();
            }

            scoreText.setText(R.string.score + score);
        }
    }
}
