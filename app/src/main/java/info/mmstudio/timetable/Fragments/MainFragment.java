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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.mmstudio.timetable.R;

public class MainFragment extends Fragment {

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main,container,false);
        final FragmentManager fm = getFragmentManager();
        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.edit));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fm.beginTransaction().replace(R.id.content_frame, new EditFragment()).commit();
            }
        });

        return v;
    }
}
