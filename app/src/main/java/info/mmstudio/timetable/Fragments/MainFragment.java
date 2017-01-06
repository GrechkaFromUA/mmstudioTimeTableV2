package info.mmstudio.timetable.Fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
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
    boolean image=false;

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main,container,false);

        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
       // fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.logoico));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(image==false) {
                    fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.logoico));
                    image=true;
                } else {
                    fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.mylogo));
                    image=false;
                }
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return v;
    }
}
