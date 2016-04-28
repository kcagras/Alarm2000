package com.alarmcompany.alarm2000_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import me.philio.pinentry.PinEntryView;

/**
 * Created by Kawa on 28.04.2016.
 */
public class PinActivity extends Activity implements OnClickListener{

    private static final String TAG = PinActivity.class.getSimpleName();
    Button btnEnter;
    PinEntryView pinEntryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        pinEntryView = (PinEntryView) findViewById(R.id.pin_entry_simple);
        btnEnter = (Button) findViewById(R.id.btn_enter);
        btnEnter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if ((pinEntryView.getText().toString()).equals("0000")) {
            Intent intent = new Intent(this, LiveActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(PinActivity.this, "Sorry Bro, PIN is nich korrekt...", Toast.LENGTH_SHORT).show();
        }
    }
}
