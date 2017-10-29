package co.miniforge.corey.mediatracker;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import co.miniforge.corey.mediatracker.model.MediaItem;

/**
 * This activity will display the contents of a media item and allow the user to update the contents
 * of the item. When the user clicks the save button, the activity should create an intent that goes
 * back to MyListActivity and puts the MediaItem into the intent (If you are stuck on that, read through
 * the code in MyListActivity)
 */
public class MediaItemDetailActivity extends AppCompatActivity {

    EditText name;
    EditText desc;
    EditText url;
    Button saveBttn;
    MediaItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_item_detail);
        item = intentExtra();

        if(item == null)
        {
            Toast.makeText(getApplicationContext(), "Media item null, returning", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(this, MyListActivity.class);
            startActivity(myIntent);
        }

        locateViews(); // set the view items (text and buttons)
        bindData(item); // set the view to match the data
        bindFunctionality(); // set functionality of button's within view
    }

    public MediaItem intentExtra()
    {
        Intent intent = getIntent();
        if(intent.hasExtra("mediaItem"))
        {
            try {
                String item = intent.getStringExtra("mediaItem");
                JSONObject jObj = new JSONObject(item);
                return new MediaItem(jObj);
            }
            catch (JSONException jEx)
            {
                // yay for exceptions.... jk
                jEx.printStackTrace();
                return null;
            }

        }
        else
        {
            // well we fucked up apparently... throw custom exception but eh lazy yet again
            return null;
        }
    }

    private void locateViews()
    {
        name = (EditText) findViewById(R.id.editName);
        desc = (EditText) findViewById(R.id.editDesc);
        url = (EditText) findViewById(R.id.editUrl);
        saveBttn = (Button) findViewById(R.id.saveBttn);
    }

    private void bindFunctionality()
    {
        saveBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // update the item from the text fields.
                item.title = name.getText().toString();
                item.description = desc.getText().toString();
                item.url = url.getText().toString();

                // start new intent.
                Intent intent = new Intent(getApplicationContext(), MyListActivity.class);
                String jSon = item.toJson().toString();
                intent.putExtra("mediaExtra", jSon); // ugh i wanna rename this, but dont know if i will screw up future assignments.
                startActivity(intent);
            }
        });
    }

    private void bindData(MediaItem item)
    {
        // woo bind that data!
        name.setText(item.title);
        desc.setText(item.description);
        url.setText(item.url);
    }
}
