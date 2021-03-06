package com.secondtype.realtimesearchword3;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TimerTask;

public class DetailWeb extends AppCompatActivity {

    WebView mWebView;
    WebSettings mWebSettings;

    String mURL;
    ArrayList<SearchWord> mData;

    Button mBackButton;
    TextView mTitleTextView;

    Integer currentNum;

    TextView word1;
    TextView word2;
    TextView word3;

    String w1, w2, w3;
    Integer n1, n2, n3;

    private TimerTask time;

    Animation animation;


    ///////////// + check case main or list all //////////
    Boolean checkCase = false;


    ////// + button share edit : title button
    Button shareBtn;
    Button editBtn;
    /////////////////////////////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_web);

        mBackButton = (Button)findViewById(R.id.button_back_detailweb);
        mTitleTextView = (TextView)findViewById(R.id.textview_title_detail);

        word1 = (TextView)findViewById(R.id.textview_word1);
        word2 = (TextView)findViewById(R.id.textview_word2);
        word3 = (TextView)findViewById(R.id.textview_word3);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);

        word1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Integer a = word1.getText().toString().indexOf(".");
                String word = word1.getText().toString().substring(a+2, word1.getText().toString().length());
                mURL = "https://search.naver.com/search.naver?where=nexearch&query=" + word + "&sm=top_lve&ie=utf8";
                openWeb(mURL);
                String titleText = "\"" + word + "\"" + " 관련 최신기사";
                mTitleTextView.setText(titleText);
            }
        });
        word2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Integer a = word2.getText().toString().indexOf(".");
                String word = word2.getText().toString().substring(a+2, word2.getText().toString().length());
                mURL = "https://search.naver.com/search.naver?where=nexearch&query=" + word + "&sm=top_lve&ie=utf8";
                openWeb(mURL);
                String titleText = "\"" + word + "\"" + " 관련 최신기사";
                mTitleTextView.setText(titleText);
            }
        });
        word3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Integer a = word3.getText().toString().indexOf(".");
                String word = word3.getText().toString().substring(a+2, word3.getText().toString().length());
                mURL = "https://search.naver.com/search.naver?where=nexearch&query=" + word + "&sm=top_lve&ie=utf8";
                openWeb(mURL);
                String titleText = "\"" + word + "\"" + " 관련 최신기사";
                mTitleTextView.setText(titleText);
            }
        });

        ////// + button share, edit : title button
        shareBtn = (Button)findViewById(R.id.button_share);
        shareBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "조금만 기다리세요! 곧 공유하게 해드릴게요!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        editBtn = (Button)findViewById(R.id.button_edit);
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "사실 이건 아직 별로 쓸데가 없어서... 원하는게 있으면 연락주세요! appaaaa@naver.com", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        ////////////////////////////////////////////
        mData = new ArrayList<>();



        Intent mIntent = getIntent();


        if(mIntent.getSerializableExtra("mDataset") != null) {

            mData.addAll((ArrayList<SearchWord>) getIntent().getSerializableExtra("mDataset"));
        }





        currentNum = mIntent.getIntExtra("currentNumber",1);
        if(currentNum != null) {
            Log.v("currentNum", currentNum.toString());
            String titleText = "\"" + mData.get(currentNum).getWord() + "\"" + " 관련 최신기사";
            mTitleTextView.setText(titleText);
        }

        if(currentNum>=1 && currentNum <=18) {
            n1 = currentNum;
            n2 = currentNum + 1;
            n3 = currentNum + 2;

            w1 = n1.toString() + ". " + mData.get(n1 - 1).getWord();
            w2 = n2.toString() + ". " + mData.get(n2 - 1).getWord();
            w3 = n3.toString() + ". " + mData.get(n3 - 1).getWord();

            word1.setText(w1);
            word2.setText(w2);
            word3.setText(w3);
        }else if(currentNum == 0){
            n1 = 20;
            n2 = currentNum+1;
            n3 = currentNum+2;

            w1 = n1.toString() + ". " + mData.get(n1 - 1).getWord();
            w2 = n2.toString() + ". " + mData.get(n2 - 1).getWord();
            w3 = n3.toString() + ". " + mData.get(n3 - 1).getWord();

            word1.setText(w1);
            word2.setText(w2);
            word3.setText(w3);
        }else if(currentNum == 19){
            n1 = currentNum;
            n2 = currentNum+1;
            n3 = 1;

            w1 = n1.toString() + ". " + mData.get(n1 - 1).getWord();
            w2 = n2.toString() + ". " + mData.get(n2 - 1).getWord();
            w3 = n3.toString() + ". " + mData.get(n3 - 1).getWord();

            word1.setText(w1);
            word2.setText(w2);
            word3.setText(w3);
        }

        if(mIntent.getStringExtra("newsURL") != null) {

            /// + check Case //////////////////
            checkCase = mIntent.getBooleanExtra("allList", false);
            if(checkCase){
                mURL = "https://search.naver.com/search.naver?where=nexearch&query=" + mData.get(currentNum).getWord() + "&sm=top_lve&ie=utf8";
            }else {
                mURL = mIntent.getStringExtra("newsURL");
                //mURL = "https://search.naver.com/search.naver?where=nexearch&query=" + mData.get(currentNum).getWord() + "&sm=top_lve&ie=utf8";
            }
            //////////////////////////////////
            Log.v("URL ", checkCase + mURL);

            openWeb(mURL);

        }else{
            Log.v("URL ", "null");
        }


        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, R.anim.notmove );
            }
        });

        startSubThread();


    }

    public void startSubThread(){
        MyRunnable myRunnable = new MyRunnable();
        Thread myThread = new Thread(myRunnable);
        myThread.setDaemon(true);
        myThread.start();
    }

    android.os.Handler mainHandler = new android.os.Handler()
    {
        public void handleMessage(Message msg)
        {
            if(msg.what == 0){
                if(currentNum != 19){
                    currentNum +=1;
                }
                else if(currentNum == 19){
                    currentNum = 0;
                }
                if(currentNum>=1 && currentNum <=18) {


                    n1 = currentNum;
                    n2 = currentNum + 1;
                    n3 = currentNum + 2;

                    w1 = n1.toString() + ". " + mData.get(n1 - 1).getWord();
                    w2 = n2.toString() + ". " + mData.get(n2 - 1).getWord();
                    w3 = n3.toString() + ". " + mData.get(n3 - 1).getWord();

                    word1.startAnimation(animation);
                    word1.setText(w1);

                    word2.startAnimation(animation);
                    word2.setText(w2);
                    word3.startAnimation(animation);
                    word3.setText(w3);
                }else if(currentNum == 0){
                    n1 = 20;
                    n2 = currentNum+1;
                    n3 = currentNum+2;

                    w1 = n1.toString() + ". " + mData.get(n1 - 1).getWord();
                    w2 = n2.toString() + ". " + mData.get(n2 - 1).getWord();
                    w3 = n3.toString() + ". " + mData.get(n3 - 1).getWord();

                    word1.setText(w1);
                    word2.setText(w2);
                    word3.setText(w3);
                }else if(currentNum == 19){
                    n1 = currentNum;
                    n2 = currentNum+1;
                    n3 = 1;

                    w1 = n1.toString() + ". " + mData.get(n1 - 1).getWord();
                    w2 = n2.toString() + ". " + mData.get(n2 - 1).getWord();
                    w3 = n3.toString() + ". " + mData.get(n3 - 1).getWord();

                    word1.setText(w1);
                    word2.setText(w2);
                    word3.setText(w3);
                }
            }
        };
    };

    public class MyRunnable implements Runnable{
        @Override
        public void run() {


            while(true){
                Message msg = Message.obtain();
                msg.what = 0;
                mainHandler.sendMessage(msg);

                try{
                    Thread.sleep(5000);
                }catch (Exception e){

                }
            }
        }
    }

    public void openWeb(String murl){
        mWebView = (WebView)findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient()); //클릭시 새창 안뜨게
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        //mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebView.loadUrl(murl);
    }
}
