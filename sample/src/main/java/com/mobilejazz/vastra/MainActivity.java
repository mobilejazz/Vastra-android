package com.mobilejazz.vastra;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.mobilejazz.library.ValidationService;
import com.mobilejazz.library.Vastra;
import com.mobilejazz.library.strategies.ValidationStrategy;
import com.mobilejazz.library.strategies.reachability.ReachabilityValidationStrategy;
import com.mobilejazz.library.strategies.timestamp.TimestampValidationStrategy;
import com.mobilejazz.vastra.model.User;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "Vastra";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
      }
    });

    List<ValidationStrategy> strategies =
        Arrays.asList(new ReachabilityValidationStrategy(this.getApplicationContext()),
            new TimestampValidationStrategy());

    Vastra vastra = Vastra.with(strategies).build();

    final ValidationService validationService = vastra.validationService();

    final User user = new User();
    user.setId(1);
    user.setName("Jose Luis Franconetti Olmedo");
    user.setLastUpdate(new Date(System.currentTimeMillis()));

    boolean isValid = validationService.isValid(user);

    Log.d(TAG, "onCreate: User: " + user.toString());

    Log.d(TAG, "onCreate: User is valid: " + isValid);

    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        boolean isValid = validationService.isValid(user);

        Log.d(TAG, "onCreate: User is valid after 6 seconds: " + isValid);
      }
    }, 6000);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
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
