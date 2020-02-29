package firstaidbuddy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import org.tensorflow.lite.examples.classification.R;

import firstaidbuddy.database.DBHelper;

public class SpashScreenActivity extends AppCompatActivity {

  // Splash screen timer
  private static int SPLASH_TIME_OUT = 4000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_spash_screen);

    new Handler().postDelayed(new Runnable() {

      /*
       * Showing splash screen with a timer. This will be useful when you
       * want to show case your app logo / company
       */

      @Override
      public void run() {
        // This method will be executed once the timer is over
        // Start your app main activity
        boolean res = new DBHelper(getApplicationContext()).initRepo(getApplicationContext()); //init database

        if(res == true) {
          Toast.makeText(getApplicationContext(),"Succssessfully Updated Repository ",Toast.LENGTH_SHORT).show();
        }else {
          Toast.makeText(getApplicationContext(),"Error Updating Repository ",Toast.LENGTH_SHORT).show();
        }

        Intent i = new Intent(getApplicationContext(), VoiceInputActivity.class);
        startActivity(i);

        // close this activity
        finish();
      }
    }, SPLASH_TIME_OUT);
  }
}
