package com.example.rapha.sundaybaking.ui.instructions;

public class PlayerState {

    private long playerPosition;
    private boolean isPlaying;

    public PlayerState(long playerPosition, boolean isPlaying) {
        this.playerPosition = playerPosition;
        this.isPlaying = isPlaying;
    }

    public long getPlayerPosition() {
        return playerPosition;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
