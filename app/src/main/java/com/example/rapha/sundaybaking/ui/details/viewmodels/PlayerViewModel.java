package com.example.rapha.sundaybaking.ui.details.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.models.InstructionStep;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import timber.log.Timber;

public class PlayerViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SimpleExoPlayer player;
    private RecipeRepository repository;
    private String recipeName;

    public PlayerViewModel(@NonNull Application application, RecipeRepository recipeRepository, String recipeName) {
        Timber.d("Creating PlayerViewModel for recipe: %s", recipeName);
        context = application.getApplicationContext();
        repository = recipeRepository;
        this.recipeName = recipeName;
        preparePlayer();
    }

    public void startVideo(String videoUrl) {
        setAndPlayMediaSource(Uri.parse(videoUrl));
    }

    private void preparePlayer() {
        if (player == null) {
            Timber.d("Create ExoPlayer instance");
            TrackSelector trackSelector = new DefaultTrackSelector();
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        }
    }

    private void releasePlayer() {
        player.stop();
        player.release();
        player = null;
        Timber.d("ExoPlayer released");
    }

    public void setAndPlayMediaSource(Uri uri) {
        Timber.d("Set and play media source: %s", uri.toString());
        String userAgent = Util.getUserAgent(context, "SundayBaking");
        DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(context, userAgent);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory).createMediaSource(uri);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        releasePlayer();
    }

    public LiveData<List<InstructionStep>> getSteps() {
        return repository.getInstructionSteps(recipeName);
    }

    public SimpleExoPlayer getPlayerInstance() {
        return player;
    }
}
