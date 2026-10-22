package com.example.quicktiles.tiles;

import android.media.AudioManager;

import com.example.quicktiles.R;

public class MediaDownTile extends VolumeTile {
    @Override protected int stream() { return AudioManager.STREAM_MUSIC; }
    @Override protected boolean raise() { return false; }
    @Override protected int labelRes() { return R.string.tile_media_down; }
}
