package com.netgear.jbyrne.golfcard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.netgear.jbyrne.golfcard.listeners.ButtonListener;


public class ScoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_score);

        ////ADD LISTENERS HERE

        Button btnTally = (Button) findViewById(R.id.btnTally);
        Button btnClear = (Button) findViewById(R.id.btnClear);
        Button btnIn = (Button) findViewById(R.id.btnIn);

        btnTally.setOnClickListener(new ButtonListener(this));  // 'this' passes in this Activity
        btnClear.setOnClickListener(new ButtonListener(this));  // so that the Listener Class has
        btnIn.setOnClickListener(new ButtonListener(this));     // access to these resources.

        //////////////////////

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.score, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
