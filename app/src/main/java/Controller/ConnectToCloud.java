package Controller;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by esveer on 12/30/15.
 */
public class ConnectToCloud {

    private final String LOG_TAG = ConnectToCloud.class.getSimpleName();
    public String getJsonStringFromUrl(String sortByCome)
    {

        String sort_by_default  = sortByCome;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String comingJsonStr = null;

        try {
            final String BASE_URL   = "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_BY    = "sort_by";
            final String API_KEY    = "api_key";
            final String API_VALUE  = "104cd7ae1c869742a5543776bbb649ea";
/*            if(sortByCome.isEmpty())
            {
                sort_by_default = sortByCome;
            }*/
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_BY , sort_by_default)
                    .appendQueryParameter(API_KEY , API_VALUE )
                    .build();

            URL url = new URL(builtUri.toString());
            Log.e(LOG_TAG, "My URL is :"+url.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            Log.e(LOG_TAG, inputStream.toString());
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                Log.d(LOG_TAG, "inputStream = Null ");
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                Log.d(LOG_TAG, "No String in Buffer !");
                return null;
            }
            comingJsonStr = buffer.toString();
            Log.e(LOG_TAG, comingJsonStr);
            if (comingJsonStr.length() < 1)
            {
                Log.e(LOG_TAG," StringJson less than 1 character");
                return null;
            }
            Log.d(LOG_TAG ,"Forecast JSON String :"+ comingJsonStr);
            return comingJsonStr;
        }catch (Exception e)
        {
            Log.e(LOG_TAG , "Connection"+e.getMessage() );
            return null;
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                    return null;
                }
            }

        }
        //http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=[YOUR APIKEY]
       // return comingJsonStr;
    }

    public String getJsonForMovieInformation(String selectTV , int movieId) {
        //http://api.themoviedb.org/3/movie/140607/videos?api_key=104cd7ae1c869742a5543776bbb649ea
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String comingJsonStr = null;

        try {
            final String BASE_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/" +selectTV+ "?";

            final String API_KEY = "api_key";
            final String API_VALUE = "********************************";


            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY, API_VALUE)
                    .build();

            URL url = new URL(builtUri.toString());
            Log.e(LOG_TAG, "My URL is :" + url.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            Log.e(LOG_TAG, inputStream.toString());
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                Log.d(LOG_TAG, "inputStream = Null ");
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                Log.d(LOG_TAG, "No String in Buffer !");
                return null;
            }
            comingJsonStr = buffer.toString();
            Log.e(LOG_TAG, comingJsonStr);
            if (comingJsonStr.length() < 1) {
                Log.e(LOG_TAG, " StringJson less than 1 character");
                return null;
            }
            Log.d(LOG_TAG, "Forecast JSON String :" + comingJsonStr);
            return comingJsonStr;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Connection" + e.getMessage());
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                    return null;
                }
            }

        }
    }
}
