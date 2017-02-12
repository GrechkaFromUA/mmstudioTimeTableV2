package info.mmstudio.timetable.Fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Максим on 04.01.2017.
 */

public class DBHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TimeTableDB";

    public static final String TABLE_TIME= "Time";

    public static final String TABLE_SUBJECTS= "Subjects";

    public static final String TABLE_TEACHERS= "Teachers";

    public static final String TABLE_BUILDS= "Builds";

    public static final String TABLE_LESSONS_TYPE= "Lessons_type";


    public static final String KEY_ID = "_id";

    public static final String KEY_TIME = "Time";

    public static final String KEY_SUBJECT_NAME = "Subject_name";

    public static final String KEY_TEACHER_NAME = "Teacher_name";

    public static final String KEY_BUILDS = "Builds_name";

    public static final String KEY_LESSONS_TYPE = "Lessons_type";











    int k;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_LESSONS_TYPE+ "(" + KEY_ID
                + " integer primary key,"+ KEY_LESSONS_TYPE + " text" + ")");






    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + TABLE_TIME);
        db.execSQL("drop table if exists " + TABLE_SUBJECTS);
        db.execSQL("drop table if exists " + TABLE_TEACHERS);
        db.execSQL("drop table if exists " + TABLE_BUILDS);
        db.execSQL("drop table if exists " + TABLE_LESSONS_TYPE);

        onCreate(db);


    }



}
