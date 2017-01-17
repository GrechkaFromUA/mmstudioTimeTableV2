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
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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

public class SubjectsFragment extends Fragment  implements View.OnClickListener,View.OnLongClickListener{


    LinearLayout linearLayout;
    FloatingActionButton btn_add;
    DBHelper dbHelper;
    EditText et,et2;
    String[] les;
    TextView sign;
    SimpleAdapter sAdapter;
    ListView lv;

    final String AT_NAME_TEXT = "text";

    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    public SubjectsFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.testlists, container, false);



        btn_add = (FloatingActionButton) v.findViewById(R.id.fab);
        btn_add.setOnClickListener(this);




        sign = (TextView) v.findViewById(R.id.Sign);




        linearLayout = (LinearLayout) v.findViewById(R.id.liner_less);


        et =  new EditText(getActivity());



        dbHelper = new DBHelper(getActivity());

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("create table if not exists "+DBHelper.TABLE_SUBJECTS+" ("+DBHelper.KEY_ID+" integer primary key, "+DBHelper.KEY_SUBJECT_NAME+" text)");
        Cursor cursor = database.query(DBHelper.TABLE_SUBJECTS, null, null, null, null, null, null);


        int k = cursor.getCount();

        les= new String[k+1];
        if (cursor.moveToFirst()) {

            int lessIndex = cursor.getColumnIndex(DBHelper.KEY_SUBJECT_NAME);
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
                    showPopupMenu(arg1,arg2);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Adding Subject Type")
                        .setView(et)
                        .setCancelable(false)
                        .setNegativeButton("Add",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        String lessons1 = et.getText().toString();

                                        if(TextUtils.isEmpty(lessons1)) {
                                            Toast.makeText(getActivity(),"You cant add empty name",Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                            restart();

                                        }else{


                                            Log.d("mLog",lessons1);
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put(DBHelper.KEY_SUBJECT_NAME, lessons1);

                                            SQLiteDatabase database = dbHelper.getWritableDatabase();
                                            database.insert(DBHelper.TABLE_SUBJECTS, null, contentValues);
                                            dialog.cancel();
                                            restart();




                                        }
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

        return  false;
    }




    public void restart(){
        dbHelper.close();
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();


    }





    private void showPopupMenu(final View view, final int id1) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view, Gravity.RIGHT);
        popupMenu.inflate(R.menu.popupmenu);
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {



                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        final SQLiteDatabase database = dbHelper.getWritableDatabase();
                        switch (item.getItemId()) {

                            case R.id.menu1:

                                et2 =  new EditText(getActivity());
                                et2.setText(les[id1]);
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Rename Type")
                                        .setView(et2)
                                        .setCancelable(false)
                                        .setPositiveButton("Rename",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                                                        database.execSQL("UPDATE "+DBHelper.TABLE_SUBJECTS+" SET "+DBHelper.KEY_SUBJECT_NAME+" = '"+et2.getText().toString()+"' WHERE "+DBHelper.KEY_SUBJECT_NAME+" = '"+les[id1]+"'");
                                                        Log.d("mLog","123:"+et2.getText().toString());
                                                        restart();
                                                    }
                                                }) .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {


                                                restart();
                                            }
                                        })
                                ;
                                AlertDialog alert = builder.create();
                                alert.show();



                                restart();
                                return true;
                            case R.id.menu2:


                                database.execSQL("DELETE  FROM "+DBHelper.TABLE_SUBJECTS+" WHERE "+DBHelper.KEY_SUBJECT_NAME+" = '"+les[id1]+"'");
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



