package com.secondtype.realtimesearchword3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by appaaaa on 2017-02-17.
 */

public class SearchWordAdapter extends RecyclerView.Adapter<SearchWordAdapter.ViewHolder>{
    private ArrayList<SearchWord> mDataset;
    Context mContext;
    MainActivity mMainActivity;

    ListView replyListView;
    ListViewAdapter replyListViewAdapter;

    //////////////+ reply close button //////////////////
    Button replyBack;
    /////////////////////////////////////////////////////

    private View view;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView numberTextView;
        public TextView wordTextView;
//        public ImageView arrowImageView;
//        public TextView rankingTextView;
        public TextView newsTitleTextView;
        public ImageView newsImageImageView;
//        public TextView newRankingTextView;

        public CardView mCardView;

        public LinearLayout replyLinearLayout;
        public LinearLayout contentsLinearLayout;
        public TextView replyName;
        public TextView replyText;
        public TextView replyTime;
        public TextView replyCount1;
        public TextView replyCount2;
        public TextView replyCount3;

        public Button moreReplyButton;






        public ViewHolder(View view){
            super(view);
            numberTextView = (TextView)view.findViewById(R.id.textview_number);
            wordTextView = (TextView)view.findViewById(R.id.textview_word);
//            arrowImageView = (ImageView)view.findViewById(R.id.imageview_arrow);
//            rankingTextView = (TextView) view.findViewById(R.id.textview_ranking);
            newsTitleTextView = (TextView)view.findViewById(R.id.textview_newstitle);
            newsImageImageView = (ImageView)view.findViewById(R.id.imageview_news);
//            newRankingTextView = (TextView)view.findViewById(R.id.textview_new);
            mCardView = (CardView)view.findViewById(R.id.cardview);
            replyLinearLayout = (LinearLayout) view.findViewById(R.id.linearlayout_reply);
            contentsLinearLayout = (LinearLayout)view.findViewById(R.id.linearlayout_contents);
            replyName = (TextView)view.findViewById(R.id.textview_reply_name);
            replyText = (TextView)view.findViewById(R.id.textview_reply_text);
            replyTime = (TextView)view.findViewById(R.id.textview_reply_time);
            replyCount1 = (TextView)view.findViewById(R.id.textview_reply_count1);
            replyCount2 = (TextView)view.findViewById(R.id.textview_reply_count2);
            replyCount3 = (TextView)view.findViewById(R.id.textview_reply_count3);
            moreReplyButton = (Button)view.findViewById(R.id.button_more_reply);



        }
    }

    public SearchWordAdapter(Context mContext, ArrayList<SearchWord> mDataset, MainActivity mMainActivity){
        this.mContext = mContext;
        this.mDataset = mDataset;
        this.mMainActivity = mMainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final int pos = position;
        holder.numberTextView.setText(mDataset.get(position).getNumber());
        holder.wordTextView.setText(mDataset.get(position).getWord());

//        if(mDataset.get(position).getArrow().equals("상승")){
//
//            holder.rankingTextView.setText(mDataset.get(position).getRanking());
//
//        }else if(mDataset.get(position).getArrow().equals("NEW")){
//            holder.arrowImageView.setVisibility(View.GONE);
//            holder.rankingTextView.setVisibility(View.GONE);
//            holder.newRankingTextView.setVisibility(View.VISIBLE);
//        }
//
//        holder.rankingTextView.setText(mDataset.get(position).getRanking());
        holder.newsTitleTextView.setText(mDataset.get(position).getNewsTitle());
        Glide.with(holder.newsImageImageView.getContext())
                .load(mDataset.get(position).getNewsImage())

                .into(holder.newsImageImageView);

        Log.v("test data", mDataset.get(position).getNewsTitle());
        final String s = mDataset.get(position).getNewsTitle();


        if(!mDataset.get(position).getReplyArrayList().isEmpty()){
            holder.replyLinearLayout.setVisibility(View.VISIBLE);
            Log.v("replybug1", mDataset.get(position).getWord());
            Log.v("replybug1", Integer.toString(mDataset.get(position).getReplyArrayList().size()));
            holder.replyName.setText(mDataset.get(position).getReplyArrayList().get(0).name);
            holder.replyText.setText(mDataset.get(position).getReplyArrayList().get(0).text);
            holder.replyTime.setText(mDataset.get(position).getReplyArrayList().get(0).time);
            holder.replyCount1.setText(mDataset.get(position).getReplyArrayList().get(0).count1);
            holder.replyCount2.setText(mDataset.get(position).getReplyArrayList().get(0).count2);
            holder.replyCount3.setText(mDataset.get(position).getReplyArrayList().get(0).count3);

        }else if(mDataset.get(position).getReplyArrayList().isEmpty()){
            holder.replyLinearLayout.setVisibility(View.GONE);
        }

        holder.contentsLinearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(s != null) {
                    Intent intent = new Intent(mMainActivity, DetailWeb.class);
                    intent.putExtra("newsURL", mDataset.get(position).getNewsURL());
                    intent.putExtra("mDataset", mDataset);
                    intent.putExtra("currentNumber", position);
                    mMainActivity.startActivity(intent);
                    mMainActivity.overridePendingTransition(R.anim.rightin, R.anim.notmove);
                }
            }
        });





        holder.replyLinearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                view = mMainActivity.getLayoutInflater().inflate(R.layout.reply_listview, null);
                replyListView = (ListView)view.findViewById(R.id.listview_reply);
                replyListViewAdapter = new ListViewAdapter(mMainActivity, mDataset.get(position).getReplyArrayList());
                ///////////////////+ reply back Button ////////////////////////
                replyBack = (Button)view.findViewById(R.id.button_back_reply);
                ///////////////////////////////////////////////////////////////

                replyListView.setAdapter(replyListViewAdapter);

                replyListViewAdapter.notifyDataSetChanged();

                final AlertDialog listViewDialog = new AlertDialog.Builder(mMainActivity).create();
                listViewDialog.setView(view);
                listViewDialog.show();

                ///////// + reply back button ////////////////////////////
                replyBack.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        listViewDialog.dismiss();
                    }
                });
                ///////////////////////////////////////////////////////////


            }
        });
        holder.moreReplyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                view = mMainActivity.getLayoutInflater().inflate(R.layout.reply_listview, null);
                replyListView = (ListView)view.findViewById(R.id.listview_reply);
                replyListViewAdapter = new ListViewAdapter(mMainActivity, mDataset.get(position).getReplyArrayList());
                ///////////////////+ reply back Button ////////////////////////
                replyBack = (Button)view.findViewById(R.id.button_back_reply);
                ///////////////////////////////////////////////////////////////

                replyListView.setAdapter(replyListViewAdapter);

                replyListViewAdapter.notifyDataSetChanged();

                final AlertDialog listViewDialog = new AlertDialog.Builder(mMainActivity).create();
                listViewDialog.setView(view);
                listViewDialog.show();

                ///////// + reply back button ////////////////////////////
                replyBack.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        listViewDialog.dismiss();
                    }
                });
                ///////////////////////////////////////////////////////////

            }
        });




    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
