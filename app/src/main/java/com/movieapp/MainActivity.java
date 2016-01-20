package com.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    MainFragmentActivity fragment = new MainFragmentActivity();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = new Bundle();
        bundle.putString("sortBy", getString(R.string.pref_sort_by_default));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_movieList, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment oldFragment = getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
        if (oldFragment != null) {
            //kill Details fragment every time user change sortion
            getSupportFragmentManager().beginTransaction().remove(oldFragment).commit();
        }

        if (id == R.id.action_mostPopular) {
            String sortBy = getString(R.string.pref_sort_by_popularity);
            if (fragment != null) {
                fragment.changeSortion(sortBy);
            }
            return true;
        }
        if (id == R.id.action_heighRate) {
            String sortBy = getString(R.string.pref_sort_by_heighset_rated);
            if (fragment != null) {
                fragment.changeSortion(sortBy);
            }
            return true;
        }
        if (id == R.id.action_favourite) {
            String sortBy = "favourite";
            if (fragment != null) {
                fragment.changeSortion(sortBy);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
