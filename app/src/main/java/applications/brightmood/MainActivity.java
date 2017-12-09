package applications.brightmood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import applications.brightmood.R;


public class MainActivity extends AppCompatActivity {

    EditText etPreset, etWord, etArea;
    Button btnAdd,btnView,btnGo;
    DatabaseHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPreset = (EditText) findViewById(R.id.etPreset);
        etWord = (EditText) findViewById(R.id.etWord);
        etArea = (EditText) findViewById(R.id.etArea);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);
        btnGo = (Button) findViewById(R.id.btnGo);

        myDB = new DatabaseHelper(this);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FitbitActivity.class);
                startActivity(intent);
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewListContents.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = etWord.getText().toString();
                String area = etArea.getText().toString();
                String preset = etPreset.getText().toString();
                if(word.length() != 0 && area.length() != 0){// && preset.length() != 0){
                    AddData(word, area, preset);
                    etWord.setText("");
                    etArea.setText("");
                    etPreset.setText("");
                }else{
                    Toast.makeText(MainActivity.this,"You must put something in the text field!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void AddData(String word,String area, String preset ){
        boolean insertData = myDB.addData(word,area,preset);

        if(insertData==true){
            Toast.makeText(MainActivity.this,"Successfully Entered Data!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this,"Something went wrong :(.",Toast.LENGTH_LONG).show();
        }
    }
}
