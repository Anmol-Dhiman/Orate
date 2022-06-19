package com.example.orate.Repository.RoomDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.orate.DataModel.DateConverter;

import java.util.Date;


@Entity(tableName = "callHistory")
public class CallHistoryModel {


    @PrimaryKey(autoGenerate = true)
    private int id;


    private String profilePictureSrc;
    private String mediaType;
    private String contactName;
    private String callType;
    private String about;


    @TypeConverters({DateConverter.class})
    private Date date;

    public CallHistoryModel(String profilePictureSrc, String mediaType, String contactName, String callType, Date date, String about) {
        this.profilePictureSrc = profilePictureSrc;
        this.mediaType = mediaType;
        this.contactName = contactName;
        this.callType = callType;
        this.about = about;
        this.date = date;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
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
