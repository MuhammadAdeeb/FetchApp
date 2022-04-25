package com.example.fetchapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<FetchItem> itemsList= new ArrayList<>();

    private RecyclerView recyclerView;
    private itemAdapter item_adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To make sure API call doesn't crash, if internet's not available
        if (doNetCheck() == false) {
            Toast.makeText(this, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show();
        } else {
            recyclerView = findViewById(R.id.recycler);
            download(); // Internet's available; make API call to download data
        }
    }

    //Function to check Internet Connection/Network
    private boolean doNetCheck(){

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
            Toast.makeText(this, "Cannot access ConnectivityManager", Toast.LENGTH_SHORT).show();
            return false;  // No connection
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null)
            return false; // No connection

        // Check for active live connection
        boolean isConnected = activeNetwork.isConnectedOrConnecting();

        // Check if the connection is metered (possibly paid data), if asked for in requirements:
        // boolean isMetered = cm.isActiveNetworkMetered();
        return isConnected;
    }

    // Starts a new thread to download FetchItems JSON data
    public void download(){
        Log.d(TAG, "download: b4 starting new thread");

        // This API call is small and simple, so Multithreading is not necessary; however, I did it
        // to thinking of if data represented something bigger and we still wanted user to interact
        // with the screen
        MyRunnable myRunnable = new MyRunnable(this);
        new Thread(myRunnable).start();

        Log.d(TAG, "download: Started the thread");
    }

    // Receives data from the second thread to populate screen
    public void receiveData(ArrayList<FetchItem> al) {
        item_adap = new itemAdapter(al, this);
        recyclerView.setAdapter(item_adap);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}