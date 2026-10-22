package com.example.quicktiles.tiles;

import android.graphics.drawable.Icon;
import android.media.AudioManager;
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
        if (tile == null) return;

        tile.setLabel(getString(labelRes()));
        tile.setIcon(Icon.createWithResource(this,
                raise() ? R.drawable.ic_volume_up : R.drawable.ic_volume_down));
        // INACTIVE = the normal dark "off" look. Still tappable, unlike STATE_UNAVAILABLE.
        tile.setState(Tile.STATE_INACTIVE);
        tile.updateTile();
    }
}
