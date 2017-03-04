package info.mmstudio.timetable;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import info.mmstudio.timetable.Fragments.BuildingsFragment;
import info.mmstudio.timetable.Fragments.LessonsFragment;
import info.mmstudio.timetable.Fragments.MainFragment;
import info.mmstudio.timetable.Fragments.SettingsFragment;
import info.mmstudio.timetable.Fragments.SubjectsFragment;
import info.mmstudio.timetable.Fragments.TeachersFragment;
import info.mmstudio.timetable.Fragments.TimeFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        FragmentManager fm = getFragmentManager();
        setTitle(getString(R.string.timetable));
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
            setTitle(getString(R.string.timetable));
        } else if (id == R.id.nav_settings) {
            fm.beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
            setTitle(getString(R.string.title_settings));
        } else if (id == R.id.nav_time) {
            fm.beginTransaction().replace(R.id.content_frame, new TimeFragment()).commit();
            setTitle(getString(R.string.title_time));
        } else if (id == R.id.nav_subjects) {
            fm.beginTransaction().replace(R.id.content_frame, new SubjectsFragment()).commit();
            setTitle(getString(R.string.title_subjects));
        } else if (id == R.id.nav_teachers) {
            fm.beginTransaction().replace(R.id.content_frame, new TeachersFragment()).commit();
            setTitle(getString(R.string.title_teachers));
        } else if (id == R.id.nav_buildings) {
            fm.beginTransaction().replace(R.id.content_frame, new BuildingsFragment()).commit();
            setTitle(getString(R.string.title_buildings));
        } else if (id == R.id.nav_lessons_types) {
            fm.beginTransaction().replace(R.id.content_frame, new LessonsFragment()).commit();
            setTitle(getString(R.string.title_lessons_type));
        } else if (id == R.id.nav_two_weeks) {

        } else if (id == R.id.nav_first_day) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
