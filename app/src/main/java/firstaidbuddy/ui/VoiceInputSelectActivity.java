package firstaidbuddy.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.examples.classification.R;

import java.util.ArrayList;
import java.util.Locale;

import firstaidbuddy.database.DBHelper;

public class VoiceInputSelectActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

  private TextToSpeech textToSpeech;
  private ImageView playB,pauseB,stopB;
  private String category,sub_category ;
  private TextView voiceInputSelectResultTv;
  private String userInputString;
  private Button backB;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_voice_input_select);

    voiceInputSelectResultTv = findViewById(R.id.voiceInputSelectResultTv);
    voiceInputSelectResultTv.setMovementMethod(new ScrollingMovementMethod());

      textToSpeech = new TextToSpeech(getApplicationContext(), this);
      sub_category= getIntent().getStringExtra("sub_category");
      category= getIntent().getStringExtra("category");
      playB = findViewById(R.id.playIv);
      pauseB = findViewById(R.id.pauseIv);
      backB = findViewById(R.id.backButton);
//      stopB = findViewById(R.id.stopIv);

      if(!sub_category.isEmpty()){
        userInputString = fetchDataFromDb(sub_category);
        voiceInputSelectResultTv.setHeight(50);
        voiceInputSelectResultTv.setTextColor(Color.WHITE);
        voiceInputSelectResultTv.setText(userInputString);
      }

      playB.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          textToSpeechConvert(userInputString);
        }
      });

      pauseB.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          textToSpeech.stop();
        }
      });

    backB.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(),VoiceInputActivity.class);
        startActivity(intent);
      }
    });
  }

  @Override
  public void onInit(int i) {
    if (i == TextToSpeech.SUCCESS) {
      int result = textToSpeech.setLanguage(Locale.US);
      if (result == TextToSpeech.LANG_MISSING_DATA
              || result == TextToSpeech.LANG_NOT_SUPPORTED) {
        Log.e("TTS", "Language is not supported");
      } else {
        Log.e("TTS", "Language Not Supported");
      }
    } else {
      Log.e("TTS", "Initilization Failed");
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (textToSpeech != null) {
      textToSpeech.stop();
      textToSpeech.shutdown();
    }
  }
    public String fetchDataFromDb(String subCategoryString){
    DBHelper dbHelper = new DBHelper(this);
    Cursor crs = dbHelper.getData();
    String subCatDb = "";
    String firstAidDb = "" ;
    String output = "";
    while (crs.moveToNext()) {
      subCatDb = crs.getString(crs.getColumnIndex("sub_category"));
      firstAidDb = crs.getString(crs.getColumnIndex("first_aid"));

      if(subCatDb.equalsIgnoreCase(subCategoryString.trim())){
        output = firstAidDb ;
          break;
      }else{
        output =  "Data Not Found" ;
            Log.i("Data Not Found","Data Not Found");
      }
    }
    return output ;
  }
  public void textToSpeechConvert(String textInput){
    int speechStatus = textToSpeech.speak(textInput, TextToSpeech.QUEUE_FLUSH, null);
    if (speechStatus == TextToSpeech.ERROR) {
      Log.e("TTS", "Error in converting Text to Speech!");
    }
  }
}
