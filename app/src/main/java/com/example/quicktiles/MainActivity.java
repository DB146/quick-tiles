package com.example.quicktiles;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView dndStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        int pad = dp(20);
        root.setPadding(pad, pad, pad, pad);
        scroll.addView(root);

        TextView title = text(getString(R.string.app_name), 24);
        title.setTypeface(null, Typeface.BOLD);
        root.addView(title);
        root.addView(text(getString(R.string.main_intro), 16));
        root.addView(text(getString(R.string.main_step_add), 16));
        root.addView(text(getString(R.string.main_step_dnd), 16));

        dndStatus = text("", 16);
        dndStatus.setTypeface(null, Typeface.BOLD);
        root.addView(dndStatus);
        root.addView(button(R.string.btn_dnd, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
            }
        }));

        setContentView(scroll);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationManager nm = getSystemService(NotificationManager.class);
        boolean dnd = nm != null && nm.isNotificationPolicyAccessGranted();
        dndStatus.setText(dnd ? R.string.status_dnd_on : R.string.status_dnd_off);
    }

    private void open(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ignored) {
            // Settings screen not available on this device.
        }
    }

    private TextView text(CharSequence s, float sp) {
        TextView tv = new TextView(this);
        tv.setText(s);
        tv.setTextSize(sp);
        tv.setPadding(0, dp(6), 0, dp(6));
        return tv;
    }

    private Button button(int label, View.OnClickListener listener) {
        Button b = new Button(this);
        b.setText(label);
        b.setOnClickListener(listener);
        return b;
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }
}
