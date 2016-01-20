package Controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.Movie;

public class MovieDatabaseAdapter {

    List<Movie> listOfMovies = new ArrayList<Movie>() ;
    MovieDatabaseHelper movieHelper;
    public MovieDatabaseAdapter(Context context)
    {
        movieHelper = new MovieDatabaseHelper(context);
    }
//COL_MOVIE_ID - COL_MOVIE_ORIGINAL_TITLE - COL_MOVIE_OVERVIEW - COL_MOVIE_POSTER_PATH - COL_MOVIE_BACKDROP_PATH - COL_MOVIE_RELEASE_DATE - COL_MOVIE_VOTE_AVERAGE

    public long addFavouriteMovie (Movie movie)
    {
        SQLiteDatabase db = movieHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieDatabaseHelper.COL_MOVIE_ID , movie.getMovie_id());
        contentValues.put(MovieDatabaseHelper.COL_MOVIE_ORIGINAL_TITLE , movie.getOriginal_title());
        contentValues.put(MovieDatabaseHelper.COL_MOVIE_OVERVIEW , movie.getOverview());
        contentValues.put(MovieDatabaseHelper.COL_MOVIE_POSTER_PATH , movie.getImage_path());
        contentValues.put(MovieDatabaseHelper.COL_MOVIE_BACKDROP_PATH , movie.getImage_details_path());
        contentValues.put(MovieDatabaseHelper.COL_MOVIE_RELEASE_DATE , movie.getRelease_date());
        contentValues.put(MovieDatabaseHelper.COL_MOVIE_VOTE_AVERAGE, movie.getUser_rating());

        long result = db.insert(MovieDatabaseHelper.TABLE_NAME , null , contentValues);
        return result;
    }

    public int deleteFromFavourite (Movie movie)
    {
        SQLiteDatabase db = movieHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieDatabaseHelper.COL_MOVIE_ID , movie.getMovie_id());
        int result = db.delete(MovieDatabaseHelper.TABLE_NAME, contentValues.toString(), null);
        return result;
    }
    public int checkForFavourite(Movie movie)
    {
        SQLiteDatabase db = movieHelper.getReadableDatabase();
        String select = "Select * FROM "+MovieDatabaseHelper.TABLE_NAME+" WHERE "+MovieDatabaseHelper.COL_MOVIE_ID+" = "+movie.getMovie_id()+"";
        Cursor c = db.rawQuery(select, null);
        if(c.moveToNext())
            return 1;
        else
            return -1;

    }

    public List<Movie> getFavouriteMovies()
    {
        SQLiteDatabase db = movieHelper.getReadableDatabase();
        String select = "Select * FROM "+MovieDatabaseHelper.TABLE_NAME;
        Cursor c = db.rawQuery(select, null);
        while(c.moveToNext())
        {
            Movie tempMovie = new Movie();
            tempMovie.setMovie_id(c.getInt(0));
            tempMovie.setOriginal_title(c.getString(1));
            tempMovie.setOverview(c.getString(2));
            tempMovie.setImage_path(c.getString(3));
            tempMovie.setImage_details_path(c.getString(4));
            tempMovie.setRelease_date(c.getString(5));
            tempMovie.setUser_rating(c.getString(6));

            listOfMovies.add(tempMovie);
        }
        c.close();
        db.close();
        return listOfMovies;
    }

    class MovieDatabaseHelper  extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "movieFavouriteDatabase";

        private static final String TABLE_NAME = "favourite_movies_table";
        private static final String COL_MOVIE_ID   = "movie_id";
        private static final String COL_MOVIE_ORIGINAL_TITLE = "movie_original_title";
        private static final String COL_MOVIE_OVERVIEW = "movie_overview";
        private static final String COL_MOVIE_POSTER_PATH = "movie_poster_path";
        private static final String COL_MOVIE_BACKDROP_PATH = "movie_backdrop_path";
        private static final String COL_MOVIE_RELEASE_DATE = "movie_release_date";
        private static final String COL_MOVIE_VOTE_AVERAGE = "movie_vote_average";
        private static final int DATABASE_VERSION = 5;


        private static final String CREATE_TABLE = "CREATE TABLE " +TABLE_NAME+"("+COL_MOVIE_ID+" INTEGER PRIMARY KEY, "+COL_MOVIE_ORIGINAL_TITLE+" VARCHAR(255) , "+COL_MOVIE_OVERVIEW+" VARCHAR(255) , " +
                " "+COL_MOVIE_POSTER_PATH+ " VARCHAR(255) , "+COL_MOVIE_BACKDROP_PATH+" VARCHAR(255) , "+COL_MOVIE_RELEASE_DATE+" VARCHAR(255) , "+COL_MOVIE_VOTE_AVERAGE+" VARCHAR(255) ); ";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public MovieDatabaseHelper(Context context)
        {
            super(context , DATABASE_NAME , null , DATABASE_VERSION);
            this.context = context;

        }
        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
