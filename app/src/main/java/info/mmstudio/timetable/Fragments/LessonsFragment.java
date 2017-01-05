package info.mmstudio.timetable.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.mmstudio.timetable.R;

public class LessonsFragment extends Fragment  implements View.OnClickListener,View.OnLongClickListener{



    LinearLayout linearLayout;
    Button btn_add;
    DBHelper dbHelper;
    EditText et;
    String[] les;

    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;


    public LessonsFragment(){}




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lessons, container, false);



        btn_add = (Button) v.findViewById(R.id.add_less);
        btn_add.setOnClickListener(this);




        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });








        linearLayout = (LinearLayout) v.findViewById(R.id.liner_less);


        et =  new EditText(v.getContext());


        dbHelper = new DBHelper(v.getContext());

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_LESSONS, null, null, null, null, null, null);




        int k = cursor.getCount();
        les= new String[k+1];
        if (cursor.moveToFirst()) {

            int lessIndex = cursor.getColumnIndex(DBHelper.KEY_LESONS_NAME);
            int i = 0;
            do {

                les[i] = cursor.getString(lessIndex);
                i++;
            } while (cursor.moveToNext());
        }

        cursor.close();

        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        if(les!=null) {
            int l = les.length;
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, wrapContent);


            for (int i = 0; i < l - 1; i++) {

                TextView textView = new TextView(v.getContext());
                textView.setText(les[i]);
                textView.setLayoutParams(lParams);
                textView.setTextSize(20);
                textView.setMaxLines(1);
                textView.setId(10+i+1);
                textView.setLongClickable(true);
                textView.setSingleLine(true);
                textView.setOnLongClickListener(this);
                linearLayout.addView(textView);


            }
        }






        return v;
    }








    @Override
    public void onClick(View v) {



        switch (v.getId()){





            case R.id.add_less:

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Добавление Предмета")
                        .setView(et)
                        .setCancelable(false)
                        .setNegativeButton("Add",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        String lessons = et.getText().toString();

                                        if(lessons !=null) {
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put(DBHelper.KEY_LESONS_NAME, lessons);

                                            SQLiteDatabase database = dbHelper.getWritableDatabase();
                                            database.insert(DBHelper.TABLE_LESSONS, null, contentValues);
                                            dbHelper.close();
                                            restart();
                                            dialog.cancel();
                                        }else{dialog.cancel();}
                                    }
                                })
                ;
                AlertDialog alert = builder.create();
                alert.show();

                break;





        }


    }





    @Override
    public boolean onLongClick(final View view) {

        final String editableText=les[view.getId()-11];

        et.setText(editableText);

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(et)
                .setCancelable(false)
                .setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                dbHelper = new DBHelper(view.getContext());

                                SQLiteDatabase database = dbHelper.getWritableDatabase();

                                database.execSQL("DELETE FROM Lessons WHERE Lessons_name LIKE '%"+editableText+"%'");


                            restart();
                            }
                        })
                .setPositiveButton("Rename",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dbHelper = new DBHelper(view.getContext());

                                SQLiteDatabase database = dbHelper.getWritableDatabase();


                                database.execSQL(" UPDATE Lessons SET Lessons_name = REPLACE(Lessons_name, '"+editableText+"', '"+et.getText().toString()+"');");

                            restart();
                            }
                        })
        ;
        AlertDialog alert = builder.create();
        alert.show();


        return false;
    }




public void restart(){

    getFragmentManager().beginTransaction().detach(this).attach(this).commit();

}






}



