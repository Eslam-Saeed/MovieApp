package com.movieapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Controller.ConnectToCloud;
import Controller.JsonParse;
import Controller.MovieDatabaseAdapter;
import Model.Movie;


public class MainFragmentActivity extends Fragment {

    private MovieAdapter movieAdapter;
    List<Movie> listOfMovies = new ArrayList<Movie>();
    GridView gridView;
    String sortBy = "";
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    MovieDatabaseAdapter movieDatabaseAdabter;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("savedMovies", (ArrayList<? extends Parcelable>) listOfMovies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void updateMovieGrid(String sortBy) {
        new GetMoviesData().execute(sortBy);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        sortBy = getArguments().getString("sortBy");
        movieDatabaseAdabter = new MovieDatabaseAdapter(getContext());
        gridView = (GridView) rootView.findViewById(R.id.gridview_movie);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);
                if (getActivity().findViewById(R.id.movie_detailsFragment) != null) {
                    MovieDetailsFragment movieFragment = new MovieDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("movieDetails", movie);
                    movieFragment.setArguments(bundle);

                    getFragmentManager().beginTransaction()
                            .replace(R.id.movie_detailsFragment, movieFragment, DETAILFRAGMENT_TAG)
                            .commit();
                } else {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class).putExtra("movieDetail", movie);
                    startActivity(intent);
                }
            }
        });

        if (savedInstanceState == null || !savedInstanceState.containsKey("savedMovies")) {
            updateMovieGrid(sortBy);
        } else {
            //Toast.makeText(getContext(), "Found Saved Movies !", Toast.LENGTH_SHORT).show();
            listOfMovies = savedInstanceState.getParcelableArrayList("savedMovies");
            movieAdapter = new MovieAdapter(getActivity(), listOfMovies);
            gridView.setAdapter(movieAdapter);
        }
        return rootView;
    }

    public class GetMoviesData extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(String... params) {
            ConnectToCloud connect = new ConnectToCloud();
            JsonParse jsonData = new JsonParse();
            String jsonBack = connect.getJsonStringFromUrl(params[0]);
            if (jsonBack != null) {
                listOfMovies = jsonData.getMoviesFromJsonString(jsonBack);
            }
            return listOfMovies;
        }

        @Override
        protected void onPostExecute(List<Movie> listComeMovies) {
            if (!listComeMovies.isEmpty()) {
                if (movieAdapter != null) {
                    movieAdapter.clear();
                    movieAdapter.notifyDataSetChanged();
                }
                movieAdapter = new MovieAdapter(getActivity(), listComeMovies);
                gridView.setAdapter(movieAdapter);
            } else {
                Toast.makeText(getContext(), "Please Connect to the Internet, to View Latest Movies !", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sortBy.equals("favourite")) {
            movieAdapter.clear();
            listOfMovies = movieDatabaseAdabter.getFavouriteMovies();
            movieAdapter = new MovieAdapter(getActivity(), listOfMovies);
            gridView.setAdapter(movieAdapter);
        }
    }

    public void changeSortion(String sortByCome) {
        if (movieAdapter != null) {
            movieAdapter.clear();
            movieAdapter.notifyDataSetChanged();
            sortBy = sortByCome;
            if (sortBy.equals("favourite")) {
                listOfMovies = movieDatabaseAdabter.getFavouriteMovies();
                movieAdapter = new MovieAdapter(getActivity(), listOfMovies);
                gridView.setAdapter(movieAdapter);
            } else {
                updateMovieGrid(sortBy);
            }
        } else {
            Toast.makeText(this.getActivity(), "Please Connect to the Internet !", Toast.LENGTH_SHORT).show();
        }
    }
}
