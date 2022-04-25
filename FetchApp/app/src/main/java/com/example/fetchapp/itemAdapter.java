package com.example.fetchapp;

import android.content.ClipData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private static final String TAG = "itemAdapter";
    private List<FetchItem> items_list;
    private final MainActivity mainAct;

    public itemAdapter(List<FetchItem> iList, MainActivity ma) {
        this.items_list = iList;
        mainAct = ma;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fetch_item, parent, false);
        //itemView.setOnClickListener(mainAct);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        FetchItem fi = items_list.get(position);
        holder.listIdTv.setText(Integer.toString(fi.getListId()));

        /*
        ArrayList<Integer> li_names = new ArrayList<Integer>();
        li_names = fi.getArr_id();
        ArrayList<String> str_names = new ArrayList<String>();

        //      Elements of arr_id of FetchItems used to create this string
        String fin_str = "";// Variable used to populate nameTv in Recycler View

        for (int i = 0; i < li_names.size(); i++){
            fin_str = fin_str + "Item " + Integer.toString(li_names.get(i)) + "\n";
            str_names.add("Item " + Integer.toString(li_names.get(i)));
        }

         */
        Log.d(TAG, "onBindViewHolder: getFin_str: " + fi.getFin_str());
        Log.d(TAG, "onBindViewHolder: listId: " + fi.getListId());
        holder.nameTv.setText(fi.getFin_str());
    }

    @Override
    public int getItemCount() {
        return items_list.size();
    }
}
