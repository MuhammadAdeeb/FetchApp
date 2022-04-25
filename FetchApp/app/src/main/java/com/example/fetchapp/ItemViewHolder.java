package com.example.fetchapp;

import android.service.autofill.TextValueSanitizer;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    TextView listIdTv;
    TextView nameTv;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        listIdTv = itemView.findViewById(R.id.listIdVal_tv);
        nameTv = itemView.findViewById(R.id.nameVal_tv);
    }

}
