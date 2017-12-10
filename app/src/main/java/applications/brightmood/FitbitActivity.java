package applications.brightmood;

/**
 * Created by florali on 3/12/17.
 */


import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;

import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

import applications.brightmood.R;


public class FitbitActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button sendButton;
    private SeekBar fitbitSlider;
    private Button speechBtn;
    private  ArrayList<Speech> speechList;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitbit);

        mTextMessage = (TextView) findViewById(R.id.message);
        fitbitSlider = (SeekBar) findViewById(R.id.seekBar);
        sendButton = (Button) findViewById(R.id.button10);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextMessage.setText("Sending HTTP");
                LogicFitbit fitbit = new LogicFitbit();
                StrictMode.ThreadPolicy tp = StrictMode.ThreadPolicy.LAX;
                StrictMode.setThreadPolicy(tp);

                String msg = fitbit.logicFitbit(mTextMessage, fitbitSlider.getProgress());
                mTextMessage.setText("Heart Rate got");
                GatewayHandler handler = new GatewayHandler(mTextMessage);
                handler.execute(msg);
            }
        });

        speechBtn = (Button) findViewById(R.id.speechBtn);
        speechBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSpeechToText();
            }
        });
        speechList = new ArrayList<>();
        Speech speech;
        myDB = new DatabaseHelper(this);
        Cursor data = myDB.getListContents();
        int numRows = data.getCount();
        if (numRows == 0) {
            Toast.makeText(FitbitActivity.this, "The Database is empty  :(.", Toast.LENGTH_LONG).show();
        } else {
            int i = 0;
            while (data.moveToNext()) {
                speech = new Speech(data.getString(1), data.getString(2), data.getString(3),data.getString(4));
                speechList.add(i, speech);
                i++;
            }
        }

        //speechList =(ArrayList<Speech>) getIntent().getSerializableExtra("key");

    }

    private final int SPEECH_RECOGNITION_CODE = 1;

    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak something...");
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback for speech recognition activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    chooseLighting(text);
                }
                break;
            }

        }
    }

    private void chooseLighting(String text) {
        ArrayList<String> wordsRaw = new ArrayList<>(Arrays.asList(text.split(" ")));
        ArrayList<String> words = new ArrayList<>();
        for (String word : wordsRaw) {
            words.add(word.toLowerCase());
        }
        GatewayHandler handler = new GatewayHandler(mTextMessage);
        // ArrayList<Speech> speechList = new ArrayList<>();
        //pass in the value that speechList has

        String p1 = "http://192.168.1.32/SetDyNet.cgi?a=";
        String tempMessage = null;
//
//            for(Speech w: speechList) {
//                if (words.contains("hello") && words.contains("hello")) {
//
//                    String p2 = w.getArea();
//                    String p3 = w.getPreset();
//                    String p4 = w.getDomain();
//
//                    String temp = "hello detected";
//                //String temp = w.getWord().concat("speechList on detected");
//                    mTextMessage.setText(temp);
//                    if(p4==null){
//                        tempMessage = p1+p2+"&p="+p3;
//                    //handler.execute(tempMessage);
//                    }
//                    if(p4!=null){
//                        String p5 = "http://";
//                        String p6 = "/SetDyNet.cgi?a=";
//                        tempMessage = p5+p4+p6+p2+"&p="+p3;
//                    //handler.execute(tempMessage);
//                    }
//                    break;
//                }
//                else if (words.contains(w.getWord()) && words.contains("off")) {
//
////                String p2 = w.getArea();
////                String p3 = "&p=4";
////                handler.execute(p1 + p2 + p3);
//                    //handler.execute("http://192.168.1.32/SetDyNet.cgi?a=2&p=3");
//                    String temp = w.getWord().concat("speechList off detected");
//                    mTextMessage.setText(temp);
//                    break;
//                }
//                else{
//                    mTextMessage.setText("jump out the speech list");
//                }
//            }
//            if(tempMessage!=null){ handler.execute(tempMessage);
//            mTextMessage.setText("speech detected");}

        //test using p1
        if (words.contains("dim")) {
            handler.execute(p1 + "2&l=50&f=1");
            mTextMessage.setText("dim detected");
        } else if (words.contains("white")) {
            handler.execute(p1 + "2&p=1");
            mTextMessage.setText("white detected");
        } else if (words.contains("blue") || words.contains("beach")) {
            handler.execute(p1 + "2&p=5");
            mTextMessage.setText("blue detected");

            //leave for test p1 work or not
        } else if (words.contains("red") || words.contains("angry")) {
            handler.execute(p1+"2&p=3");
            mTextMessage.setText("red detected");
        } else if (words.contains("green") || words.contains("park")) {
            handler.execute(p1+"2&p=2");
            mTextMessage.setText("green detected");
        } else if (words.contains("applause")) {
            handler.execute(p1+"10&p=3");
            mTextMessage.setText("applause detected");
        } else if (words.contains("party")) {
            handler.execute(p1+"10&p=4");
            mTextMessage.setText("party detected");
        } else if (words.contains("relax")) {
            handler.execute(p1+"10&p=5");
            mTextMessage.setText("relax detected");
        } else {
            Speech temp=null;
            int flag = 0;
            Iterator<Speech> itr = speechList.iterator();
            while (itr.hasNext()) {
                Speech element = itr.next();
                String w = element.getWord();
                if (words.contains(w)) {
                    flag = 1;
                    temp = element;
                    break;
                }
            }
            if (flag == 1) {
                String a = temp.getArea();
                String d = temp.getDomain();
                String p = temp.getPreset();
                if(d==null){
                    tempMessage = p1+a+"&p="+p;
                }else{
                    String ht = "http://";
                    String dy = "/SetDyNet.cgi?a=";
                    tempMessage = ht+d+dy+a+"&p="+p;
                    Log.e("debug",tempMessage);
                }
                handler.execute(tempMessage);
                mTextMessage.setText("string from speechList detected");
            } else if (words.contains("on")) {
                handler.execute("http://192.168.1.32/SetDyNet.cgi?a=2&p=1");
                mTextMessage.setText("on detected");
            } else if (words.contains("quit") || words.contains("exit") || words.contains("night") || words.contains("stop") || words.contains("off") || words.contains("cancel")) {
                handler.execute("http://192.168.1.32/SetDyNet.cgi?a=10&p=7");
                mTextMessage.setText("stop/off/cancel detected");
            } else {
                mTextMessage.setText(text);
            }

        }

    }
}







