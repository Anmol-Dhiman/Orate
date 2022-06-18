package com.example.orate.Repository.RoomDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;


@Entity(tableName = "callHistory")
public class CallHistoryModel {


    @PrimaryKey(autoGenerate = true)
    private int id;


    private String profilePictureSrc;
    private String mediaType;
    private String contactName;
    private String callType;


    @TypeConverters({DateConverter.class})
    private Date date;

    public CallHistoryModel(String profilePictureSrc, String mediaType, String contactName, String callType, Date date) {
        this.profilePictureSrc = profilePictureSrc;
        this.mediaType = mediaType;
        this.contactName = contactName;
        this.callType = callType;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getProfilePictureSrc() {
        return profilePictureSrc;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getContactName() {
        return contactName;
    }

    public String getCallType() {
        return callType;
    }

    public Date getDate() {
        return date;
    }
}
