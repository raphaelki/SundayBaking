package com.example.rapha.sundaybaking.data.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipe_id",
        onDelete = CASCADE),
        tableName = "instruction_steps")
public class InstructionStep {

    @SerializedName("id")
    @PrimaryKey
    private Integer id;
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
    @ColumnInfo(name = "recipe_id")
    private int recipeId;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
