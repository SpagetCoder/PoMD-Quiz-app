package zientek.lukasz.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void takeAQuiz(View view)
    {
        Intent intent = new Intent(MainActivity.this, Quiz.class);
        startActivity(intent);
    }
}
