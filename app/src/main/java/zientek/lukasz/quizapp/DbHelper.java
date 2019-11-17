package zientek.lukasz.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "Quiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public DbHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " + QuizContract.QuestionsTable.TABLE_NAME +
                " ( " + QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_RIGHT_ANSWER + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_SWITCHER_POSITION + " INTEGER " + ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestions()
    {
        Question q1 = new Question("Which mobile operating system is the best?", "Android" , "Windows Phone", "iOS","Symbian OS", "A", -1);
        addQuestion(q1);
        Question q2 = new Question("Is android the best?", null , null, null,null, null, 1);
        addQuestion(q2);
        Question q3 = new Question("Are iPhones any good?", null , null, null,null, null, 0);
        addQuestion(q3);
        Question q4 = new Question("Which mobile phone manufacturers are the best?", "Apple" , "Xiaomi", "OnePlus","OPPO", "BCD", -1);
        addQuestion(q4);
        Question q5 = new Question("Why android phones are better than iPhones?", "They work" , "Customization", "USB-C","Headphone jack", "ABCD", -1);
        addQuestion(q5);
        Question q6 = new Question("If you could buy an iPhone, would you do it?", "Nope" , "Never", "Absolutely not","No.", "ABCD", -1);
        addQuestion(q6);
        Question q7 = new Question("Which processor is used in OnePlus 6T?", "Snapdragon 855" , "Snapdragon 845", "MAD2WD1","A7", "B", -1);
        addQuestion(q7);
    }

    private void addQuestion(Question question)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizContract.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        contentValues.put(QuizContract.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        contentValues.put(QuizContract.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        contentValues.put(QuizContract.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        contentValues.put(QuizContract.QuestionsTable.COLUMN_OPTION4, question.getOption4());
        contentValues.put(QuizContract.QuestionsTable.COLUMN_RIGHT_ANSWER, question.getRightAnswer());
        contentValues.put(QuizContract.QuestionsTable.COLUMN_SWITCHER_POSITION, question.getSwitcherPosition());
        db.insert(QuizContract.QuestionsTable.TABLE_NAME, null, contentValues);
    }


    public List<Question> getQuestions()
    {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME, null);
        if(cursor.moveToFirst())
        {
            do{
                Question question = new Question();
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(cursor.getString(cursor.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION4)));
                question.setRightAnswer(cursor.getString(cursor.getColumnIndex(QuizContract.QuestionsTable.COLUMN_RIGHT_ANSWER)));
                question.setSwitcherPosition(cursor.getInt(cursor.getColumnIndex(QuizContract.QuestionsTable.COLUMN_SWITCHER_POSITION)));
                questionList.add(question);

            }while (cursor.moveToNext());
        }

        cursor.close();
        return questionList;
    }

}
