package com.secondtype.realtimesearchword3;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by appaaaa on 2017-02-17.
 */

public class SearchWord implements Serializable{

    private String number;
    private String word;
//    private String arrow;
//    private String ranking;
    private String newsTitle;
    private String newsImage;
    private String newsURL;
    private ArrayList<Reply> replyArrayList = new ArrayList<Reply>();

    public SearchWord(){
    }

    public SearchWord(String number, String word,  String newsTitle, String newsImage, String newsURL, ArrayList<Reply> replyArrayList){
        this.number = number;
        this.word = word;
//        this.arrow = arrow;
//        this.ranking = ranking;
        this.newsTitle = newsTitle;
        this.newsImage = newsImage;
        this.newsURL = newsURL;
        this.replyArrayList.addAll(replyArrayList);
    }

    public String getNumber() {return number; }
    public String getWord() {return word; }
//    public String getArrow() {return arrow; }
//    public String getRanking() {return ranking; }
    public String getNewsTitle() {return newsTitle; }
    public String getNewsImage() {return newsImage; }
    public String getNewsURL(){return newsURL;}
    public ArrayList<Reply> getReplyArrayList(){return replyArrayList;}

    public void setNumber(String number){this.number = number;}
    public void setWord(String word){this.word = word;}
//    public void setArrow(String arrow){this.arrow = arrow;}
//    public void setRanking(String ranking){this.ranking = ranking;}
    public void setNewsTitle(String newsTitle){this.newsTitle = newsTitle;}
    public void setNewsImage(String newsImage){this.newsImage = newsImage;}
    public void setNewsURL(String newsURL){this.newsURL = newsURL;}
    public void setReplyArrayList(ArrayList<Reply> replyArrayList){this.replyArrayList.addAll(replyArrayList);}

//    public SearchWord(Parcel in){
//        this.number = in.readString();
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(this.number);
//        parcel.writeString(this.word);
//        parcel.writeString(this.arrow);
//        parcel.writeString(this.ranking);
//        parcel.writeString(this.newsTitle);
//        parcel.writeString(this.newsImage);
//        parcel.writeString(this.newsURL);
//    }
}
