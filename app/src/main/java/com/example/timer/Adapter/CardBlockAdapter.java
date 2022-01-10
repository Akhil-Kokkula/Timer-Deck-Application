package com.example.timer.Adapter;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timer.AddNewCard;
import com.example.timer.Model.CardModel;
import com.example.timer.R;
import com.example.timer.TimerCardCreation;
import com.example.timer.Utils.DatabaseHandler;

import java.util.*;

public class CardBlockAdapter extends RecyclerView.Adapter<CardBlockAdapter.ViewHolder> {

    private List<CardModel> cardBlockList;
    private TimerCardCreation fragment;
    private DatabaseHandler db;

    public CardBlockAdapter(DatabaseHandler db, TimerCardCreation fragment) {
        this.db = db;
        this.fragment = fragment;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        db.openDatabase();
        CardModel item = cardBlockList.get(position);
        holder.cardName.setText(item.getCardName());
        holder.repeatNum.setText(String.valueOf(item.getRepeatNum()));


        ListItemsAdapter listAdapter = new ListItemsAdapter(item.getTasks());
        holder.items.setLayoutManager(new LinearLayoutManager(fragment.getActivity()));
        holder.items.setAdapter(listAdapter);

        TimeListAdapter timeAdapter = new TimeListAdapter(item.getTimes());
        holder.times.setLayoutManager(new LinearLayoutManager(fragment.getActivity()));
        holder.times.setAdapter(timeAdapter);
    }

    public int getItemCount() {
        return cardBlockList.size();
    }

    public void setTasks(List<CardModel> list) {
        this.cardBlockList = list;
        notifyDataSetChanged();
    }

    public void editItem(int position) {
        CardModel item = cardBlockList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("cardName", item.getCardName());
        AddNewCard fragment = new AddNewCard();
        fragment.setArguments(bundle);
        fragment.show(fragment.getChildFragmentManager(), AddNewCard.TAG);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_template, parent, false);
        return new ViewHolder(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardName;
        TextView repeatNum;
        RecyclerView items;
        RecyclerView times;

        ViewHolder(View view) {
            super(view);
            cardName = view.findViewById(R.id.cardBlockName);
            repeatNum = view.findViewById(R.id.repeatTxt);
            items = view.findViewById(R.id.itemsOfCard);
            times = view.findViewById(R.id.timeList);
        }
    }

}
