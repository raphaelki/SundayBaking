package com.example.rapha.sundaybaking;

import android.app.Application;

import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.local.RecipeDatabase;
import com.example.rapha.sundaybaking.data.remote.RecipesRemoteAPI;
import com.example.rapha.sundaybaking.util.Constants;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class SundayBakingApp extends Application {

    final private AppExecutors appExecutors = new AppExecutors();
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
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

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }
}
