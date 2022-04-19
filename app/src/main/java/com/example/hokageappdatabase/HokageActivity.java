package com.example.hokageappdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HokageActivity extends AppCompatActivity {

    public static final String EXTRA_HOKAGEID = "hokageId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hokage);

        //This is the ID of the drink the user chose.
        int hokageId = (Integer)getIntent().getExtras().get(EXTRA_HOKAGEID);

        //Create a cursor
        SQLiteOpenHelper hokageDatabaseHelper = new HokageDatabaseHelper(this);

        try{

            SQLiteDatabase db = hokageDatabaseHelper.getReadableDatabase();

            /*Create a cursor that gets the
                NAME, DESCRIPTION, and IMAGE_
                RESOURCE_ID data from the HOKAGE
                table where _id matches hokageId
            */
            Cursor cursor = db.query("HOKAGE",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[] {Integer.toString(hokageId)},
                    null,null,null);

            //Move to the first record in the Cursor
            if(cursor.moveToFirst())
            {
                //Get the drink details from the cursor
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);

                //Populate the hoakge name
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);

                //Populate the hokage description
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);

                //Populate the hokage image
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
            }
            cursor.close();
            db.close();
        }catch(SQLException e)
        {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}