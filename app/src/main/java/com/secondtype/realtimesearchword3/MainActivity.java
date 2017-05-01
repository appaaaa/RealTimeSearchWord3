package com.secondtype.realtimesearchword3;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button refreshBtn;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SearchWord> myDataset;

    private ProgressBar mProgressBar;

    private ArrayList<Reply> replyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();





        mProgressBar = (ProgressBar)findViewById(R.id.progressbar);


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
            }
        });

        GetDataParser firstParser = new GetDataParser();
        firstParser.execute( );



    }


    public class GetDataParser extends AsyncTask<Void, Void, ArrayList<SearchWord>>{


        String url = "http://datalab.naver.com/keyword/realtimeList.naver?where=main";




        @Override
        protected ArrayList<SearchWord> doInBackground(Void... voids) {

            ArrayList<SearchWord> tempList = new ArrayList<SearchWord>();

            try{
                Document mDocument = Jsoup.connect(url).get();
//                Elements mElements = mDocument.select("ol#realrank > li > a");

                Elements mElements = mDocument.select("div.select_date ul li");
                for(int i = 0; i < mElements.size(); i++) {
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
                        Log.v("replybug", "-------------------------------------"+searchWord.getWord());

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
                                Log.v("replybug", mReply.text);
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
                                Log.v("replybug", mReply.text);
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

            mProgressBar.setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<SearchWord> tempList) {
            myDataset.clear();
            myDataset.addAll(tempList);

            mRecyclerView.setAdapter(mAdapter);

            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();


    }
}
