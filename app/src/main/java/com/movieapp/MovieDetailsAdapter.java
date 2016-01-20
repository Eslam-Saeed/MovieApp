package com.movieapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import Controller.MovieDatabaseAdapter;
import Model.Movie;
import Model.Reviews;
import Model.Trailers;

/**
 * Created by esveer on 1/4/16.
 */
public class MovieDetailsAdapter extends BaseAdapter {
    Activity activity;
    Movie movieDetails;
    LayoutInflater inflater;
    View rootView = null;
    MovieDatabaseAdapter movieDatabaseAdabter;
    Button favButton;


    public MovieDetailsAdapter(Activity activity, Movie detailsForMovie) {
        this.activity = activity;
        this.movieDetails = detailsForMovie;
        inflater=activity.getLayoutInflater();
//        rootView = inflater.inflate(0,null);
        // View rootView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_image, parent, false);
    }

    @Override
    public int getCount() {
        return 1+movieDetails.getTrailers().size()+movieDetails.getReviews().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        if(getType(position)=="details"){
            rootView = inflater.inflate(R.layout.list_detail_movie, parent, false);

            ImageView movieImage = (ImageView) rootView.findViewById(R.id.imageMovie_detail);
            TextView movieTitle = (TextView) rootView.findViewById(R.id.textView_moviename);
            TextView movieRelease = (TextView) rootView.findViewById(R.id.textView_yearRelease);
            //TextView movieTime = (TextView) convertView.findViewById(R.id.textView_movieTime);
            TextView movieRate = (TextView) rootView.findViewById(R.id.textView_movieRate);
            TextView movieOverview = (TextView) rootView.findViewById(R.id.textView_movieOverview);


            Picasso.with(activity).load("http://image.tmdb.org/t/p/w185/" + movieDetails.getImage_details_path()).into(movieImage);
            movieTitle.setText(movieDetails.getOriginal_title());
            movieRelease.setText(movieDetails.getRelease_date().substring(0,4));
            //movieTime.setText(movieDetail.get);
            movieRate.setText(movieDetails.getUser_rating() + "/10");
            movieOverview.setText(movieDetails.getOverview());
            favButton = (Button) rootView.findViewById(R.id.button_markAsFavourite);
            movieDatabaseAdabter = new MovieDatabaseAdapter(parent.getContext());
            int checkForFavourite = movieDatabaseAdabter.checkForFavourite(movieDetails);
            if(checkForFavourite == 1)
            {
                favButton.setBackgroundColor(Color.GREEN);
                favButton.setText("Favourite");
            }
        }
        else if(getType(position)=="trailer") {
            if(movieDetails.getTrailers().size() == 0)
            {
                Toast.makeText(activity, "No Trailers for "+movieDetails.getOriginal_title(), Toast.LENGTH_SHORT).show();
            }
            //inflate row_trailer
            Trailers trailer=movieDetails.getTrailers().get(position-1);
            rootView = inflater.inflate(R.layout.list_trailer_movie, parent, false);
            ImageView trailerImage = (ImageView) rootView.findViewById(R.id.image_trailer);
            TextView trailerName = (TextView) rootView.findViewById(R.id.textView_trailerName);
            Picasso.with(activity).load(trailer.getTrailerImagePath()).into(trailerImage);
            trailerName.setText(trailer.getTrailerName());
        }
        else{
            Reviews review=movieDetails.getReviews().get(position - 1 - movieDetails.getTrailers().size());
            rootView = inflater.inflate(R.layout.list_review_movie, parent, false);
            TextView reviewerName = (TextView) rootView.findViewById(R.id.textView_reviewer_name);
            TextView reviewerContent = (TextView) rootView.findViewById(R.id.textView_reviewer_review);
            reviewerName.setText(review.getReviewerName());
            reviewerName.setShadowLayer(10, -10, 10, Color.DKGRAY);
            reviewerContent.setText(review.getReviewerReview());
        }
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!favButton.getText().equals("Favourite")) {
                    long result = movieDatabaseAdabter.addFavouriteMovie(movieDetails);
                    if (result > 0) {
                        favButton.setBackgroundColor(Color.GREEN);
                        favButton.setText("Favourite");
                        Toast.makeText(parent.getContext(), movieDetails.getOriginal_title() + " Added Successfully to Favourite", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(parent.getContext(), movieDetails.getOriginal_title() + " Cannot be added !", Toast.LENGTH_SHORT).show();
                } else {
                    int result = movieDatabaseAdabter.deleteFromFavourite(movieDetails);
                    if (result > 0) {
                        favButton.setBackgroundColor(Color.parseColor("#0099cc"));
                        favButton.setText("Mark as favourite");
                        Toast.makeText(parent.getContext(), movieDetails.getOriginal_title() + " Deleted Successfully from Favourite", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(parent.getContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    String getType(int position){
        if(position==0)
            return "details";
        else if(position<=movieDetails.getTrailers().size())
            return "trailer";
        else
            return "review";
    }

}
