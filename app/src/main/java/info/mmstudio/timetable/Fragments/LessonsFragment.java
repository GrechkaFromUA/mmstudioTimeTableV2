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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

public class LessonsFragment extends Fragment  implements View.OnClickListener,View.OnLongClickListener{


    LinearLayout linearLayout;
    FloatingActionButton btn_add;
    DBHelper dbHelper;
    EditText et;
    String[] les;
    TextView sign;
    SimpleAdapter sAdapter;
    ListView lv;

    final String AT_NAME_TEXT = "text";

    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    public LessonsFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

          View v = inflater.inflate(R.layout.testlists, container, false);



        btn_add = (FloatingActionButton) v.findViewById(R.id.fab);
        btn_add.setOnClickListener(this);




        sign = (TextView) v.findViewById(R.id.Sign);




        linearLayout = (LinearLayout) v.findViewById(R.id.liner_less);


        et =  new EditText(v.getContext());


        dbHelper = new DBHelper(getActivity());

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



            ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(k);
            Map<String, Object> m;



            for (int i = 0; i < l - 1; i++) {

               /* TextView textView = new TextView(v.getContext());
                textView.setText(les[i]);
                textView.setLayoutParams(lParams);
                textView.setTextSize(20);
                textView.setMaxLines(1);
                textView.setId(10+i+1);
                textView.setLongClickable(true);
                textView.setSingleLine(true);
                textView.setOnLongClickListener(this);
                linearLayout.addView(textView);
                */
                m = new HashMap<String, Object>();
                m.put(AT_NAME_TEXT, les[i]);
                data.add(m);


            }

            String[] from = {AT_NAME_TEXT};
            // массив ID View-компонентов, в которые будут вставлять данные
            int[] to = {R.id.Sign};

            sAdapter = new SimpleAdapter(getActivity(), data, R.layout.pc_list_activity_mod, from, to);


            // определяем список и присваиваем ему адаптер
            lv = (ListView) v.findViewById(R.id.listTEST);
            lv.setAdapter(sAdapter);
            lv.setLongClickable(true);
            lv.setLayoutParams(lParams);
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                    showPopupMenu(getView());
                    return false;
                }
            });


        }


        return v;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.fab:

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Добавление Предмета")
                        .setView(et)
                        .setCancelable(false)
                        .setNegativeButton("Add",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        String lessons1 = et.getText().toString();

                                        if(lessons1 !=null || lessons1 !="") {

                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put(DBHelper.KEY_LESONS_NAME, lessons1);

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
    public boolean onLongClick( View view) {
        Log.d("mLog","Click");
        showPopupMenu(view);
        return false;
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





                                dbHelper.close();
                                restart();
                                return true;
                            case R.id.menu2:

                                Log.d("mLog",Integer.toString(view.getId()));

                                //database.execSQL("DELETE FROM LESSONS WHERE Lessons_name LIKE `%"+et+"%`");
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



