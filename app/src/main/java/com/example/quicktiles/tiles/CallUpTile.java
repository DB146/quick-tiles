package com.example.quicktiles.tiles;

import android.media.AudioManager;

import com.example.quicktiles.R;

public class CallUpTile extends VolumeTile {
    @Override protected int stream() { return AudioManager.STREAM_VOICE_CALL; }
    @Override protected boolean raise() { return true; }
    @Override protected int labelRes() { return R.string.tile_call_up; }
}
