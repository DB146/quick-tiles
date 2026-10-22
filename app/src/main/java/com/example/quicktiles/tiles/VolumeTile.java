package com.example.quicktiles.tiles;

import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import com.example.quicktiles.R;

/** Base class for the ten "volume up / volume down" tiles. */
public abstract class VolumeTile extends TileService {

    /** One of AudioManager.STREAM_*. */
    protected abstract int stream();

    /** true = raise volume, false = lower volume. */
    protected abstract boolean raise();

    protected abstract int labelRes();

    @Override
    public void onStartListening() {
        refresh();
    }

    @Override
    public void onClick() {
        AudioManager am = getSystemService(AudioManager.class);
        if (am == null) return;
        try {
            am.adjustStreamVolume(
                    stream(),
                    raise() ? AudioManager.ADJUST_RAISE : AudioManager.ADJUST_LOWER,
                    AudioManager.FLAG_SHOW_UI);
        } catch (SecurityException e) {
            // Changing ring/notification volume into silent mode needs Do Not Disturb access.
            Toast.makeText(this, R.string.dnd_access_needed, Toast.LENGTH_LONG).show();
        }
        refresh();
    }

    private void refresh() {
        Tile tile = getQsTile();
        AudioManager am = getSystemService(AudioManager.class);
        if (tile == null || am == null) return;

        int stream = stream();
        int current = am.getStreamVolume(stream);
        int max = am.getStreamMaxVolume(stream);
        int min = Build.VERSION.SDK_INT >= 28 ? am.getStreamMinVolume(stream) : 0;
        boolean atLimit = raise() ? current >= max : current <= min;

        tile.setLabel(getString(labelRes()));
        tile.setIcon(Icon.createWithResource(this,
                raise() ? R.drawable.ic_volume_up : R.drawable.ic_volume_down));
        tile.setState(atLimit ? Tile.STATE_INACTIVE : Tile.STATE_ACTIVE);
        if (Build.VERSION.SDK_INT >= 29) {
            tile.setSubtitle(current + " / " + max);
        }
        tile.updateTile();
    }
}
