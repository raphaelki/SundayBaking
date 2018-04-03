package com.example.rapha.sundaybaking.data.models;

import com.google.gson.annotations.SerializedName;

public class InstructionStep {

    @SerializedName("id")
    public Integer id;
    @SerializedName("shortDescription")
    public String shortDescription;
    @SerializedName("description")
    public String description;
    @SerializedName("videoURL")
    public String videoURL;
    @SerializedName("thumbnailURL")
    public String thumbnailURL;

    public Integer getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
