package info.mmstudio.timetable.Fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import java.util.Calendar;

import info.mmstudio.timetable.R;

public class TimePickerFragment extends Fragment {

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_time_picker,container,false);
        final FragmentManager fm = getFragmentManager();

        TimePicker t1 = (TimePicker) v.findViewById(R.id.timePicker);
        TimePicker t2 = (TimePicker) v.findViewById(R.id.timePicker2);

        t1.setIs24HourView(true);
        t2.setIs24HourView(true);


        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.accept));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fm.beginTransaction().replace(R.id.content_frame, new TimeFragment()).commit();
                getActivity().setTitle("Время Уроков");

            }
        });

        return v;
    }
}
