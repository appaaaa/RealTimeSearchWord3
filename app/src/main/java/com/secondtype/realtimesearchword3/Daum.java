package com.secondtype.realtimesearchword3;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by 임지훈 on 2017-06-24.
 */

public class Daum  extends Page{
    static int COUNT_TITLE = 10;
    String url_title = "http://www.daum.net/";
    String[] title = null;

    private int LoadTitle()
    {
        title = new String[COUNT_TITLE];
        try {
            Document mDocument = Jsoup.connect(url_title).get();
            Elements mElements = mDocument.select("ol.list_hotissue div.rank_cont a.link_issue");
//            Log.v("title_" + i,mElements.toString());

            int count = mElements.size();
            for (int i = 0, i_el = 0; i < COUNT_TITLE && i_el < count; i++, i_el+=2) {
                title[i] = mElements.get(i_el).text();
                Log.v("title_" + i,mElements.get(i_el).text());
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

        String url = "http://search.daum.net/search?w=news&nil_search=btn&DA=NTB&enc=utf8&cluster=y&cluster_page=1&q=" + searchWord.getWord();

        try {
            Document mDocument2 = Jsoup.connect(url).get();
            Elements newsElement = mDocument2.select("li.fst");

            if (newsElement != null) {
                String title = newsElement.select("a.f_link_b").text();
                searchWord.setNewsTitle(title);

//                String newsURL = newsElement.select("a.f_link_b").attr("href");
                String newsURL_daum = newsElement.select("span.f_nb.date a.f_nb").attr("href");
                if(newsURL_daum != null) {
                    searchWord.setNewsURL(newsURL_daum);

                    Document mDocument_news = Jsoup.connect(newsURL_daum).get();
                    String ImageUrl = mDocument_news.select("div.news_view img.thumb_g").attr("src");
                    if(ImageUrl == null || ImageUrl == "") {
                        ImageUrl = newsElement.select("img.thumb_img").attr("src");
                    }

                    if(ImageUrl != null && ImageUrl != "") {
                        searchWord.setNewsImage(ImageUrl);
                        Log.v("___content", title + ", " + ImageUrl + ", " + newsURL_daum);
                    }
                    else {
                        Log.v("___content", title + ", null, " + newsURL_daum);
                    }
                }
                else
                {
                    String newsURL = newsElement.select("a.f_link_b").attr("href");
                    searchWord.setNewsURL(newsURL);
                    Log.v("___content", title + ", " + newsURL);
                }


            }
            else
                searchWord.setNewsTitle("관련 뉴스 X");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String replysUrl = "http://search.daum.net/search?w=social&period=&sd=&ed=&nil_search=btn&enc=utf8&q=" + searchWord.getWord() + "&spacing=0";

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











//        SearchWord searchWord = new SearchWord();
//        searchWord.setNumber(Integer.toString(index + 1));
//        searchWord.setWord(title[index]);
//
//        String url = "https://search.naver.com/search.naver?where=nexearch&query=" + searchWord.getWord() + "&sm=top_lve&ie=utf8";
//
//        try {
//            Document mDocument2 = Jsoup.connect(url).get();
//            Elements newsElement = mDocument2.select("li#sp_nws_all1");
//            Elements newsElement2 = mDocument2.select("div.news ul li");
//            Log.v("newssection","test");
//            Log.v("newssection",newsElement2.select("dl dt a").get(2).text());
//            Log.v("newssection",Integer.toString(newsElement2.size()));
//
//
//            if (newsElement != null) {
//                String title = "BASIC";
//                String title2 = "BASIC";
//                String title3 = "BASIC";
//                String title4 = "BASIC";
//                title = newsElement.select("dl dt a").text();
//
//                //Log.v("title2", title2);
//                            /*
//                            title2 = newsElement2.first().select("dl dt a").text();
//                            title2 = newsElement2.next().get(1).select("dl dt a").text();
//                            title3 = newsElement2.next().get(2).select("dl dt a").text();
//                            title4 = newsElement2.next().select("dl dt a").text();
//
//                            Log.v("title2", "title2" + newsElement2.text());
//                            Log.v("title2","title3" +  title3);
//                            Log.v("title2","title4" +  title4);*/
//
//                Elements imageElements = newsElement.select("div.thumb a img");
//                String ImageUrl = imageElements.attr("src");
//
//                Elements urlElements = newsElement.select("div.thumb a");
//
//                String newsURL = urlElements.attr("href");
//
//                if(title.equals("BASIC")) {
//                    searchWord.setNewsTitle("관련 뉴스 X");
//                }
//                else{
//                    searchWord.setNewsTitle(title);
//                    searchWord.setNewsURL(newsURL);
//                }
//                searchWord.setNewsImage(ImageUrl.substring(0, ImageUrl.length()-28));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String replysUrl = "https://search.naver.com/search.naver?where=realtime&query=" + searchWord.getWord() + "&best=1";
//
//        try{
//            Document mDocument3 = Jsoup.connect(replysUrl).get();
//            Elements replyElements = mDocument3.select("ul.type01 li");
//
//            for(int j = 0; j < replyElements.size(); j++){
//                Reply mReply = new Reply();
//                mReply.name = replyElements.get(j).select(".user_name").text();
//
//                if(!replyElements.get(j).select(".sub_retweet").text().isEmpty()){
//                    mReply.text = replyElements.get(j).select(".cmmt").text();
//                    mReply.time = replyElements.get(j).select(".time").text();
//                    mReply.count1 = replyElements.get(j).select(".sub_retweet").text();
//                    mReply.count2 = replyElements.get(j).select(".sub_interest").text();
//                    mReply.count3 = "  ";
//                }
//                else if(!replyElements.get(j).select(".sub_reply").text().isEmpty()){
//                    mReply.text = replyElements.get(j).select(".txt_link").text();
//                    mReply.time = replyElements.get(j).select(".sub_time").text();
//                    mReply.count1 = replyElements.get(j).select(".sub_reply").text();
//                    mReply.count2 = replyElements.get(j).select(".sub_like").text();
//                    mReply.count3 = replyElements.get(j).select(".sub_dis").text();
//                }
//                searchWord.getReplyArrayList().add(mReply);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return searchWord;
    }
}
