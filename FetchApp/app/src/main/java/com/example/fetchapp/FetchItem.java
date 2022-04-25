package com.example.fetchapp;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

public class FetchItem {
    private static final String TAG = "FetchItem";
    private int listId; // listiD
    private ArrayList<Integer> arr_id;// Array stores all int ids of a specific listId
    private String fin_str; // Stores a string of names formatted for textview on Front-End
    private ArrayList<String> str_names;  // Array stores all string names of a specific listId

    FetchItem(int listId, ArrayList<Integer> arr_id){
        this.listId = listId;
        this.arr_id = arr_id;

        this.fin_str = "";
        this.str_names = new ArrayList<>();

        for (int i = 0; i < arr_id.size(); i++){
            this.fin_str = this.fin_str + "Item " + Integer.toString(arr_id.get(i)) + "\n";
            this.str_names.add("Item " + Integer.toString(arr_id.get(i)));
        }


    }

    public int getListId(){return listId;}
    public ArrayList<Integer> getArr_id() {return arr_id;}
    public String getFin_str(){return fin_str;}

    public ArrayList<String> getStr_names() {return str_names;}
}
