package com.example.rapha.sundaybaking.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.rapha.sundaybaking.data.models.Recipe;
import com.example.rapha.sundaybaking.data.remote.RecipesRemoteAPI;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class RecipeRepository {

    private RecipesRemoteAPI recipesRemoteAPI;

    @Inject
    public RecipeRepository(RecipesRemoteAPI recipesRemoteAPI) {
        this.recipesRemoteAPI = recipesRemoteAPI;
    }

    public LiveData<List<Recipe>> getRecipes(){
        final MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
        recipesRemoteAPI.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
        return recipes;
    }
}
