package Controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Model.Movie;
import Model.Reviews;
import Model.Trailers;


public class JsonParse {
    public List<Movie> getMoviesFromJsonString (String jsonMoviesString)
    {
        List<Movie> listOfMovies = new ArrayList<Movie>();
        JSONObject tempMovieJson ;


        try {
            JSONObject jsonOpject = new JSONObject(jsonMoviesString);
            //Log.e("Helooooooooo",jsonOpject.toString());
            JSONArray jsonArrayOfResults = jsonOpject.getJSONArray("results");
            for (int i = 0; i < jsonArrayOfResults.length(); i++) {
                Movie tempMovieOpject = new Movie();
                tempMovieJson = jsonArrayOfResults.getJSONObject(i);
                tempMovieOpject.setMovie_id(tempMovieJson.getInt("id"));
                tempMovieOpject.setOriginal_title(tempMovieJson.getString("original_title"));
                tempMovieOpject.setOverview(tempMovieJson.getString("overview"));
                tempMovieOpject.setImage_path(tempMovieJson.getString("poster_path"));
                tempMovieOpject.setImage_details_path(tempMovieJson.getString("backdrop_path"));
                tempMovieOpject.setRelease_date(tempMovieJson.getString("release_date"));
                tempMovieOpject.setUser_rating(tempMovieJson.getString("vote_average"));

                listOfMovies.add(tempMovieOpject);
            }

            return listOfMovies;

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public List<Trailers> getTrailersFromJson(String jsonString)
    {
        List<Trailers> listTrailers = new ArrayList<Trailers>();
        JSONObject tempTrailerJson ;
        try {
            JSONObject jsonOpject = new JSONObject(jsonString);
            // Log.e("Helooooooooo", jsonOpject.toString());
            JSONArray jsonArrayOfResults = jsonOpject.getJSONArray("results");
            for (int i = 0; i < jsonArrayOfResults.length(); i++) {
                Trailers tempTrailers = new Trailers();
                tempTrailerJson = jsonArrayOfResults.getJSONObject(i);
                tempTrailers.setTrailerName(tempTrailerJson.getString("name"));
                tempTrailers.setTrailerURL(tempTrailerJson.getString("key"));
                tempTrailers.setTrailerImagePath(tempTrailerJson.getString("key"));

                listTrailers.add(tempTrailers);

            }
            return listTrailers;
        }catch (final JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<Reviews> getReviewsFromJson(String jsonString)
    {
        List<Reviews> listReviews = new ArrayList<Reviews>();
        JSONObject tempReviewJson ;
        try {
            JSONObject jsonOpject = new JSONObject(jsonString);
           // Log.e("Helooooooooo", jsonOpject.toString());
            JSONArray jsonArrayOfResults = jsonOpject.getJSONArray("results");
            for (int i = 0; i < jsonArrayOfResults.length(); i++) {
                Reviews tempReview = new Reviews();
                tempReviewJson = jsonArrayOfResults.getJSONObject(i);
                tempReview.setReviewerName(tempReviewJson.getString("author"));
                tempReview.setReviewerReview(tempReviewJson.getString("content"));

                listReviews.add(tempReview);

            }
            return listReviews;

        }catch (final JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    

}
