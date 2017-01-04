package info.mmstudio.timetable.Fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Максим on 04.01.2017.
 */

public class DBHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TimeTableDB";
    public static final String TABLE_LESSONS= "Lessons";

    public static final String KEY_ID = "_id";
    public static final String KEY_LESONS_NAME = "Lessons_name";
    public static final String KEY_LESSONS_TYPE = "Lessons_type";
    int k;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_LESSONS+ "(" + KEY_ID
                + " integer primary key," + KEY_LESONS_NAME + " text," + KEY_LESSONS_TYPE + " text" + ")");



    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + TABLE_LESSONS);

        onCreate(db);


    }

    public void delete(String TABLE_NAME,String KEY){



    }

}
