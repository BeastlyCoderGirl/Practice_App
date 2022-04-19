package com.example.hokageappdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class HokageCategoryActivity extends Activity {

    /*We’re adding these as private variables so we can close
    the database and cursor in our onDestroy() method.*/
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hokage_category);

        ListView listHokages = (ListView) findViewById(R.id.list_hokage);
        SQLiteOpenHelper hokageDatabaseHelper  = new HokageDatabaseHelper(this);
        try{

            //Get a reference to the database.
            db = hokageDatabaseHelper.getReadableDatabase();
            //Create the cursor.
            cursor = db.query("HOKAGE",
                    new String[] {"_id", "NAME"},
                    null,null,null,null,null);

            //Create the cursor adapter.
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    //Map the contents of the NAME column to the text
                    //in the ListView.
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);

            //Set the adapter to the ListView
            listHokages.setAdapter(listAdapter);
        }catch(SQLException e)
        {
            Toast.makeText(this, "Database Catergory unavailable", Toast.LENGTH_SHORT).show();
        }

        //Create a listener to listen for clicks in the list view
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> listDrinks,
                                            View itemView,
                                            int position,
                                            long id) {
                        //Pass the drink the user clicks on to DrinkActivity
                        Intent intent = new Intent(HokageCategoryActivity.this,
                                HokageActivity.class);
                        intent.putExtra(HokageActivity.EXTRA_HOKAGEID, (int) id);
                        startActivity(intent);
                    }
                };
        //Assign the listener to the list view
        listHokages.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}