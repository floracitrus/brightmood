package applications.brightmood;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import applications.brightmood.R;


/**
 * Created by Mitch on 2016-05-13.
 * Modified by Flora on 2017-12-02
 */
public class ViewListContents extends AppCompatActivity {

    DatabaseHelper myDB;
    ArrayList<Speech> speechList;
    ListView listView;
    Speech speech;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        myDB = new DatabaseHelper(this);
        speechList = new ArrayList<>();
        Cursor data = myDB.getListContents();
        int numRows = data.getCount();
        if (numRows == 0) {
            Toast.makeText(ViewListContents.this, "The Database is empty  :(.", Toast.LENGTH_LONG).show();
        } else {
            int i = 0;
            while (data.moveToNext()) {
                speech = new Speech(data.getString(1), data.getString(2), data.getString(3));
                speechList.add(i, speech);
                System.out.println(data.getString(1) + " " + data.getString(2) + " " + data.getString(3));
                System.out.println(speechList.get(i).getWord());
                i++;
            }
            ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(this, R.layout.list_adapter_view, speechList);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

        }

        Intent i = new Intent(getApplicationContext(), FitbitActivity.class);
        i.putExtra("key",speechList);

    }


}
