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
    private String videoUrl;
    private RecipeRepository recipeRepository;
    private int stepId;

    public PlayerViewModel(@NonNull Application application, RecipeRepository recipeRepository, int stepId) {
        context = application.getApplicationContext();
        this.recipeRepository = recipeRepository;
        this.stepId = stepId;
        preparePlayer();
    }

    public LiveData<InstructionStep> getInstrucionStep() {
        return recipeRepository.getInstructionStep(stepId);
    }

    public void startVideo(String videoUrl) {
        setAndPlayMediaSource(Uri.parse(videoUrl));
    }

    private void preparePlayer() {
        if (player == null) {
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
}
