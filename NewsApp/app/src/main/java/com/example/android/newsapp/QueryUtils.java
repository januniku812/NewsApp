package com.example.android.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    public static  List<News> fetchEarthquakeData(String requestUrl) {

        URL url = createUrl(requestUrl);
        List<News> newsList = null;

        try {
            String jsonResponse = makeHttpRequest(url);
            newsList = extractFeaturesFromJson(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return newsList;
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<News> extractFeaturesFromJson(String jsonResponse) {

        if(jsonResponse == null) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        List<News> newsList = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            JSONObject JSONresponse = new JSONObject(jsonResponse);
            JSONObject response = JSONresponse.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            for(int i = 0; i < results.length(); i++) {
                News news;
                JSONObject JSONobj = results.getJSONObject(i);
//             String url = JSONobj.getJSONArray("tags").getJSONObject(0).getString("webUrl");
                String url = JSONobj.getString("webUrl");
//                rearranging the date as they write it backwards, which seems odd to the eye
                String publicationDateMonth = JSONobj.getString("webPublicationDate").split("T")[0].replace("-","/").substring(5,7);
                String publicationDateDay = JSONobj.getString("webPublicationDate").split("T")[0].replace("-","/").substring(8,10);
                String publicationDateYear = JSONobj.getString("webPublicationDate").split("T")[0].replace("-","/").substring(0,4);
                String publicationDate = publicationDateMonth + "/" + publicationDateDay + "/"+ publicationDateYear;
//              getting the time
                String publicationTime = JSONobj.getString("webPublicationDate").split("T")[1].replace("Z","");
//                getting the title
                String title = JSONobj.getString("webTitle");
//                getting the image from the link with the entire article
                JSONObject fields = JSONobj.getJSONObject("fields");
                String imageUrl = fields.getString("thumbnail");
//              gettings the tags object which under is the author info
                JSONArray tags = JSONobj.getJSONArray("tags");
//                retrieving webTitle under which is the author
                String author = null;
                for(int j=0; j < tags.length(); j++){
                    JSONObject currentTag = tags.getJSONObject(j);
                      author = currentTag.getString("webTitle");
                }
               news = new News(url, publicationDate, publicationTime, title, author, imageUrl);
                newsList.add(news);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        // Return the list of earthquakes
        return newsList;
    }

    private static URL createUrl(String stringUrl) {

        URL url = null;

        if(stringUrl == null) {
            return url;
        }
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        if(url == null) {
            return jsonResponse;
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream (InputStream inputStream) {
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();

        if(inputStream == null) {
            return null;
        }

        streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        reader = new BufferedReader(streamReader);
        try {
            String line = reader.readLine();
            while (line != null) {

                result.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

}