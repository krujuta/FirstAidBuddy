package firstaidbuddy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.tensorflow.lite.examples.classification.R;

import java.util.ArrayList;
import java.util.List;

import firstaidbuddy.database.DBHelper;

public class AdminDataInsert extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner cat,subcat;
    private TextView saveTv,sup,sin;
    private EditText descTv, firstAidTv;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_data_insert2);

        cat = findViewById(R.id.catSpinner);
        subcat = findViewById(R.id.subcatSpinner);
        sup = (TextView)findViewById(R.id.sup);
        sin = (TextView)findViewById(R.id.sin);
        saveTv = (TextView)findViewById(R.id.saveTv);
        descTv = (EditText) findViewById(R.id.descEt);
        firstAidTv = (EditText) findViewById(R.id.firstAidEt);
        dbHelper = new DBHelper(this.getApplicationContext());

        cat.setOnItemSelectedListener(this);
        dbHelper.initRepo(getApplicationContext()); //to initialize repo

        sup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { //for navigating to user tab
                Intent it = new Intent(getApplicationContext(), VoiceInputActivity.class);
                startActivity(it);
            }
        });

        sin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { //for navigating to user tab
                Intent it = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(it);
            }
        });

        saveTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { //for navigating to user tab
                saveData();
            }
        });

    }

    public void saveData(){
        String category = cat.getSelectedItem().toString();
        String subCategory = subcat.getSelectedItem().toString();
        String description = descTv.getText().toString();
        String firstAid = firstAidTv.getText().toString();
        boolean result = dbHelper.insertDataManually(category,subCategory,description,firstAid);

        if(result == true){
            Toast.makeText(this.getApplicationContext(),"Data Inserted Successfully",Toast.LENGTH_LONG).show();

            descTv.setText(" ");
            firstAidTv.setText(" ");

            descTv.setHint("Description");
            firstAidTv.setHint("First Aid");

        }else{
            Toast.makeText(this.getApplicationContext(),"Error Inserting Data",Toast.LENGTH_LONG).show();
        }
    }


    //To add sub-categories for categories
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        String catString= String.valueOf(cat.getSelectedItem());
        Toast.makeText(this, catString, Toast.LENGTH_SHORT).show();
        List<String> list;

        if(catString.contentEquals("Ache")) { //
            list = new ArrayList<String>();
            list.add("Head Ache");
            list.add("Stomatch Ache");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            subcat.setAdapter(dataAdapter);
        }
        if(catString.contentEquals("Beauty")) { //
            list = new ArrayList<String>();
            list.add("Acne");
            list.add("Blister");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            subcat.setAdapter(dataAdapter2);
        }
        if(catString.contentEquals("Bite")) { //
            list = new ArrayList<String>();
            list.add("Bee Bite");
            list.add("Dog Bite");
            list.add("Insect Bite");
            list.add("Scorpion Bite");
            list.add("Snake Bite");

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            subcat.setAdapter(dataAdapter2);
        }
        if(catString.contentEquals("Burn")) { //
            list = new ArrayList<String>();
            list.add("Chemical Burn");
            list.add("Oil Burn");
            list.add("Sun Burn");
            list.add("Water Burn");

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            subcat.setAdapter(dataAdapter2);
        }
        if(catString.contentEquals("Irritation Or Infection")) { //
            list = new ArrayList<String>();
            list.add("Ear Irritation");
            list.add("Eye Irritation");
            list.add("Indigestion");
            list.add("Loose motion");
            list.add("Skin rash");

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            subcat.setAdapter(dataAdapter2);
        }
        if(catString.contentEquals("Seasonal Illness")) { //
            list = new ArrayList<String>();
            list.add("Cold and cough");
            list.add("Fever");
            list.add("Giddiness");
            list.add("Nose bleeding");
            list.add("Tiredness");

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            subcat.setAdapter(dataAdapter2);
        }
        if(catString.contentEquals("Wound")) { //
            list = new ArrayList<String>();
            list.add("Blood clot");
            list.add("Cramp");
            list.add("Fracture");

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            subcat.setAdapter(dataAdapter2);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(),"Please select the values",Toast.LENGTH_LONG).show();
    }
}

