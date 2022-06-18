package com.example.orate.Activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orate.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private int ACTIVITY_CODE;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView contactImage, phoneCall, videoCall, callType;
        private TextView contactName, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactImage = itemView.findViewById(R.id.contactImage);
            phoneCall = itemView.findViewById(R.id.phoneCall);
            videoCall = itemView.findViewById(R.id.videoCall);
            callType = itemView.findViewById(R.id.callType);
            contactName = itemView.findViewById(R.id.contactName);
            status = itemView.findViewById(R.id.status);
        }
    }
}
