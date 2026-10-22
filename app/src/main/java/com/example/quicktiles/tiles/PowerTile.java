package com.example.quicktiles.tiles;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import com.example.quicktiles.MainActivity;
import com.example.quicktiles.PowerAccessibilityService;
import com.example.quicktiles.R;

/** Quick Settings tile that opens the system power menu. */
public class PowerTile extends TileService {

    @Override
    public void onStartListening() {
        refresh();
    }

    @Override
    public void onClick() {
        if (!PowerAccessibilityService.showPowerMenu()) {
            Toast.makeText(this, R.string.enable_accessibility_first, Toast.LENGTH_LONG).show();
            openApp();
        }
    }

    private void refresh() {
        Tile tile = getQsTile();
        if (tile == null) return;
        boolean ready = PowerAccessibilityService.isRunning();
        tile.setLabel(getString(R.string.tile_power));
        tile.setIcon(Icon.createWithResource(this, R.drawable.ic_power));
        tile.setState(ready ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        if (Build.VERSION.SDK_INT >= 29) {
            tile.setSubtitle(ready ? null : getString(R.string.tile_needs_setup));
        }
        tile.updateTile();
    }

    @SuppressWarnings("deprecation")
    private void openApp() {
        Intent intent = new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 34) {
            startActivityAndCollapse(PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT));
        } else {
            startActivityAndCollapse(intent);
        }
    }
}
