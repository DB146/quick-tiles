package com.example.quicktiles.tiles;

import android.media.AudioManager;

import com.example.quicktiles.R;

public class RingUpTile extends VolumeTile {
    @Override protected int stream() { return AudioManager.STREAM_RING; }
    @Override protected boolean raise() { return true; }
    @Override protected int labelRes() { return R.string.tile_ring_up; }
}
