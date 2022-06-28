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
import com.example.orate.MethodHelperClasses.MainActivityHelper;
import com.example.orate.DataModel.UserModel;
import com.example.orate.R;
import com.example.orate.Repository.RoomDatabase.CallHistoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private int ACTIVITY_CODE_CONTACT = 1;
    private int ACTIVITY_CODE_HISTORY = 2;
    private int ACTIVITY_CODE;
    private Context context;
    private MainActivityHelper mainActivityHelper;

    private List<UserModel> contactList;
    private FirebaseDatabase firebaseDatabase;
    private List<CallHistoryModel> callHistoryModelsList;


    public void setContactList(List<UserModel> contactList) {
        this.contactList = contactList;
    }

    public void setCallHistoryModelsList(List<CallHistoryModel> callHistoryModelsList) {
        this.callHistoryModelsList = callHistoryModelsList;
    }


    public Adapter(Context context, int ACTIVITY_CODE) {
        this.context = context;
        this.ACTIVITY_CODE = ACTIVITY_CODE;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (ACTIVITY_CODE == ACTIVITY_CODE_CONTACT) {
//contact code
            mainActivityHelper = MainActivityHelper.getHelperMethods();

            UserModel model = contactList.get(position);
            holder.callType.setVisibility(View.GONE);
            holder.about.setText(model.getAbout());
            holder.contactName.setText(model.getUserName());

            Glide.with(context)
                    .load(model.getImage())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.contactImage);

            holder.videoCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivityHelper.sendCallRequest(model.getPhoneNumber(), "video");
                }
            });

            holder.phoneCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivityHelper.sendCallRequest(model.getPhoneNumber(), "audio");
                }
            });


        } else if (ACTIVITY_CODE == ACTIVITY_CODE_HISTORY) {

//            call history code
            CallHistoryModel model = callHistoryModelsList.get(position);
            holder.phoneCall.setVisibility(View.GONE);

            firebaseDatabase.getReference().child("User").child(model.getFriendPhoneNumber()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//            contact profile image setting
                    Glide.with(context)
                            .load(snapshot.child("image").getValue().toString())
                            .apply(RequestOptions.centerCropTransform())
                            .into(holder.contactImage);
                    holder.contactName.setText(snapshot.child("userName").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            holder.about.setText("date");


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

    private void makeCall(String friendPhoneNumber) {

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
            about = itemView.findViewById(R.id.connectivityStatus);
        }
    }
}