//
//        if (words.contains("living") && words.contains("on")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=7&p=1");
//            mTextMessage.setText("living on detected");
//        } else if (words.contains("living") && words.contains("off")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=7&p=4");
//            mTextMessage.setText("living off detected");
//        } else if (words.contains("dining") && words.contains("on")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=8&p=1");
//            mTextMessage.setText("dining on detected");
//        } else if (words.contains("dining") && words.contains("off")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=8&p=4");
//            mTextMessage.setText("dining off detected");
//        } else if (words.contains("master") && words.contains("bedroom") && words.contains("on")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=17&p=1");
//            mTextMessage.setText("Master bedroom on detected");
//        } else if (words.contains("master") && words.contains("bedroom") && words.contains("off")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=17&p=4");
//            mTextMessage.setText("Master bedroom off detected");
//        } else if (words.contains("kitchen") && words.contains("on")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=9&p=1");
//            mTextMessage.setText("kitchen on detected");
//        } else if (words.contains("kitchen") && words.contains("off")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=9&p=4");
//            mTextMessage.setText("kitchen off detected");
//        } else if ((words.contains("garage") && words.contains("on")) || words.contains("car") || words.contains("leaving")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=19&p=1");
//            mTextMessage.setText("garage on detected");
//        } else if (words.contains("garage") && words.contains("off")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=19&p=4");
//            mTextMessage.setText("garage off detected");
//        } else if (words.contains("white")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=2&p=1");
//            mTextMessage.setText("white detected");
//        } else if (words.contains("blue") || words.contains("beach")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=2&p=5");
//            mTextMessage.setText("blue detected");
//        } else if (words.contains("red") || words.contains("angry")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=2&p=3");
//            mTextMessage.setText("red detected");
//        } else if (words.contains("green") || words.contains("park")) {
//            handler.execute("http://192.168.1.32/SetDyNet.cgi?a=2&p=2");
//            mTextMessage.setText("green detected");
//        }