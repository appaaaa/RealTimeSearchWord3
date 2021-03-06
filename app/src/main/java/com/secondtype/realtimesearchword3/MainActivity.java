package com.secondtype.realtimesearchword3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class MainActivity extends AppCompatActivity {
    //test git
    // test 2 git
    //branch change
    Button refreshBtn;

    public static Boolean switchs = false;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SearchWord> myDataset;

    private LinearLayout loadingLayout;
    private TextView loadingTime;
    private TextView titleTime;

    private ArrayList<Reply> replyList;




    //////////// + tab 대신 임시 사용 //////////////
    private ImageButton firstButton;
    private ImageButton secondButton;
    /////////////////////////////////////////////

    ////////////////// + list all ////////////////
    LinearLayout linearLayoutListAll;

    TextView listAllText1;
    TextView listAllText2;
    TextView listAllText3;
    TextView listAllText4;
    TextView listAllText5;
    TextView listAllText6;
    TextView listAllText7;
    TextView listAllText8;
    TextView listAllText9;
    TextView listAllText10;
    TextView listAllText11;
    TextView listAllText12;
    TextView listAllText13;
    TextView listAllText14;
    TextView listAllText15;
    TextView listAllText16;
    TextView listAllText17;
    TextView listAllText18;
    TextView listAllText19;
    TextView listAllText20;
    ///////////////////////////////////////////////////

    /////// 데이터 수신중 클릭 방지 /////////
    LinearLayout contentLinearlayout;
    //////////////////////////////////////////

    private ArrayList<String> listAll;

    ////// + button naver, daum 추가
    Button naverBtn;
    Button daumBtn;
    ////////////////////////////////

    ////// + searchword 만 따로 받아오기. 리스트 표시용
    ArrayList<String> wordList;
    ////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////// + switch 초기화
        switchs = false;
        //////////////////////

        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseMessagingService.SUBSCRIBE);
        FirebaseInstanceId.getInstance().getToken();

        loadingLayout = (LinearLayout)findViewById(R.id.linearlayout_loading);
        loadingTime = (TextView)findViewById(R.id.textview_starttime);
        titleTime = (TextView)findViewById(R.id.textview_title);

        ///////////////tab 대신 임시사용 ///////////////////
        firstButton = (ImageButton) findViewById(R.id.button_first);
        firstButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mRecyclerView.setVisibility(View.VISIBLE);
                linearLayoutListAll.setVisibility(View.GONE);
            }
        });

        secondButton = (ImageButton) findViewById(R.id.button_second);
        secondButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mRecyclerView.setVisibility(View.GONE);
                linearLayoutListAll.setVisibility(View.VISIBLE);
            }
        });
        ////////////////////////////////////////////////////

        /////////////////// + listview list all /////////////
        linearLayoutListAll = (LinearLayout)findViewById(R.id.linearlayout_listall);

        listAllText1 = (TextView)findViewById(R.id.textview_listall_text1);
        listAllText2 = (TextView)findViewById(R.id.textview_listall_text2);
        listAllText3 = (TextView)findViewById(R.id.textview_listall_text3);
        listAllText4 = (TextView)findViewById(R.id.textview_listall_text4);
        listAllText5 = (TextView)findViewById(R.id.textview_listall_text5);
        listAllText6 = (TextView)findViewById(R.id.textview_listall_text6);
        listAllText7 = (TextView)findViewById(R.id.textview_listall_text7);
        listAllText8 = (TextView)findViewById(R.id.textview_listall_text8);
        listAllText9 = (TextView)findViewById(R.id.textview_listall_text9);
        listAllText10 = (TextView)findViewById(R.id.textview_listall_text10);
        listAllText11 = (TextView)findViewById(R.id.textview_listall_text11);
        listAllText12 = (TextView)findViewById(R.id.textview_listall_text12);
        listAllText13 = (TextView)findViewById(R.id.textview_listall_text13);
        listAllText14 = (TextView)findViewById(R.id.textview_listall_text14);
        listAllText15 = (TextView)findViewById(R.id.textview_listall_text15);
        listAllText16 = (TextView)findViewById(R.id.textview_listall_text16);
        listAllText17 = (TextView)findViewById(R.id.textview_listall_text17);
        listAllText18 = (TextView)findViewById(R.id.textview_listall_text18);
        listAllText19 = (TextView)findViewById(R.id.textview_listall_text19);
        listAllText20 = (TextView)findViewById(R.id.textview_listall_text20);
        ///////////////////////////////////////////////////////////////



        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<>();
        mAdapter = new SearchWordAdapter(getApplication(),myDataset, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);


        refreshBtn = (Button)findViewById(R.id.button_refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                myDataset.clear();
                mRecyclerView.setAdapter(mAdapter);
                GetDataParser mParser = new GetDataParser(); // Asynctask는 1회용이라서 매번 다시 생성해줘야함
                mParser.execute();

            }
        });

        ////// + button naver, daum 추가
        naverBtn = (Button)findViewById(R.id.button_naver);
        daumBtn = (Button)findViewById(R.id.button_daum);
        daumBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "열심히 카페인을 들이부으며 준비중입니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        /////////////////////////////////////////////

        ////// + searchword만 따로 받아오기
        wordList = new ArrayList<>();
        ///////////////////////////////////

        ////////// + 데이터 수신 중 막기 //////////
        //  contentLinearlayout = (LinearLayout)findViewById(R.id.linearlayout_contents);
        //  contentLinearlayout.setClickable(false);

        myDataset.clear();
        GetDataParser firstParser = new GetDataParser();
        firstParser.execute( );
    }


    public class GetDataParser extends AsyncTask<Void, SearchWord, ArrayList<SearchWord>> {

//        String url = "http://datalab.naver.com/keyword/realtimeList.naver?where=main";
        Integer position = 0;
        Page page = new Naver();

        @Override
        protected ArrayList<SearchWord> doInBackground(Void... voids) {
            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            ////// + list만 빨리 먼저 받아오기
            String[] titles = page.GetTitles();
            wordList.clear();
            for(int i = 0; i < titles.length; i++){
                wordList.add(titles[i]);
            }
            ListAllBinding();
            //////////////////////////////////
            for(int i = 0; i < titles.length; i++) {
                SearchWord searchWord = page.GetSearchWord(i);
                publishProgress(searchWord);
            }
            return tempList;
        }

        @Override
        protected void onPreExecute() {
            currentTimer();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(SearchWord... values) {
            myDataset.add(values[0]);
            mAdapter.notifyItemInserted(position++);
        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            switchs = true; //데이터를 다 가져왔으면 클릭 가능
        }
    }

    public void currentTimer(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm:ss");
        String formateDate = sdfNow.format(date);
        loadingTime.setText(formateDate);
        titleTime.setText(formateDate+" 지금이슈");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void ListAllBinding(){
        listAllText1.setText("1. " + wordList.get(0));
        listAllText2.setText("2. " + wordList.get(1));
        listAllText3.setText("3. " + wordList.get(2));
        listAllText4.setText("4. " + wordList.get(3));
        listAllText5.setText("5. " + wordList.get(4));
        listAllText6.setText("6. " + wordList.get(5));
        listAllText7.setText("7. " + wordList.get(6));
        listAllText8.setText("8. " + wordList.get(7));
        listAllText9.setText("9. " + wordList.get(8));
        listAllText10.setText("10. " + wordList.get(9));
        listAllText11.setText("11. " + wordList.get(10));
        listAllText12.setText("12. " + wordList.get(11));
        listAllText13.setText("13. " + wordList.get(12));
        listAllText14.setText("14. " + wordList.get(13));
        listAllText15.setText("15. " + wordList.get(14));
        listAllText16.setText("16. " + wordList.get(15));
        listAllText17.setText("17. " + wordList.get(16));
        listAllText18.setText("18. " + wordList.get(17));
        listAllText19.setText("19. " + wordList.get(18));
        listAllText20.setText("20. " + wordList.get(19));

        listAllText1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(0).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 0);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(1).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(2).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 2);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(3).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 3);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(4).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 4);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(5).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 5);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(6).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 6);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(7).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 7);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(8).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 8);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(9).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 9);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(10).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 10);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(11).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 11);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText13.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(12).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 12);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText14.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(13).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 13);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText15.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(14).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 14);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText16.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(15).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 15);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText17.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(16).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 16);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText18.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(17).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 17);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText19.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(18).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 18);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
        listAllText20.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailWeb.class);
                intent.putExtra("newsURL", myDataset.get(19).getNewsURL());
                intent.putExtra("mDataset", myDataset);
                intent.putExtra("allList", true);
                intent.putExtra("currentNumber", 19);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.notmove);
            }
        });
    }
}
