package com.example.rapha.sundaybaking.ui.instructions;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import timber.log.Timber;

/*
 * Lifecycle Observer which is managing the ExoPlayer instance and reacts on lifecycle changes
 * of the PlayerFragment
 * The setup is based on the ExoPlayer codelab:
 * https://codelabs.developers.google.com/codelabs/exoplayer-intro/
 */
class PlayerComponent implements LifecycleObserver {

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private SimpleExoPlayer player;
    private final Context context;
    private final PlayerView playerView;
    private MediaSource mediaSource;
    private PlayerListener playerListener;
    private PlayerState playerState;

    public PlayerComponent(Context context, PlayerView playerView, PlayerListener playerListener) {
        this.context = context;
        this.playerView = playerView;
        this.playerListener = playerListener;
    }

    public void playVideo(String url) {
        prepareMediaSource(Uri.parse(url));
    }

    /*
     * As on API > 23 the app might be visible in split window mode but not active, the player needs
     * to be initialized in onStart
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onStart() {
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    /*
     * On API <= 23: Wait as long as possible before initializing the player
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume() {
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    /*
     * On API <= 23 there is no guarantee of onStop being called
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onPause() {
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    /*
     * On API > 23 the activity is still visible and onStop is guaranteed to be called
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onStop() {
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        Timber.d("Create ExoPlayer instance");
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(context),
                new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                new DefaultLoadControl());
        playerView.setPlayer(player);
    }

    private void releasePlayer() {
        playerListener.savePlayerState(new PlayerState(player.getCurrentPosition(), player.getPlayWhenReady()));
        player.stop();
        if (mediaSource != null) {
            mediaSource.releaseSource();
        }
        player.release();
        mediaSource = null;
        player = null;
        Timber.d("ExoPlayer released");
    }

    private void prepareMediaSource(Uri uri) {
        Timber.d("Set and play media source: %s", uri.toString());
        String userAgent = Util.getUserAgent(context, "SundayBaking");
        DefaultHttpDataSourceFactory defaultHttpDataSourceFactory =
                new DefaultHttpDataSourceFactory(userAgent);
        mediaSource = new ExtractorMediaSource.Factory(
                defaultHttpDataSourceFactory)
                .createMediaSource(uri);
        player.prepare(mediaSource, true, false);
        // restore player state if existing
        if (playerState != null) {
            player.setPlayWhenReady(playerState.isPlaying());
            player.seekTo(playerState.getPlayerPosition());
        } else {
            player.setPlayWhenReady(true);
        }

    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }
}
