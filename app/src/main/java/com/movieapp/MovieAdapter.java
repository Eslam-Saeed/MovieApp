package com.movieapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Movie;

/**
 * Created by esveer on 12/31/15.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Activity context , List<Movie> movies)
    {
        super(context , 0 , movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_image, parent, false);
        Movie movie = getItem(position);
       /* ImageView img ;
        if(convertView == null)
        {
            img = new ImageView(getContext());
            img.setLayoutParams(new GridView.LayoutParams(200,200));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setPadding(8,8,8,8);
        }else {
        */

            ImageView imagePoster = (ImageView) rootView.findViewById(R.id.image_in_grid_view);
            //iagePoster.setImageResource(movie.getMovie_id());
            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/" + movie.getImage_path()).into(imagePoster);

        return rootView;
    }


}
