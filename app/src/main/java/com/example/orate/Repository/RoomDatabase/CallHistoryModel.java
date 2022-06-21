package com.example.orate.Repository.RoomDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.orate.DataModel.DateConverter;

import java.sql.Date;


@Entity(tableName = "callHistory")
public class CallHistoryModel {


    @PrimaryKey(autoGenerate = true)
    private int id;


    private String mediaType;
    private String friendPhoneNumber;
    private String callType;


    @TypeConverters({DateConverter.class})
    private Date date;

    public CallHistoryModel(String mediaType, String friendPhoneNumber, String callType, Date date) {

        this.mediaType = mediaType;
        this.friendPhoneNumber = friendPhoneNumber;
        this.callType = callType;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getFriendPhoneNumber() {
        return friendPhoneNumber;
    }

    public void setFriendPhoneNumber(String friendPhoneNumber) {
        this.friendPhoneNumber = friendPhoneNumber;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
