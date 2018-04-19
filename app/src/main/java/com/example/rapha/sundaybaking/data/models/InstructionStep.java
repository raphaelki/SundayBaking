package com.example.rapha.sundaybaking.data.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Recipe.class,
                parentColumns = "name",
                childColumns = "recipe_name",
                onDelete = CASCADE),
        tableName = "instruction_steps")
public class InstructionStep {

    @PrimaryKey(autoGenerate = true)
    private Integer dbId;
    @SerializedName("id")
    @ColumnInfo(name = "step_no")
    private Integer stepNo;
    @SerializedName("shortDescription")
    @ColumnInfo(name = "short_description")
    private String shortDescription;
    @SerializedName("description")
    private String description;
    @SerializedName("videoURL")
    @ColumnInfo(name = "video_url")
    private String videoURL;
    @SerializedName("thumbnailURL")
    @ColumnInfo(name = "thumbnail_url")
    private String thumbnailURL;
    @ColumnInfo(name = "recipe_name")
    private String recipeName;

    public InstructionStep(Integer dbId, Integer stepNo, String shortDescription, String description, String videoURL, String thumbnailURL, String recipeName) {
        this.dbId = dbId;
        this.stepNo = stepNo;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
        this.recipeName = recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public Integer getStepNo() {
        return stepNo;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public Integer getDbId() {
        return dbId;
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
