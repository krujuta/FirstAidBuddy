package firstaidbuddy.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.examples.classification.R;

import java.util.Locale;

import firstaidbuddy.login.LoginSharedPreferences;

public class AdminActivity extends AppCompatActivity implements TextToSpeech.OnInitListener
{
    private TextView signIn,sin,sup;
    private EditText usernameEt,passwordEt;
    private TextToSpeech textToSpeech ;
    private SharedPreferences sharedpreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        sin = (TextView)findViewById(R.id.sin);
        sup = (TextView)findViewById(R.id.sup);
        signIn = (TextView) findViewById(R.id.signInTv);
        usernameEt = (EditText) findViewById(R.id.usernameEt);
        passwordEt = (EditText) findViewById(R.id.passwordEt);

        sharedpreferences = getSharedPreferences(LoginSharedPreferences.LOGINPREFERENCES, Context.MODE_PRIVATE);

        sin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { //for navigating to user tab
                Intent it = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(it);
            }
        });

        sup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { //for navigating to user tab
                Intent it = new Intent(getApplicationContext(), VoiceInputActivity.class);
                startActivity(it);
            }
        });

        //for sign in
        signIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {  //login for sign in
                logIn();
            }
        });

    textToSpeech = new TextToSpeech(getApplicationContext(), this);
//    initialAppPrompt();


//        new CountDownTimer(3000, 1500) {
//        @Override
//            public void onTick(long l) { //for initial prompt
//            initialAppPrompt();  //speak after 1000ms
//        }
//        @Override
//            public void onFinish() {
//
//            }
//        }.start();
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
    protected void onPause() {
        // TODO Auto-generated method stub

        if(textToSpeech != null){

            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
    public void textToSpeechConvert(String textInput){
        int speechStatus = textToSpeech.speak(textInput, TextToSpeech.QUEUE_FLUSH, null);
        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }
    }

//    public void initialAppPrompt(){
//        textToSpeechConvert("Log In or Navigate to User");
//    }

    public void initSharedPreferences(){ //for future modularity
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LoginSharedPreferences.USERNAME, "admin");
        editor.putString(LoginSharedPreferences.PASSWORD, "admin");
        editor.commit();
    }

    public void logIn() {
        initSharedPreferences(); //for future modularity
        sharedpreferences = getSharedPreferences(LoginSharedPreferences.LOGINPREFERENCES, Context.MODE_PRIVATE);

        String shName = sharedpreferences.getString(LoginSharedPreferences.USERNAME, "");
        String shPass = sharedpreferences.getString(LoginSharedPreferences.PASSWORD, "");

        if (shName.equalsIgnoreCase(usernameEt.getText().toString()) && shPass.equalsIgnoreCase(passwordEt.getText().toString())) {
            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), AdminDataInsert.class);
            startActivity(intent);
        }else{
            textToSpeechConvert("Invalid Credentials");
            Log.e("Login Error","Login Failed" );
            Toast.makeText(getApplicationContext(),"Login Failed , Try Again",Toast.LENGTH_SHORT).show();
        }
    }
}
