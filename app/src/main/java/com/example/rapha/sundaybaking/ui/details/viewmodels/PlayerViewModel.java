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

    public void startVideo(InstructionStep step) {
        player.stop();
        String url = "";
        if (!step.getVideoURL().isEmpty()) url = step.getVideoURL();
        else if (!step.getThumbnailURL().isEmpty()) url = step.getThumbnailURL();
        if (!url.isEmpty()) setAndPlayMediaSource(Uri.parse(url));
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

    public void stopPlayer() {
        if (player != null) {
            player.stop();
        }
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

    public SimpleExoPlayer getPlayerInstance() {
        return player;
    }

    public LiveData<InstructionStep> getSelectedStep(int stepNo) {
        return repository.getInstructionStep(recipeName, stepNo);
    }
}
