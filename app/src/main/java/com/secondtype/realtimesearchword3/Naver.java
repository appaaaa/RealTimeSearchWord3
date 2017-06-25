package com.secondtype.realtimesearchword3;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by 임지훈 on 2017-06-24.
 */
public class Naver extends Page {
    static int COUNT_TITLE = 20;
    String url_title = "http://datalab.naver.com/keyword/realtimeList.naver?where=main";
    String[] title = null;

    private int LoadTitle()
    {
        title = new String[COUNT_TITLE];
        try {
            Document mDocument = Jsoup.connect(url_title).get();
            Elements mElements = mDocument.select("div.select_date ul li");

            for (int i = 0; i < COUNT_TITLE; i++) {
                title[i] = mElements.get(i).select("span.title").text();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String[] GetTitles()
    {
        if(title == null)
            LoadTitle();

        return title;
    }

    @Override
    public SearchWord GetSearchWord(int index)
    {
        SearchWord searchWord = new SearchWord();
        searchWord.setNumber(Integer.toString(index + 1));
        searchWord.setWord(title[index]);

        String url = "https://search.naver.com/search.naver?where=nexearch&query=" + searchWord.getWord() + "&sm=top_lve&ie=utf8";

        try {
            Document mDocument2 = Jsoup.connect(url).get();
            Elements newsElement = mDocument2.select("li#sp_nws_all1");
            Elements newsElement2 = mDocument2.select("div.news ul li");
            Log.v("newssection","test");
            Log.v("newssection",newsElement2.select("dl dt a").get(2).text());
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String replysUrl = "https://search.naver.com/search.naver?where=realtime&query=" + searchWord.getWord() + "&best=1";

        try{
            Document mDocument3 = Jsoup.connect(replysUrl).get();
            Elements replyElements = mDocument3.select("ul.type01 li");

            for(int j = 0; j < replyElements.size(); j++){
                Reply mReply = new Reply();
                mReply.name = replyElements.get(j).select(".user_name").text();

                if(!replyElements.get(j).select(".sub_retweet").text().isEmpty()){
                    mReply.text = replyElements.get(j).select(".cmmt").text();
                    mReply.time = replyElements.get(j).select(".time").text();
                    mReply.count1 = replyElements.get(j).select(".sub_retweet").text();
                    mReply.count2 = replyElements.get(j).select(".sub_interest").text();
                    mReply.count3 = "  ";
                }
                else if(!replyElements.get(j).select(".sub_reply").text().isEmpty()){
                    mReply.text = replyElements.get(j).select(".txt_link").text();
                    mReply.time = replyElements.get(j).select(".sub_time").text();
                    mReply.count1 = replyElements.get(j).select(".sub_reply").text();
                    mReply.count2 = replyElements.get(j).select(".sub_like").text();
                    mReply.count3 = replyElements.get(j).select(".sub_dis").text();
                }
                searchWord.getReplyArrayList().add(mReply);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return searchWord;
    }
}
