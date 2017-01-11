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
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.mmstudio.timetable.R;

public class TeachersFragment extends Fragment {

    DBHelper dbHelper;
    String[] les;
    LinearLayout linearLayout;
    EditText et;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_teachers,container,false);


    //Підключення бази даних
        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
    //Створення бази якшо її не існує
        database.execSQL("create table if not exists Teachers (_id integer primary key, Teacher_name text)");


        Cursor cursor = database.query(DBHelper.TABLE_TEACHERS,null,null,null,null,null,null,null);

    //Вставлення даних в масив
        int k = cursor.getCount();
        les= new String[k+1];
        if (cursor.moveToFirst()) {

            int lessIndex = cursor.getColumnIndex(DBHelper.KEY_TEACHER_NAME);
            int i = 0;
            do {

                les[i] = cursor.getString(lessIndex);
                i++;
            } while (cursor.moveToNext());
        }

        cursor.close();


        //виведення на екран масива
        if(les!=null) {
            int l = les.length;
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

             linearLayout =  (LinearLayout) v.findViewById(R.id.liner_teach);





            for (int i = 0; i < l - 1; i++) {

               TextView textView = new TextView(v.getContext());
                textView.setText(les[i]);
                textView.setLayoutParams(lParams);
                textView.setTextSize(20);
                textView.setMaxLines(1);
                textView.setId(10+i+1);
                textView.setLongClickable(true);
                textView.setSingleLine(true);
                textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {


                        showPopupMenu(view);



                        return false;
                    }
                });
                linearLayout.addView(textView);




            }




        }














        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et =  new EditText(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("New Teacher")
                        .setView(et)
                        .setCancelable(false)
                        .setNegativeButton("Add",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        String lessons1 = et.getText().toString();

                                        if(lessons1 !=null || lessons1 !="") {

                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put(DBHelper.KEY_TEACHER_NAME, lessons1);

                                            SQLiteDatabase database = dbHelper.getWritableDatabase();
                                            database.insert(DBHelper.TABLE_TEACHERS, null, contentValues);
                                            dbHelper.close();
                                            restart();
                                            dialog.cancel();
                                        }else{dialog.cancel();}
                                    }
                                })
                ;
                AlertDialog alert = builder.create();
                alert.show();

            }
        });


        return v;
    }



    public void restart(){

        getFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }



    private void showPopupMenu(final View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), getView());
        popupMenu.inflate(R.menu.popupmenu); // Для Android 4.0
        // для версии Android 3.0 нужно использовать длинный вариант
        // popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
        // popupMenu.getMenu());

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {



                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Toast.makeText(PopupMenuDemoActivity.this,
                        // item.toString(), Toast.LENGTH_LONG).show();
                        // return true;
                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                        switch (item.getItemId()) {

                            case R.id.menu1:






                                return true;
                            case R.id.menu2:



                                database.execSQL("DELETE FROM Teachers WHERE _id = "+(view.getId()-10));
                                restart();
                                return true;

                            default:
                                return false;
                        }
                    }
                });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();
    }



}
