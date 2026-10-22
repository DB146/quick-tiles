package com.example.quicktiles;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

/**
 * Third-party apps cannot power the device off directly. The supported way to
 * offer a "power button" is to ask the system to show its own power menu, which
 * an accessibility service can do with GLOBAL_ACTION_POWER_DIALOG.
 * This service does not read the screen or react to any events.
 */
public class PowerAccessibilityService extends AccessibilityService {

    private static volatile PowerAccessibilityService instance;

    public static boolean isRunning() {
        return instance != null;
    }

    /** Opens the system power menu. Returns false if the service is not enabled. */
    public static boolean showPowerMenu() {
        PowerAccessibilityService service = instance;
        return service != null && service.performGlobalAction(GLOBAL_ACTION_POWER_DIALOG);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        instance = this;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        instance = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        instance = null;
        super.onDestroy();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Not used.
    }

    @Override
    public void onInterrupt() {
        // Not used.
    }
}
