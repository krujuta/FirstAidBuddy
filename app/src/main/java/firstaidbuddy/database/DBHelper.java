package firstaidbuddy.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "healthbuddyapp.db";
    public static final String REPO_TABLE_NAME = "repo_table";

    public static final String REPO_COLUMN_SR_NO = "sr_no";
    public static final String REPO_COLUMN_CATEGORY = "category";
    public static final String REPO_COLUMN_SUBCATEGORY = "sub_category";
    public static final String REPO_COLUMN_DESCRIPTION = "description";
    public static final String REPO_COLUMN_FIRST_AID = "first_aid";
    private static int sr_no = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String createTableQuery = "CREATE TABLE repo_table (sr_no TEXT, category TEXT, sub_category TEXT, description TEXT, first_aid TEXT, PRIMARY KEY (sr_no));" ;
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS repo_table");
        onCreate(db);
    }

    public boolean initRepo(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS repo_table"); //drop and recreate if already exists while loading the app
        onCreate(db);

        //Using CSV file
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("healthappdata.csv");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line;
            String[] values;

            while ((line = bufferedReader.readLine()) != null) {

                values = line.split(",");
                String insertCommand = String.format("insert into repo_table(sr_no,category, sub_category, description, first_aid) values(\"%s\",\"%s\", \"%s\", \"%s\", \"%s\")",
                        values[0], values[1], values[2], values[3], values[4]);
                db.execSQL(insertCommand);

            }
            return true;
        } catch (IOException e) {
            Log.e("TBCAE", "Failed to open data input file");
            return false;
        }
    }

    public boolean insertDataManually(String category,String sub_category,String description,String first_aid) {
        SQLiteDatabase db = this.getWritableDatabase();
         //get serial number from table and then increment    //** next version update
        String srNoStr = String.valueOf(sr_no);

        ContentValues contentValues = new ContentValues();
        contentValues.put("sr_no", srNoStr);
        contentValues.put("category", category);
        contentValues.put("sub_category", sub_category);
        contentValues.put("description", description);
        contentValues.put("first_aid", first_aid);

        try {
            db.insert(REPO_TABLE_NAME, null, contentValues);
            sr_no = sr_no+1; //increment serialNumber after insertion
            return true;
        }catch (Exception e){
            Log.e("DB Update","Error Inserting Data");
            return false ;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + REPO_TABLE_NAME, null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, REPO_TABLE_NAME);
        return numRows;
    }
}
