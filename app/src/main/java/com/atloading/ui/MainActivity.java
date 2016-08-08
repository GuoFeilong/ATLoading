package com.atloading.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.atloading.R;
import com.atloading.customview.ATCircleDialog;
import com.atloading.customview.ATWaterDialog;

public class MainActivity extends AppCompatActivity {

    private ATCircleDialog atCircleDialog;
    private ATWaterDialog atWaterDialog;
    private boolean showCircleOrWater;
    private String snakeDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        atWaterDialog = new ATWaterDialog.Builder(this, R.layout.dialog_water_loading)
                .cancelable(true)
                .outsideCancelable(true)
                .loadingDesc("我擦,加载....")
                .build();

        atCircleDialog = new ATCircleDialog.Builder(this, R.layout.dialog_circle_loading)
                .cancelable(true)
                .outsideCancelable(true)
                .build();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showCircleOrWater) {
                    atCircleDialog.dismiss();
                    atWaterDialog.show();
                    snakeDesc = "显示-->>waterloading...";
                } else {
                    atWaterDialog.dismiss();
                    atCircleDialog.show();
                    snakeDesc = "显示-->>圆环loading...";

                }
                showCircleOrWater = !showCircleOrWater;

                Snackbar.make(view, snakeDesc, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
