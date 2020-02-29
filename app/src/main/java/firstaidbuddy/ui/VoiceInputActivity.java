package firstaidbuddy.ui;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.examples.classification.ClassifierActivity;
import org.tensorflow.lite.examples.classification.R;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceInputActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
  private ImageView micIv, searchIv, cameraIv;
  private EditText searchEt;
  private TextToSpeech textToSpeech;
  private String userInputString;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_voice_input);

    cameraIv = findViewById(R.id.cameraIv);
    micIv = findViewById(R.id.micIv);
    searchEt = findViewById(R.id.searchTe);
    searchIv = findViewById(R.id.searchIv);
    textToSpeech = new TextToSpeech(getApplicationContext(), this);

    micIv.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        getSpeechInput();
      }
    });

    searchIv.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        userInputString = searchEt.getText().toString();
        openUserInputActivity(userInputString);
      }
    });

    cameraIv.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ClassifierActivity.class);
        startActivity(intent);
      }
    });
  }

  public void getSpeechInput() {
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    if (intent.resolveActivity(getPackageManager()) != null) {
      startActivityForResult(intent, 11);
    } else {
      Toast.makeText(getApplicationContext(), "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
    }
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case 11:
        if (resultCode == RESULT_OK && data != null) {
          ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
          userInputString = result.get(0).trim();

          //check userInput
          if(userInputString!= null || userInputString.length()>0) {
            openUserInputActivity(userInputString);
          }else{
            Log.i("Mismatched Input","Incorrect Input Received. Try Again");
            textToSpeechConvert("Incorrect Input Received. Please Try Again");
          }
        }
        break;
    }
  }

  public void textToSpeechConvert(String textInput){
    int speechStatus = textToSpeech.speak(textInput, TextToSpeech.QUEUE_FLUSH, null);
    if (speechStatus == TextToSpeech.ERROR) {
      Log.e("TTS", "Error in converting Text to Speech!");
    }
  }

  public void openUserInputActivity(String activityName){
    Intent intent = new Intent(getApplicationContext(),VoiceInputSelectActivity.class);

      if(activityName.equalsIgnoreCase("Acne")){
        intent.putExtra("category", " ");
        intent.putExtra("sub_category", "Acne");
        startActivity(intent);
      }
      else if(activityName.equalsIgnoreCase("Blister")) {
        intent.putExtra("category", " ");
        intent.putExtra("sub_category", "Blister");
        startActivity(intent);
      }
      else if(activityName.equalsIgnoreCase("Bee Bite")) {
        intent.putExtra("category", " ");
        intent.putExtra("sub_category", "Bee Bite ");
        startActivity(intent);
      }
    else if(activityName.equalsIgnoreCase("Dog Bite")) {
        intent.putExtra("category", " ");
        intent.putExtra("sub_category", "Dog Bite ");
        startActivity(intent);
      }
    else if(activityName.equalsIgnoreCase("Insect Bite")) {
        intent.putExtra("category", " ");
        intent.putExtra("sub_category", "Insect Bite ");
        startActivity(intent);
      }
    else if(activityName.equalsIgnoreCase("Snake Bite")) {
        intent.putExtra("category", " ");
        intent.putExtra("sub_category", "Snake Bite ");
        startActivity(intent);
      }
    else if(activityName.equalsIgnoreCase("Scorpion Bite")) {
        intent.putExtra("category", " ");
        intent.putExtra("sub_category", "Scorpion Bite ");
        startActivity(intent);
      }
    else if(activityName.equalsIgnoreCase("Skin Rash")) {
      intent.putExtra("category", " ");
      intent.putExtra("sub_category", "Skin Rash");
      startActivity(intent);
    }
    else{
        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
    }
    startActivity(intent);
  }
}
