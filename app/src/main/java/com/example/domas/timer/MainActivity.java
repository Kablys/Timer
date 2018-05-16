package com.example.domas.timer;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPress((Button) findViewById(R.id.up));
        buttonPress((Button) findViewById(R.id.down));
    }

    Boolean otherPressed = false;
    Boolean ready = false;
    Boolean going = false;

    private void buttonPress (Button button){
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.RL);
        final ChronometerM chrono = (ChronometerM) findViewById(R.id.chrono);
        final TextView message = (TextView) findViewById(R.id.message);
        button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (otherPressed){
                            if (!ready && going){
                                chrono.stop();
                                going = false;

                                rl.setBackgroundColor(Color.RED);
                                message.setText("Done");
                            }else {
                                ready = true;
                                chrono.setBase(SystemClock.elapsedRealtime());
                                rl.setBackgroundColor(Color.parseColor("#FFEA00"));
                                message.setText("Ready");
                            }
                        } else {
                            otherPressed = true;
                        }
                        v.setBackgroundColor(Color.parseColor("#32000000"));
                        break;
                    case MotionEvent.ACTION_UP:
                        if (ready && otherPressed && !going){
                            chrono.setBase(SystemClock.elapsedRealtime());
                            chrono.start();
                            ready = false;
                            going = true;
                            rl.setBackgroundColor(Color.GREEN);
                            message.setText("GO!");
                        }
                        otherPressed = false;
                        v.setBackgroundColor(Color.parseColor("#00000000"));
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Starts app in fullscreen(immersive mode)
        // Do this on resume, for app remain in fullscreen when returning to it
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    // FUNCTIONAL
    // COMPLETED get touch and release actions
    // COMPLETE require both button preses
        // TODO Test and simplify(if possible)
    // COMPLETE get better chronometer implementation
    // COMPLETE implement fullscreen(immersive mode) and remove ActionBar
    // COMPLETE remove vertical orientation
        // COMPLETE both sides
    // TODO Clean up activity_main

    // VISUAL
    // COMPLETE add semi transparent color to touched button
    // COMPLETE add instructional text
    // COMPLETE add shadow to chronometer text
}
