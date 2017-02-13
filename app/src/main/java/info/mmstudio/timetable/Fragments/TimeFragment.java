package info.mmstudio.timetable.Fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.mmstudio.timetable.R;

public class TimeFragment extends Fragment {

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


    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.testlists,container,false);
        final FragmentManager fm = getFragmentManager();
        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.plus));



        dbHelper = new DBHelper(getActivity());

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("create table if not exists "+DBHelper.TABLE_TIME+" ("+DBHelper.KEY_ID+" integer primary key, "+DBHelper.KEY_TIME+" text)");
        Cursor cursor = database.query(DBHelper.TABLE_TIME, null, null, null, null, null, null);


        int k = cursor.getCount();

        les= new String[k+1];
        if (cursor.moveToFirst()) {

            int lessIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fm.beginTransaction().replace(R.id.content_frame, new TimePickerFragment()).commit();

                getActivity().setTitle("Виберыть Час");
            }
        });

        return v;
    }


    private void showPopupMenu(final View view, final int id1) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view, Gravity.RIGHT);
        popupMenu.inflate(R.menu.popupmenu_time);
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {



                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        final SQLiteDatabase database = dbHelper.getWritableDatabase();
                        switch (item.getItemId()) {

                            case R.id.menu2:


                                database.execSQL("DELETE  FROM "+DBHelper.TABLE_TIME+" WHERE "+DBHelper.KEY_TIME+" = '"+les[id1]+"'");
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
    public void restart(){
        dbHelper.close();
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();


    }
}
