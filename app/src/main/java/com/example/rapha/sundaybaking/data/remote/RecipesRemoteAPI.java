package com.example.rapha.sundaybaking.data.remote;

import com.example.rapha.sundaybaking.data.models.Recipe;
import com.example.rapha.sundaybaking.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesRemoteAPI {

    @GET(Constants.REPOSITORY_JSON_FILENAME)
    Call<List<Recipe>> getRecipes();
}
