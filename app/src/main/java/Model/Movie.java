package Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by esveer on 12/30/15.
 */
public class Movie implements Parcelable {


    private int movie_id;
    private String original_title;
    private String image_path;
    private String image_details_path;
    private String overview;
    private String user_rating;//vote_average
    private String release_date;

    private ArrayList <Trailers> trailers;
    private ArrayList <Reviews> reviews;

    public Movie()
    {

    }

    public void setReviews(ArrayList<Reviews> reviews) {
        this.reviews = reviews;
    }

    public void setTrailers(ArrayList<Trailers> trailers) {
        this.trailers = trailers;
    }

    public ArrayList<Reviews> getReviews() {
        return reviews;
    }

    public ArrayList<Trailers> getTrailers() {
        return trailers;
    }

    private Movie(Parcel in)
    {
        setMovie_id(in.readInt());
        setOriginal_title(in.readString());
        setOverview(in.readString());
        setImage_path(in.readString());
        setImage_details_path(in.readString());
        setRelease_date(in.readString());
        setUser_rating(in.readString());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String toString()
    {
        return getMovie_id()+"--"+getOriginal_title()+"--"+getOverview()+"--"+getImage_path()+"--"+getImage_details_path() +"--"+getRelease_date()+"--"+getUser_rating();
    }

    public String getImage_details_path() {
        return image_details_path;
    }


    public void setImage_details_path(String image_details_path) {
        this.image_details_path = image_details_path;
    }
    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getUser_rating() {
        return user_rating;
    }


    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getMovie_id());
        parcel.writeString(getOriginal_title());
        parcel.writeString(getOverview());
        parcel.writeString(getImage_path());
        parcel.writeString(getImage_details_path());
        parcel.writeString(getRelease_date());
        parcel.writeString(getUser_rating());
    }


    @Override
    public int describeContents() {
        return 0;
    }


}
