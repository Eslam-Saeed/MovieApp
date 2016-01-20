package com.movieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Controller.ConnectToCloud;
import Controller.JsonParse;
import Model.Movie;
import Model.Reviews;
import Model.Trailers;


public class MovieDetailsFragment extends Fragment {
    Movie movieDetails = new Movie();
    ListView listView;
    MovieDetailsAdapter movieDetailsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_view_Details);
        movieDetails = getArguments().getParcelable("movieDetails");
        if (savedInstanceState == null) {
            new GetTrailersAndReviews().execute();
        } else {
            Toast.makeText(getContext(), "Saved Details !", Toast.LENGTH_SHORT).show();
            movieDetails = savedInstanceState.getParcelable("savedMovieDetails");
            movieDetailsAdapter = new MovieDetailsAdapter(getActivity(), movieDetails);
            listView.setAdapter(movieDetailsAdapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (position >= 1 && position <= movieDetails.getTrailers().size()) {
                    String video_path = "http://www.youtube.com/watch?v=" + movieDetails.getTrailers().get(position - 1).getTrailerURL();
                    Uri uri = Uri.parse(video_path);

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });
       return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("savedMovieDetails", movieDetails);
        super.onSaveInstanceState(outState);
    }

    public class GetTrailersAndReviews extends AsyncTask<Void, Void, Movie> {

        ProgressDialog progress = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            progress.setMessage("Getting Movie Details...");
            progress.show();
            super.onPreExecute();
        }

        @Override
        protected Movie doInBackground(Void... params) {
            ConnectToCloud connect = new ConnectToCloud();
            JsonParse jsonData = new JsonParse();

            String jsontrailers = connect.getJsonForMovieInformation("videos", movieDetails.getMovie_id());
            String jsonReviews = connect.getJsonForMovieInformation("reviews", movieDetails.getMovie_id());
            if (jsonReviews != null && jsontrailers != null) {
                movieDetails.setReviews((ArrayList<Reviews>) jsonData.getReviewsFromJson(jsonReviews));
                movieDetails.setTrailers((ArrayList<Trailers>) jsonData.getTrailersFromJson(jsontrailers));
            }
            return movieDetails;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);
            progress.hide();
            if (movie.getTrailers() != null && movie.getReviews() != null) {
                movieDetailsAdapter = new MovieDetailsAdapter(getActivity(), movie);
                listView.setAdapter(movieDetailsAdapter);
            } else {
                Toast.makeText(getContext(), "Please Connect to the Internet !", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
