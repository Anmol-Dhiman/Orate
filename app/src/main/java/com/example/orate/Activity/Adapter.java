package com.example.orate.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.orate.Activity.Fragments.CallHistory;
import com.example.orate.Activity.Fragments.UserProfile;
import com.example.orate.DataModel.UserModel;
import com.example.orate.R;
import com.example.orate.Repository.RoomDatabase.CallHistoryModel;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private int ACTIVITY_CODE_CONTACT = 1;
    private int ACTIVITY_CODE_HISTORY = 2;
    private int activity_code;
    private Context context;

    private List<UserModel> contactList;

    private List<CallHistoryModel> callHistoryModelsList;

    public void setContactList(List<UserModel> contactList) {
        this.contactList = contactList;
    }

    public void setCallHistoryModelsList(List<CallHistoryModel> callHistoryModelsList) {
        this.callHistoryModelsList = callHistoryModelsList;
    }


    public Adapter(Context context, int activity_code) {
        this.context = context;
        this.activity_code = activity_code;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (activity_code == ACTIVITY_CODE_CONTACT) {
//contact code

            UserModel model = contactList.get(position);
            holder.callType.setVisibility(View.GONE);
            holder.about.setText(model.getAbout());
            holder.contactName.setText(model.getUserName());

            Glide.with(context)
                    .load(model.getImage())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.contactImage);


        } else {

//            call history code
            CallHistoryModel model = callHistoryModelsList.get(position);
            holder.phoneCall.setVisibility(View.GONE);
            holder.contactName.setText(model.getContactName());
            holder.about.setText(model.getAbout());


//            contact profile image setting
            Glide.with(context)
                    .load(model.getProfilePictureSrc())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.contactImage);


            if (model.getMediaType() == "audio")
                holder.videoCall.setBackgroundResource(R.drawable.ic_phone_call);


            if (model.getCallType() == "outgoing")
                holder.callType.setBackgroundResource(R.drawable.ic_outgoing_call);
            else if (model.getCallType() == "outgoingMissedCall")
                holder.callType.setBackgroundResource(R.drawable.ic_outgoing_missed_call);
            else if (model.getCallType() == "incomingMissedCall")
                holder.callType.setBackgroundResource(R.drawable.ic_icoming_missed_call);

        }


    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView contactImage, phoneCall, videoCall, callType;
        private TextView contactName, about;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactImage = itemView.findViewById(R.id.contactImage);
            phoneCall = itemView.findViewById(R.id.phoneCall);
            videoCall = itemView.findViewById(R.id.videoCall);
            callType = itemView.findViewById(R.id.callType);
            contactName = itemView.findViewById(R.id.contactName);
            about = itemView.findViewById(R.id.about);
        }
    }
}
