package zientek.lukasz.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuizResults extends AppCompatActivity
{
    private TextView results;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);
        results = findViewById(R.id.results);
        String score = getIntent().getStringExtra("RESULTS");

        results.setText(score);
    }

    public void toMenu(View view)
    {
        finish();
    }
}
