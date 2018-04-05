package com.example.rapha.sundaybaking;

import android.app.Application;

import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.local.RecipeDatabase;
import com.example.rapha.sundaybaking.data.remote.RecipesRemoteAPI;
import com.example.rapha.sundaybaking.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class SundayBakingApp extends Application {

    private AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        appExecutors = new AppExecutors();
        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

    private RecipeDatabase getRecipeDatabase(){
        return RecipeDatabase.getInstance(this);
    }

    public RecipeRepository getRecipeRepository(){
        return RecipeRepository.getInstance(appExecutors, createRecipesAPI(), getRecipeDatabase());
    }

    private RecipesRemoteAPI createRecipesAPI(){
        return new Retrofit.Builder()
                .baseUrl(Constants.REPOSITORY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipesRemoteAPI.class);
    }
}
