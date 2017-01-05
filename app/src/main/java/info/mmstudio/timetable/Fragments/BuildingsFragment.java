package info.mmstudio.timetable.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.mmstudio.timetable.Fragments.BuildingsFragment;

import info.mmstudio.timetable.R;

public class BuildingsFragment extends Fragment {

    final String AT_NAME_TEXT = "text";

    Integer[] mSign = {R.string.pc1, R.string.pc2, R.string.pc3, R.string.pc4, R.string.pc5, R.string.pc6, R.string.pc7, R.string.pc7, R.string.pc7, R.string.pc7, R.string.pc7, R.string.pc7};
    ListView lv;
    TextView sign;
    Intent intent;
    SimpleAdapter sAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.testlists, container, false);

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(mSign.length);
        Map<String, Object> m;

        sign = (TextView) v.findViewById(R.id.Sign);

        for (int i = 0; i < mSign.length; i++) {
            m = new HashMap<String, Object>();
            m.put(AT_NAME_TEXT, getString(mSign[i]));
            data.add(m);
        }


        String[] from = {AT_NAME_TEXT};
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = {R.id.Sign};

        sAdapter = new SimpleAdapter(getActivity(), data, R.layout.pc_list_activity_mod, from, to);


        // определяем список и присваиваем ему адаптер
        lv = (ListView) v.findViewById(R.id.listTEST);
        lv.setAdapter(sAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                switch (position) {
                    case 0:
                        intent = new Intent(getActivity(), BuildingsFragment.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), BuildingsFragment.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), BuildingsFragment.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), BuildingsFragment.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getActivity(), BuildingsFragment.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(getActivity(), BuildingsFragment.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(getActivity(), BuildingsFragment.class);
                        startActivity(intent);
                        break;

                }

            }

        });
return v;
    }
}

