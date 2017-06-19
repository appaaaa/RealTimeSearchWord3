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

        FirebaseMessaging.getInstance().subscribeToTopic("news");
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


        refreshBtn = (Button)findViewById(R.id.button_refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                GetDataParser mParser = new GetDataParser(); // Asynctask는 1회용이라서 매번 다시 생성해줘야함
                mParser.execute();
                GetDataParser2 firstParser2 = new GetDataParser2();
                firstParser2.execute( );
                GetDataParser3 firstParser3 = new GetDataParser3();
                firstParser3.execute( );
                GetDataParser4 firstParser4 = new GetDataParser4();
                firstParser4.execute( );
                GetDataParser5 firstParser5 = new GetDataParser5();
                firstParser5.execute( );
                GetDataParser6 firstParser6 = new GetDataParser6();
                firstParser6.execute( );
                GetDataParser7 firstParser7 = new GetDataParser7();
                firstParser7.execute( );
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

        GetDataParser firstParser = new GetDataParser();
        firstParser.execute( );
        GetDataParser2 firstParser2 = new GetDataParser2();
        firstParser2.execute( );
        GetDataParser3 firstParser3 = new GetDataParser3();
        firstParser3.execute( );
        GetDataParser4 firstParser4 = new GetDataParser4();
        firstParser4.execute( );
        GetDataParser5 firstParser5 = new GetDataParser5();
        firstParser5.execute( );
        GetDataParser6 firstParser6 = new GetDataParser6();
        firstParser6.execute( );
        GetDataParser7 firstParser7 = new GetDataParser7();
        firstParser7.execute( );


    }


    public class GetDataParser extends AsyncTask<Void, Void, ArrayList<SearchWord>> {


        String url = "http://datalab.naver.com/keyword/realtimeList.naver?where=main";




        @Override
        protected ArrayList<SearchWord> doInBackground(Void... voids) {

            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument = Jsoup.connect(url).get();
//                Elements mElements = mDocument.select("ol#realrank > li > a");

                Elements mElements = mDocument.select("div.select_date ul li");

                ////// + list만 빨리 먼저 받아오기
                wordList.clear();
                for(int i = 0; i < 20; i++){
                    wordList.add(mElements.get(i).select("span.title").text());
                }
                ListAllBinding();
                //////////////////////////////////
                for(int i = 0; i < 3; i++) {
                    SearchWord searchWord = new SearchWord();
                    //Log.v("title test : ", elements.get(i).select("span.ell").text());
                    searchWord.setNumber(Integer.toString(i + 1));
//                    searchWord.setWord(mElements.get(i).select("span.ell").text());
                    searchWord.setWord(mElements.get(i).select("span.title").text());
//                    if (mElements.get(i).select("span.tx").text().equals("상승")) {
//                        searchWord.setArrow("상승");
//                        searchWord.setRanking(mElements.get(i).select("span.rk").text());
//
//                    } else {
//                        searchWord.setArrow("NEW");
//                    }
//
//                    Log.v("test arrow : ", mElements.get(i).select("span.tx").text());



                    String url = "https://search.naver.com/search.naver?where=nexearch&query=" + searchWord.getWord() + "&sm=top_lve&ie=utf8";

                    try {
                        Document mDocument2 = Jsoup.connect(url).get();
                        Elements newsElement = mDocument2.select("li#sp_nws_all1");
                        Elements newsElement2 = mDocument2.select("div.news ul li");
                        Log.v("newssection","test");
                        Log.v("newssection",newsElement2.toString());
                        Log.v("newssection",newsElement2.text());
                        Log.v("newssection",Integer.toString(newsElement2.size()));


                        if (newsElement != null) {
                            String title = "BASIC";
                            String title2 = "BASIC";
                            String title3 = "BASIC";
                            String title4 = "BASIC";
                            title = newsElement.select("dl dt a").text();



                            //Log.v("title2", title2);
                            /*
                            title2 = newsElement2.first().select("dl dt a").text();
                            title2 = newsElement2.next().get(1).select("dl dt a").text();
                            title3 = newsElement2.next().get(2).select("dl dt a").text();
                            title4 = newsElement2.next().select("dl dt a").text();

                            Log.v("title2", "title2" + newsElement2.text());
                            Log.v("title2","title3" +  title3);
                            Log.v("title2","title4" +  title4);*/

                            Elements imageElements = newsElement.select("div.thumb a img");
                            String ImageUrl = imageElements.attr("src");

                            Elements urlElements = newsElement.select("div.thumb a");

                            String newsURL = urlElements.attr("href");



                            if(title.equals("BASIC")) {

                                searchWord.setNewsTitle("관련 뉴스 X");
                            }
                            else{
                                searchWord.setNewsTitle(title);
                                searchWord.setNewsURL(newsURL);
                            }
                            searchWord.setNewsImage(ImageUrl.substring(0, ImageUrl.length()-28));
                            //Log.v("newsTitle ", newsTitle.text());

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String replysUrl = "https://search.naver.com/search.naver?where=realtime&query=" + searchWord.getWord() + "&best=1";

                    try{
                        Document mDocument3 = Jsoup.connect(replysUrl).get();
                        Elements replyElements = mDocument3.select("ul.type01 li");

                        Log.v("replay test : count", Integer.toString(replyElements.size()));

                        for(int j = 0; j < replyElements.size(); j++){
                            Reply mReply = new Reply();

                            mReply.name = replyElements.get(j).select(".user_name").text();
                            Log.v("testreply name", mReply.name + " " + j);

                            if(!replyElements.get(j).select(".sub_retweet").text().isEmpty()){
                                mReply.text = replyElements.get(j).select(".cmmt").text();
                                mReply.time = replyElements.get(j).select(".time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_retweet").text();
                                mReply.count2 = replyElements.get(j).select(".sub_interest").text();
                                mReply.count3 = "  ";
                                Log.v("testreply sub_ret",replyElements.get(j).select(".sub_retweet").text() + " " + j );
                                Log.v("testreply twitt",replyElements.get(j).select(".time").text() + " " + j);
                                Log.v("testreply count1",replyElements.get(j).select(".sub_retweet").text() + " " + j);
                                Log.v("testreply count2",replyElements.get(j).select(".sub_interest").text() + " " + j);


                            }

                            else if(!replyElements.get(j).select(".sub_reply").text().isEmpty()){
                                Log.v("test reply2 start", "reply2 start");
                                mReply.text = replyElements.get(j).select(".txt_link").text();
                                mReply.time = replyElements.get(j).select(".sub_time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_reply").text();
                                mReply.count2 = replyElements.get(j).select(".sub_like").text();
                                mReply.count3 = replyElements.get(j).select(".sub_dis").text();
                                Log.v("testreply2 text", replyElements.get(j).select(".txt_link").text());
                                Log.v("testreply2 time", mReply.time);
                                Log.v("testreply2 count1", mReply.count1);
                                Log.v("testreply2 count2", mReply.count2);
                                Log.v("testreply2 count3", mReply.count3);
                            }

                            searchWord.getReplyArrayList().add(mReply);

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    tempList.add(searchWord);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return tempList;
        }

        @Override
        protected void onPreExecute() {

            loadingLayout.setVisibility(View.VISIBLE);

            currentTimer();

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            myDataset.clear();
            myDataset.addAll(tempList);

            mRecyclerView.setAdapter(mAdapter);

            loadingLayout.setVisibility(View.GONE);


        }
    }

    public class GetDataParser2 extends AsyncTask<Void, Void, ArrayList<SearchWord>> {


        String url = "http://datalab.naver.com/keyword/realtimeList.naver?where=main";




        @Override
        protected ArrayList<SearchWord> doInBackground(Void... voids) {

            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument = Jsoup.connect(url).get();
//                Elements mElements = mDocument.select("ol#realrank > li > a");

                Elements mElements = mDocument.select("div.select_date ul li");
                for(int i = 3; i < 6; i++) {
                    SearchWord searchWord = new SearchWord();
                    //Log.v("title test : ", elements.get(i).select("span.ell").text());
                    searchWord.setNumber(Integer.toString(i + 1));
//                    searchWord.setWord(mElements.get(i).select("span.ell").text());
                    searchWord.setWord((mElements.get(i).select("span.title").text()));
//                    if (mElements.get(i).select("span.tx").text().equals("상승")) {
//                        searchWord.setArrow("상승");
//                        searchWord.setRanking(mElements.get(i).select("span.rk").text());
//
//                    } else {
//                        searchWord.setArrow("NEW");
//                    }
//
//                    Log.v("test arrow : ", mElements.get(i).select("span.tx").text());



                    String url = "https://search.naver.com/search.naver?where=nexearch&query=" + searchWord.getWord() + "&sm=top_lve&ie=utf8";

                    try {
                        Document mDocument2 = Jsoup.connect(url).get();
                        Elements newsElement = mDocument2.select("li#sp_nws_all1");

                        if (newsElement != null) {
                            String title = "BASIC";
                            title = newsElement.select("dl dt a").text();

                            Elements imageElements = newsElement.select("div.thumb a img");
                            String ImageUrl = imageElements.attr("src");

                            Elements urlElements = newsElement.select("div.thumb a");

                            String newsURL = urlElements.attr("href");



                            if(title.equals("BASIC")) {

                                searchWord.setNewsTitle("관련 뉴스 X");
                            }
                            else{
                                searchWord.setNewsTitle(title);
                                searchWord.setNewsURL(newsURL);
                            }
                            searchWord.setNewsImage(ImageUrl.substring(0, ImageUrl.length()-28));
                            //Log.v("newsTitle ", newsTitle.text());

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String replysUrl = "https://search.naver.com/search.naver?where=realtime&query=" + searchWord.getWord() + "&best=1";

                    try{
                        Document mDocument3 = Jsoup.connect(replysUrl).get();
                        Elements replyElements = mDocument3.select("ul.type01 li");

                        Log.v("replay test : count", Integer.toString(replyElements.size()));

                        for(int j = 0; j < replyElements.size(); j++){
                            Reply mReply = new Reply();

                            mReply.name = replyElements.get(j).select(".user_name").text();
                            Log.v("testreply name", mReply.name + " " + j);

                            if(!replyElements.get(j).select(".sub_retweet").text().isEmpty()){
                                mReply.text = replyElements.get(j).select(".cmmt").text();
                                mReply.time = replyElements.get(j).select(".time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_retweet").text();
                                mReply.count2 = replyElements.get(j).select(".sub_interest").text();
                                mReply.count3 = "  ";
                                Log.v("testreply sub_ret",replyElements.get(j).select(".sub_retweet").text() + " " + j );
                                Log.v("testreply twitt",replyElements.get(j).select(".time").text() + " " + j);
                                Log.v("testreply count1",replyElements.get(j).select(".sub_retweet").text() + " " + j);
                                Log.v("testreply count2",replyElements.get(j).select(".sub_interest").text() + " " + j);


                            }

                            else if(!replyElements.get(j).select(".sub_reply").text().isEmpty()){
                                Log.v("test reply2 start", "reply2 start");
                                mReply.text = replyElements.get(j).select(".txt_link").text();
                                mReply.time = replyElements.get(j).select(".sub_time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_reply").text();
                                mReply.count2 = replyElements.get(j).select(".sub_like").text();
                                mReply.count3 = replyElements.get(j).select(".sub_dis").text();
                                Log.v("testreply2 text", replyElements.get(j).select(".txt_link").text());
                                Log.v("testreply2 time", mReply.time);
                                Log.v("testreply2 count1", mReply.count1);
                                Log.v("testreply2 count2", mReply.count2);
                                Log.v("testreply2 count3", mReply.count3);
                            }

                            searchWord.getReplyArrayList().add(mReply);

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    tempList.add(searchWord);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return tempList;
        }

        @Override
        protected void onPreExecute() {

         //   mProgressBar.setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            //myDataset.clear();
            myDataset.addAll(tempList);

            // mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

      //      mProgressBar.setVisibility(View.GONE);
        }
    }

    public class GetDataParser3 extends AsyncTask<Void, Void, ArrayList<SearchWord>> {


        String url = "http://datalab.naver.com/keyword/realtimeList.naver?where=main";

        @Override
        protected ArrayList<SearchWord> doInBackground(Void... voids) {

            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument = Jsoup.connect(url).get();
//                Elements mElements = mDocument.select("ol#realrank > li > a");

                Elements mElements = mDocument.select("div.select_date ul li");
                for(int i = 6; i < 9; i++) {
                    SearchWord searchWord = new SearchWord();
                    //Log.v("title test : ", elements.get(i).select("span.ell").text());
                    searchWord.setNumber(Integer.toString(i + 1));
//                    searchWord.setWord(mElements.get(i).select("span.ell").text());
                    searchWord.setWord((mElements.get(i).select("span.title").text()));
//                    if (mElements.get(i).select("span.tx").text().equals("상승")) {
//                        searchWord.setArrow("상승");
//                        searchWord.setRanking(mElements.get(i).select("span.rk").text());
//
//                    } else {
//                        searchWord.setArrow("NEW");
//                    }
//
//                    Log.v("test arrow : ", mElements.get(i).select("span.tx").text());



                    String url = "https://search.naver.com/search.naver?where=nexearch&query=" + searchWord.getWord() + "&sm=top_lve&ie=utf8";

                    try {
                        Document mDocument2 = Jsoup.connect(url).get();
                        Elements newsElement = mDocument2.select("li#sp_nws_all1");

                        if (newsElement != null) {
                            String title = "BASIC";
                            title = newsElement.select("dl dt a").text();

                            Elements imageElements = newsElement.select("div.thumb a img");
                            String ImageUrl = imageElements.attr("src");

                            Elements urlElements = newsElement.select("div.thumb a");

                            String newsURL = urlElements.attr("href");



                            if(title.equals("BASIC")) {

                                searchWord.setNewsTitle("관련 뉴스 X");
                            }
                            else{
                                searchWord.setNewsTitle(title);
                                searchWord.setNewsURL(newsURL);
                            }
                            searchWord.setNewsImage(ImageUrl.substring(0, ImageUrl.length()-28));
                            //Log.v("newsTitle ", newsTitle.text());

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String replysUrl = "https://search.naver.com/search.naver?where=realtime&query=" + searchWord.getWord() + "&best=1";

                    try{
                        Document mDocument3 = Jsoup.connect(replysUrl).get();
                        Elements replyElements = mDocument3.select("ul.type01 li");

                        Log.v("replay test : count", Integer.toString(replyElements.size()));

                        for(int j = 0; j < replyElements.size(); j++){
                            Reply mReply = new Reply();

                            mReply.name = replyElements.get(j).select(".user_name").text();
                            Log.v("testreply name", mReply.name + " " + j);

                            if(!replyElements.get(j).select(".sub_retweet").text().isEmpty()){
                                mReply.text = replyElements.get(j).select(".cmmt").text();
                                mReply.time = replyElements.get(j).select(".time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_retweet").text();
                                mReply.count2 = replyElements.get(j).select(".sub_interest").text();
                                mReply.count3 = "  ";
                                Log.v("testreply sub_ret",replyElements.get(j).select(".sub_retweet").text() + " " + j );
                                Log.v("testreply twitt",replyElements.get(j).select(".time").text() + " " + j);
                                Log.v("testreply count1",replyElements.get(j).select(".sub_retweet").text() + " " + j);
                                Log.v("testreply count2",replyElements.get(j).select(".sub_interest").text() + " " + j);


                            }

                            else if(!replyElements.get(j).select(".sub_reply").text().isEmpty()){
                                Log.v("test reply2 start", "reply2 start");
                                mReply.text = replyElements.get(j).select(".txt_link").text();
                                mReply.time = replyElements.get(j).select(".sub_time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_reply").text();
                                mReply.count2 = replyElements.get(j).select(".sub_like").text();
                                mReply.count3 = replyElements.get(j).select(".sub_dis").text();
                                Log.v("testreply2 text", replyElements.get(j).select(".txt_link").text());
                                Log.v("testreply2 time", mReply.time);
                                Log.v("testreply2 count1", mReply.count1);
                                Log.v("testreply2 count2", mReply.count2);
                                Log.v("testreply2 count3", mReply.count3);
                            }

                            searchWord.getReplyArrayList().add(mReply);

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    tempList.add(searchWord);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return tempList;
        }

        @Override
        protected void onPreExecute() {

       //     mProgressBar.setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            //myDataset.clear();
            myDataset.addAll(tempList);

            // mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

    //        mProgressBar.setVisibility(View.GONE);
        }
    }

    public class GetDataParser4 extends AsyncTask<Void, Void, ArrayList<SearchWord>> {


        String url = "http://datalab.naver.com/keyword/realtimeList.naver?where=main";

        @Override
        protected ArrayList<SearchWord> doInBackground(Void... voids) {

            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument = Jsoup.connect(url).get();
//                Elements mElements = mDocument.select("ol#realrank > li > a");

                Elements mElements = mDocument.select("div.select_date ul li");
                for(int i = 9; i < 12; i++) {
                    SearchWord searchWord = new SearchWord();
                    //Log.v("title test : ", elements.get(i).select("span.ell").text());
                    searchWord.setNumber(Integer.toString(i + 1));
//                    searchWord.setWord(mElements.get(i).select("span.ell").text());
                    searchWord.setWord((mElements.get(i).select("span.title").text()));
//                    if (mElements.get(i).select("span.tx").text().equals("상승")) {
//                        searchWord.setArrow("상승");
//                        searchWord.setRanking(mElements.get(i).select("span.rk").text());
//
//                    } else {
//                        searchWord.setArrow("NEW");
//                    }
//
//                    Log.v("test arrow : ", mElements.get(i).select("span.tx").text());



                    String url = "https://search.naver.com/search.naver?where=nexearch&query=" + searchWord.getWord() + "&sm=top_lve&ie=utf8";

                    try {
                        Document mDocument2 = Jsoup.connect(url).get();
                        Elements newsElement = mDocument2.select("li#sp_nws_all1");

                        if (newsElement != null) {
                            String title = "BASIC";
                            title = newsElement.select("dl dt a").text();

                            Elements imageElements = newsElement.select("div.thumb a img");
                            String ImageUrl = imageElements.attr("src");

                            Elements urlElements = newsElement.select("div.thumb a");

                            String newsURL = urlElements.attr("href");



                            if(title.equals("BASIC")) {

                                searchWord.setNewsTitle("관련 뉴스 X");
                            }
                            else{
                                searchWord.setNewsTitle(title);
                                searchWord.setNewsURL(newsURL);
                            }
                            searchWord.setNewsImage(ImageUrl.substring(0, ImageUrl.length()-28));
                            //Log.v("newsTitle ", newsTitle.text());

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String replysUrl = "https://search.naver.com/search.naver?where=realtime&query=" + searchWord.getWord() + "&best=1";

                    try{
                        Document mDocument3 = Jsoup.connect(replysUrl).get();
                        Elements replyElements = mDocument3.select("ul.type01 li");

                        Log.v("replay test : count", Integer.toString(replyElements.size()));

                        for(int j = 0; j < replyElements.size(); j++){
                            Reply mReply = new Reply();

                            mReply.name = replyElements.get(j).select(".user_name").text();
                            Log.v("testreply name", mReply.name + " " + j);

                            if(!replyElements.get(j).select(".sub_retweet").text().isEmpty()){
                                mReply.text = replyElements.get(j).select(".cmmt").text();
                                mReply.time = replyElements.get(j).select(".time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_retweet").text();
                                mReply.count2 = replyElements.get(j).select(".sub_interest").text();
                                mReply.count3 = "  ";
                                Log.v("testreply sub_ret",replyElements.get(j).select(".sub_retweet").text() + " " + j );
                                Log.v("testreply twitt",replyElements.get(j).select(".time").text() + " " + j);
                                Log.v("testreply count1",replyElements.get(j).select(".sub_retweet").text() + " " + j);
                                Log.v("testreply count2",replyElements.get(j).select(".sub_interest").text() + " " + j);


                            }

                            else if(!replyElements.get(j).select(".sub_reply").text().isEmpty()){
                                Log.v("test reply2 start", "reply2 start");
                                mReply.text = replyElements.get(j).select(".txt_link").text();
                                mReply.time = replyElements.get(j).select(".sub_time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_reply").text();
                                mReply.count2 = replyElements.get(j).select(".sub_like").text();
                                mReply.count3 = replyElements.get(j).select(".sub_dis").text();
                                Log.v("testreply2 text", replyElements.get(j).select(".txt_link").text());
                                Log.v("testreply2 time", mReply.time);
                                Log.v("testreply2 count1", mReply.count1);
                                Log.v("testreply2 count2", mReply.count2);
                                Log.v("testreply2 count3", mReply.count3);
                            }

                            searchWord.getReplyArrayList().add(mReply);

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    tempList.add(searchWord);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return tempList;
        }

        @Override
        protected void onPreExecute() {

            //     mProgressBar.setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            //myDataset.clear();
            myDataset.addAll(tempList);

            // mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            //        mProgressBar.setVisibility(View.GONE);
        }
    }

    public class GetDataParser5 extends AsyncTask<Void, Void, ArrayList<SearchWord>> {


        String url = "http://datalab.naver.com/keyword/realtimeList.naver?where=main";

        @Override
        protected ArrayList<SearchWord> doInBackground(Void... voids) {

            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument = Jsoup.connect(url).get();
//                Elements mElements = mDocument.select("ol#realrank > li > a");

                Elements mElements = mDocument.select("div.select_date ul li");
                for(int i = 12; i < 15; i++) {
                    SearchWord searchWord = new SearchWord();
                    //Log.v("title test : ", elements.get(i).select("span.ell").text());
                    searchWord.setNumber(Integer.toString(i + 1));
//                    searchWord.setWord(mElements.get(i).select("span.ell").text());
                    searchWord.setWord((mElements.get(i).select("span.title").text()));
//                    if (mElements.get(i).select("span.tx").text().equals("상승")) {
//                        searchWord.setArrow("상승");
//                        searchWord.setRanking(mElements.get(i).select("span.rk").text());
//
//                    } else {
//                        searchWord.setArrow("NEW");
//                    }
//
//                    Log.v("test arrow : ", mElements.get(i).select("span.tx").text());



                    String url = "https://search.naver.com/search.naver?where=nexearch&query=" + searchWord.getWord() + "&sm=top_lve&ie=utf8";

                    try {
                        Document mDocument2 = Jsoup.connect(url).get();
                        Elements newsElement = mDocument2.select("li#sp_nws_all1");

                        if (newsElement != null) {
                            String title = "BASIC";
                            title = newsElement.select("dl dt a").text();

                            Elements imageElements = newsElement.select("div.thumb a img");
                            String ImageUrl = imageElements.attr("src");

                            Elements urlElements = newsElement.select("div.thumb a");

                            String newsURL = urlElements.attr("href");



                            if(title.equals("BASIC")) {

                                searchWord.setNewsTitle("관련 뉴스 X");
                            }
                            else{
                                searchWord.setNewsTitle(title);
                                searchWord.setNewsURL(newsURL);
                            }
                            searchWord.setNewsImage(ImageUrl.substring(0, ImageUrl.length()-28));
                            //Log.v("newsTitle ", newsTitle.text());

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String replysUrl = "https://search.naver.com/search.naver?where=realtime&query=" + searchWord.getWord() + "&best=1";

                    try{
                        Document mDocument3 = Jsoup.connect(replysUrl).get();
                        Elements replyElements = mDocument3.select("ul.type01 li");

                        Log.v("replay test : count", Integer.toString(replyElements.size()));

                        for(int j = 0; j < replyElements.size(); j++){
                            Reply mReply = new Reply();

                            mReply.name = replyElements.get(j).select(".user_name").text();
                            Log.v("testreply name", mReply.name + " " + j);

                            if(!replyElements.get(j).select(".sub_retweet").text().isEmpty()){
                                mReply.text = replyElements.get(j).select(".cmmt").text();
                                mReply.time = replyElements.get(j).select(".time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_retweet").text();
                                mReply.count2 = replyElements.get(j).select(".sub_interest").text();
                                mReply.count3 = "  ";
                                Log.v("testreply sub_ret",replyElements.get(j).select(".sub_retweet").text() + " " + j );
                                Log.v("testreply twitt",replyElements.get(j).select(".time").text() + " " + j);
                                Log.v("testreply count1",replyElements.get(j).select(".sub_retweet").text() + " " + j);
                                Log.v("testreply count2",replyElements.get(j).select(".sub_interest").text() + " " + j);


                            }

                            else if(!replyElements.get(j).select(".sub_reply").text().isEmpty()){
                                Log.v("test reply2 start", "reply2 start");
                                mReply.text = replyElements.get(j).select(".txt_link").text();
                                mReply.time = replyElements.get(j).select(".sub_time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_reply").text();
                                mReply.count2 = replyElements.get(j).select(".sub_like").text();
                                mReply.count3 = replyElements.get(j).select(".sub_dis").text();
                                Log.v("testreply2 text", replyElements.get(j).select(".txt_link").text());
                                Log.v("testreply2 time", mReply.time);
                                Log.v("testreply2 count1", mReply.count1);
                                Log.v("testreply2 count2", mReply.count2);
                                Log.v("testreply2 count3", mReply.count3);
                            }

                            searchWord.getReplyArrayList().add(mReply);

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    tempList.add(searchWord);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return tempList;
        }

        @Override
        protected void onPreExecute() {

            //     mProgressBar.setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            //myDataset.clear();
            myDataset.addAll(tempList);

            // mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            //        mProgressBar.setVisibility(View.GONE);
        }
    }

    public class GetDataParser6 extends AsyncTask<Void, Void, ArrayList<SearchWord>> {


        String url = "http://datalab.naver.com/keyword/realtimeList.naver?where=main";

        @Override
        protected ArrayList<SearchWord> doInBackground(Void... voids) {

            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument = Jsoup.connect(url).get();
//                Elements mElements = mDocument.select("ol#realrank > li > a");

                Elements mElements = mDocument.select("div.select_date ul li");
                for(int i = 15; i < 18; i++) {
                    SearchWord searchWord = new SearchWord();
                    //Log.v("title test : ", elements.get(i).select("span.ell").text());
                    searchWord.setNumber(Integer.toString(i + 1));
//                    searchWord.setWord(mElements.get(i).select("span.ell").text());
                    searchWord.setWord((mElements.get(i).select("span.title").text()));
//                    if (mElements.get(i).select("span.tx").text().equals("상승")) {
//                        searchWord.setArrow("상승");
//                        searchWord.setRanking(mElements.get(i).select("span.rk").text());
//
//                    } else {
//                        searchWord.setArrow("NEW");
//                    }
//
//                    Log.v("test arrow : ", mElements.get(i).select("span.tx").text());



                    String url = "https://search.naver.com/search.naver?where=nexearch&query=" + searchWord.getWord() + "&sm=top_lve&ie=utf8";

                    try {
                        Document mDocument2 = Jsoup.connect(url).get();
                        Elements newsElement = mDocument2.select("li#sp_nws_all1");

                        if (newsElement != null) {
                            String title = "BASIC";
                            title = newsElement.select("dl dt a").text();

                            Elements imageElements = newsElement.select("div.thumb a img");
                            String ImageUrl = imageElements.attr("src");

                            Elements urlElements = newsElement.select("div.thumb a");

                            String newsURL = urlElements.attr("href");



                            if(title.equals("BASIC")) {

                                searchWord.setNewsTitle("관련 뉴스 X");
                            }
                            else{
                                searchWord.setNewsTitle(title);
                                searchWord.setNewsURL(newsURL);
                            }
                            searchWord.setNewsImage(ImageUrl.substring(0, ImageUrl.length()-28));
                            //Log.v("newsTitle ", newsTitle.text());

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String replysUrl = "https://search.naver.com/search.naver?where=realtime&query=" + searchWord.getWord() + "&best=1";

                    try{
                        Document mDocument3 = Jsoup.connect(replysUrl).get();
                        Elements replyElements = mDocument3.select("ul.type01 li");

                        Log.v("replay test : count", Integer.toString(replyElements.size()));

                        for(int j = 0; j < replyElements.size(); j++){
                            Reply mReply = new Reply();

                            mReply.name = replyElements.get(j).select(".user_name").text();
                            Log.v("testreply name", mReply.name + " " + j);

                            if(!replyElements.get(j).select(".sub_retweet").text().isEmpty()){
                                mReply.text = replyElements.get(j).select(".cmmt").text();
                                mReply.time = replyElements.get(j).select(".time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_retweet").text();
                                mReply.count2 = replyElements.get(j).select(".sub_interest").text();
                                mReply.count3 = "  ";
                                Log.v("testreply sub_ret",replyElements.get(j).select(".sub_retweet").text() + " " + j );
                                Log.v("testreply twitt",replyElements.get(j).select(".time").text() + " " + j);
                                Log.v("testreply count1",replyElements.get(j).select(".sub_retweet").text() + " " + j);
                                Log.v("testreply count2",replyElements.get(j).select(".sub_interest").text() + " " + j);


                            }

                            else if(!replyElements.get(j).select(".sub_reply").text().isEmpty()){
                                Log.v("test reply2 start", "reply2 start");
                                mReply.text = replyElements.get(j).select(".txt_link").text();
                                mReply.time = replyElements.get(j).select(".sub_time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_reply").text();
                                mReply.count2 = replyElements.get(j).select(".sub_like").text();
                                mReply.count3 = replyElements.get(j).select(".sub_dis").text();
                                Log.v("testreply2 text", replyElements.get(j).select(".txt_link").text());
                                Log.v("testreply2 time", mReply.time);
                                Log.v("testreply2 count1", mReply.count1);
                                Log.v("testreply2 count2", mReply.count2);
                                Log.v("testreply2 count3", mReply.count3);
                            }

                            searchWord.getReplyArrayList().add(mReply);

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    tempList.add(searchWord);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return tempList;
        }

        @Override
        protected void onPreExecute() {

            //     mProgressBar.setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            //myDataset.clear();
            myDataset.addAll(tempList);

            // mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            //        mProgressBar.setVisibility(View.GONE);
        }
    }

    public class GetDataParser7 extends AsyncTask<Void, Void, ArrayList<SearchWord>> {


        String url = "http://datalab.naver.com/keyword/realtimeList.naver?where=main";

        @Override
        protected ArrayList<SearchWord> doInBackground(Void... voids) {

            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument = Jsoup.connect(url).get();
//                Elements mElements = mDocument.select("ol#realrank > li > a");

                Elements mElements = mDocument.select("div.select_date ul li");
                for(int i = 18; i < 20; i++) {
                    SearchWord searchWord = new SearchWord();
                    //Log.v("title test : ", elements.get(i).select("span.ell").text());
                    searchWord.setNumber(Integer.toString(i + 1));
//                    searchWord.setWord(mElements.get(i).select("span.ell").text());
                    searchWord.setWord((mElements.get(i).select("span.title").text()));
//                    if (mElements.get(i).select("span.tx").text().equals("상승")) {
//                        searchWord.setArrow("상승");
//                        searchWord.setRanking(mElements.get(i).select("span.rk").text());
//
//                    } else {
//                        searchWord.setArrow("NEW");
//                    }
//
//                    Log.v("test arrow : ", mElements.get(i).select("span.tx").text());



                    String url = "https://search.naver.com/search.naver?where=nexearch&query=" + searchWord.getWord() + "&sm=top_lve&ie=utf8";

                    try {
                        Document mDocument2 = Jsoup.connect(url).get();
                        Elements newsElement = mDocument2.select("li#sp_nws_all1");

                        if (newsElement != null) {
                            String title = "BASIC";
                            title = newsElement.select("dl dt a").text();

                            Elements imageElements = newsElement.select("div.thumb a img");
                            String ImageUrl = imageElements.attr("src");

                            Elements urlElements = newsElement.select("div.thumb a");

                            String newsURL = urlElements.attr("href");



                            if(title.equals("BASIC")) {

                                searchWord.setNewsTitle("관련 뉴스 X");
                            }
                            else{
                                searchWord.setNewsTitle(title);
                                searchWord.setNewsURL(newsURL);
                            }
                            searchWord.setNewsImage(ImageUrl.substring(0, ImageUrl.length()-28));
                            //Log.v("newsTitle ", newsTitle.text());

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String replysUrl = "https://search.naver.com/search.naver?where=realtime&query=" + searchWord.getWord() + "&best=1";

                    try{
                        Document mDocument3 = Jsoup.connect(replysUrl).get();
                        Elements replyElements = mDocument3.select("ul.type01 li");

                        Log.v("replay test : count", Integer.toString(replyElements.size()));

                        for(int j = 0; j < replyElements.size(); j++){
                            Reply mReply = new Reply();

                            mReply.name = replyElements.get(j).select(".user_name").text();
                            Log.v("testreply name", mReply.name + " " + j);

                            if(!replyElements.get(j).select(".sub_retweet").text().isEmpty()){
                                mReply.text = replyElements.get(j).select(".cmmt").text();
                                mReply.time = replyElements.get(j).select(".time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_retweet").text();
                                mReply.count2 = replyElements.get(j).select(".sub_interest").text();
                                mReply.count3 = "  ";
                                Log.v("testreply sub_ret",replyElements.get(j).select(".sub_retweet").text() + " " + j );
                                Log.v("testreply twitt",replyElements.get(j).select(".time").text() + " " + j);
                                Log.v("testreply count1",replyElements.get(j).select(".sub_retweet").text() + " " + j);
                                Log.v("testreply count2",replyElements.get(j).select(".sub_interest").text() + " " + j);


                            }

                            else if(!replyElements.get(j).select(".sub_reply").text().isEmpty()){
                                Log.v("test reply2 start", "reply2 start");
                                mReply.text = replyElements.get(j).select(".txt_link").text();
                                mReply.time = replyElements.get(j).select(".sub_time").text();
                                mReply.count1 = replyElements.get(j).select(".sub_reply").text();
                                mReply.count2 = replyElements.get(j).select(".sub_like").text();
                                mReply.count3 = replyElements.get(j).select(".sub_dis").text();
                                Log.v("testreply2 text", replyElements.get(j).select(".txt_link").text());
                                Log.v("testreply2 time", mReply.time);
                                Log.v("testreply2 count1", mReply.count1);
                                Log.v("testreply2 count2", mReply.count2);
                                Log.v("testreply2 count3", mReply.count3);
                            }

                            searchWord.getReplyArrayList().add(mReply);

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    tempList.add(searchWord);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return tempList;
        }

        @Override
        protected void onPreExecute() {

            //     mProgressBar.setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            //myDataset.clear();
            myDataset.addAll(tempList);

            // mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            //        mProgressBar.setVisibility(View.GONE);


            switchs = true;
        //    contentLinearlayout.setClickable(true);
        }
    }

    public void currentTimer(){
        /////////// + 현재시간 표시 //////////////

//        Handler mHandler = new Handler();
//        mHandler.postDelayed(new Runnable(){
//            @Override
//            public void run() {
//
//            }
//        }, 1000);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm:ss");
        String formateDate = sdfNow.format(date);
        loadingTime.setText(formateDate);
        titleTime.setText(formateDate+" 지금이슈");


        /////////////////////////////////////////
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
    }

}
