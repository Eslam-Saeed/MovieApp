package com.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import Model.Movie;

public class DetailsActivity extends AppCompatActivity {

    Movie movieDetails = new Movie();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        MovieDetailsFragment movieFragment = new MovieDetailsFragment();
        movieDetails = getIntent().getParcelableExtra("movieDetail");

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("movieDetails", movieDetails);
            movieFragment.setArguments(bundle);


                getSupportFragmentManager().beginTransaction()
                        .add(R.id.movie_detailsFragment, movieFragment)
                        .commit();


        }

    }
}
