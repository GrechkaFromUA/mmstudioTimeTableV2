package info.mmstudio.timetable;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import info.mmstudio.timetable.Fragments.BuildingsFragment;
import info.mmstudio.timetable.Fragments.LessonsFragment;
import info.mmstudio.timetable.Fragments.MainFragment;
import info.mmstudio.timetable.Fragments.SettingsFragment;
import info.mmstudio.timetable.Fragments.SubjectsFragment;
import info.mmstudio.timetable.Fragments.TeachersFragment;
import info.mmstudio.timetable.Fragments.TimeFragment;
import info.mmstudio.timetable.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        setTitle("Расписание");
        fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

 

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       FragmentManager fm = getFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
            setTitle("Расписание");
        } else if (id == R.id.nav_settings) {
            fm.beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
            setTitle("Настройки");
        } else if (id == R.id.nav_time) {
            fm.beginTransaction().replace(R.id.content_frame, new TimeFragment()).commit();
            setTitle("Время уроков");
        } else if (id == R.id.nav_subjects) {
            fm.beginTransaction().replace(R.id.content_frame, new SubjectsFragment()).commit();
            setTitle("Предметы");
        } else if (id == R.id.nav_teachers) {
            fm.beginTransaction().replace(R.id.content_frame, new TeachersFragment()).commit();
            setTitle("Учителя");
        } else if (id == R.id.nav_buildings) {
            fm.beginTransaction().replace(R.id.content_frame, new BuildingsFragment()).commit();
            setTitle("Корпуса");
        } else if (id == R.id.nav_lessons_types) {
            fm.beginTransaction().replace(R.id.content_frame, new LessonsFragment()).commit();
            setTitle("Типы уроков");
        } else if (id == R.id.nav_two_weeks) {

        } else if (id == R.id.nav_first_day) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
