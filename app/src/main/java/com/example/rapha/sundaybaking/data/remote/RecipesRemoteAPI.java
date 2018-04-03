package com.example.rapha.sundaybaking.data.remote;

import com.example.rapha.sundaybaking.data.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesRemoteAPI {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
