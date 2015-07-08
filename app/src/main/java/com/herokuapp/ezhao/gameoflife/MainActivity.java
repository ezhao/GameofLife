package com.herokuapp.ezhao.gameoflife;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity {
    Button btnPlay;
    Button btnLoop;
    Button btnRules;
    GameOfLifeView golCanvas;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnLoop = (Button) findViewById(R.id.btnLoop);
        btnRules = (Button) findViewById(R.id.btnRules);
        golCanvas = (GameOfLifeView) findViewById(R.id.golCanvas);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                golCanvas.play();
            }
        });
        btnLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    stopLoop();
                } else {
                    startLoop();
                }
            }
        });
        btnRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RuleDialog ruleDialog = new RuleDialog(MainActivity.this);
                ruleDialog.show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLoop();
    }

    private void stopLoop() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
        btnLoop.setText(getResources().getString(R.string.loop));
        btnLoop.setBackground(getResources().getDrawable(R.drawable.button_background));
        btnLoop.setTextColor(getResources().getColorStateList(R.color.button_text));
    }

    private void startLoop() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                golCanvas.play();
            }
        }, 0, 100);
        btnLoop.setText(getResources().getString(R.string.stop));
        btnLoop.setBackground(getResources().getDrawable(R.drawable.button_background_inverted));
        btnLoop.setTextColor(getResources().getColorStateList(R.color.button_text_inverted));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
