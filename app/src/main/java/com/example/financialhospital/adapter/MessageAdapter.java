package com.example.financialhospital.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.financialhospital.R;
import com.example.financialhospital.object.ChatObj;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ChatsViewHolder> {

    private List<ChatObj> articleList = new ArrayList<>();
    private Context context;

    public MessageAdapter(List<ChatObj> list, Context context) {
        articleList = list;
        this.context = context;
    }


    @Override
    public ChatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ChatsViewHolder holder, final int position) {


        final ChatObj chatObj = articleList.get(position);
        holder.tvChatMessage.setText(chatObj.getChat());
        String formattedDate = "now";

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Timestamp stamp = new Timestamp(Long.parseLong(chatObj.getTimestamp(), 10));
            Date date = new Date(stamp.getTime());
            formattedDate = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.tvTimestamp.setText(formattedDate);


    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }


    public static class ChatsViewHolder extends RecyclerView.ViewHolder {

        TextView tvChatMessage, tvTimestamp;


        public ChatsViewHolder(View itemView) {
            super(itemView);

            tvChatMessage = itemView.findViewById(R.id.tvChatMessage);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);

        }
    }
}
