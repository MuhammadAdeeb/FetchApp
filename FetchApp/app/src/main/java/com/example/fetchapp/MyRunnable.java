package com.example.fetchapp;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

// import com.android.volley.toolbox.Volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import javax.net.ssl.HttpsURLConnection;

public class MyRunnable implements Runnable{

    private static final String TAG = "MyRunnable";
    private final MainActivity mainActivity;

    private static final String fetchURL = "https://fetch-hiring.s3.amazonaws.com/hiring.json"; //Set the

    MyRunnable(MainActivity mainActivity) {
        Log.d(TAG, "MyRunnable: IN ");
        this.mainActivity = mainActivity;
    }

    // Since this API call is light, Using Volley for fast and efficient download of JSON data,
    // & passing to parseJSON
    public void run() {
        Log.d(TAG, "run: Starting Run");

        RequestQueue queue = Volley.newRequestQueue(mainActivity);
        String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d(TAG, "onResponse: Response is:"  + response.substring(0,500));
                        parseJsonData(response); // func parse JSON
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Create a toast if error occurs in download
                Toast.makeText(mainActivity, "Download Error Occurred", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        Log.d(TAG, "run: HERE? ");

    }

    void parseJsonData(String jsonString) {
        try {
            JSONArray retrievedArray = new JSONArray(jsonString); // Create JSONArray of retrieved string
            ArrayList<FetchItem> al = new ArrayList<FetchItem>(); // Create FetchItem array that will be returned to MainUI
            HashMap <Integer, ArrayList<Integer>> map= new HashMap<>(); // To separately store ListIDs
            // with their appropriate Names/Items: { 3: [ 23,2, 47 ...], 1: [586, 43, 89 ...] .. }

            // Iterates through the list of all JSON objects and populates the map
            for (int i = 0; i < retrievedArray.length(); i++){
                JSONObject temp = retrievedArray.getJSONObject(i); // {"id": 444, "listId": 1, "name": ""}

                String test_name = temp.getString("name"); // retrieve 'name' element of JSON object

                if (!test_name.equals("") && !test_name.equals("null")){ // If name isn't "" nor null
                    int temp_id = temp.getInt("listId");

                    // if key already exists in HashMap, add new value to its array
                    if (map.containsKey(temp_id)){
                        ArrayList<Integer> temp_val_list = map.get(temp_id);
                        temp_val_list.add(temp.getInt("id"));
                        map.put(temp_id, temp_val_list);
                        Log.d(TAG, "parseJsonData: temp_id: " + temp_id + ", AL: " + temp_val_list);
                    } else{// if key doesn't exist in HashMap, add new K, V pair where V is an arraylist of current value
                        ArrayList<Integer> temp_al = new ArrayList<Integer>();
                        temp_al.add(temp.getInt("id"));
                        map.put(temp_id,temp_al);
                    }
                }
            }
            Log.d(TAG, "parseJsonData: B4 SORT MAP" + map);
            // Sort the Names numerically for each ListId as asked for in requirements
            map.forEach(
                    (key, value)
                            -> Collections.sort(value));
            Log.d(TAG, "parseJsonData: After Sort MAP" + map);

            // Create FetchItem for all k,v pairs and add to return list, al
            Log.d(TAG, "parseJsonData: B4 Creating FetchItems and adding to al");
            map.forEach(
                    (key, value)->al.add(new FetchItem(key, value))
            );

            Log.d(TAG, "parseJsonData: After Creating FetchItems and adding to al");

            // Sort the return list numerically by ListId as asked for in requirements
            //      B/c listId 1 names have to printed before listId 2 and so on
            Collections.sort(al, Comparator.comparing(FetchItem::getListId));
            Log.d(TAG, "parseJsonData: Sorting after sorting al");

            mainActivity.runOnUiThread(() -> mainActivity.receiveData(al)); // Return to mainThread

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
