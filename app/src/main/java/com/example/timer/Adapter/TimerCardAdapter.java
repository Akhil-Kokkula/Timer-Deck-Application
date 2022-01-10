package com.example.timer.Adapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timer.AddNewCard;
import com.example.timer.HomeScreen;
import com.example.timer.MainActivity;
import com.example.timer.Model.CardModel;
import com.example.timer.Model.TimerCardModel;
import com.example.timer.R;
import com.example.timer.TimerActivity;
import com.example.timer.TimerCardCreation;
import com.example.timer.Utils.DatabaseHandler;
import com.example.timer.Utils.DatabaseHandler2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TimerCardAdapter extends RecyclerView.Adapter<TimerCardAdapter.ViewHolder>{

    private List<TimerCardModel> timerCardList;
    private HomeScreen fragment;
    private DatabaseHandler2 db2;
    private DatabaseHandler db;
    public static List<CardModel> cards = new ArrayList<CardModel>();
    public static int val = 1;

    public TimerCardAdapter(DatabaseHandler2 db2, HomeScreen fragment) {
        this.db2 = db2;
        this.fragment = fragment;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        db2.openDatabase();
        TimerCardModel item = timerCardList.get(position);
        holder.timerCardName.setText(item.getCardTimerName());
        holder.timerCardId.setText(String.valueOf(item.getId()));
    }


    @Override
    public TimerCardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timer_card, viewGroup, false);
        return new TimerCardAdapter.ViewHolder(view);
    }

    public void setTasks(List<TimerCardModel> list) {
        this.timerCardList = list;
        notifyDataSetChanged();
    }

    // Replace the contents of a view (invoked by the layout manager)


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return timerCardList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        TextView timerCardName;
        TextView timerCardId;
        ImageButton playBtn;
        Context context;

        public ViewHolder(View v) {
            super(v);
            timerCardName = v.findViewById(R.id.timerCardName);
            timerCardId = v.findViewById(R.id.idNum);
            playBtn = v.findViewById(R.id.imageButton);
            this.view = v;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //make transaction to fragment
                    val = Integer.valueOf(timerCardId.getText().toString());
                    MainActivity.index = val;
                    System.out.println(val);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new TimerCardCreation(), "NewFragmentTag").addToBackStack(null).commit();
                }
            });

            playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    val = Integer.valueOf(timerCardId.getText().toString());
                    MainActivity.index = val;
                    System.out.println(val);
                    int cardClicked = MainActivity.index;
                    //timer = DatabaseHandler2.getCardById(cardClicked);
                    cards = DatabaseHandler.getCardsById(cardClicked);

                    for (int i = 0; i < cards.size(); i++) {
                        ArrayList<String> taskList = new ArrayList<>();
                        ArrayList<String> timeList = new ArrayList<>();
                        timeList.add(cards.get(i).getTime1());
                        timeList.add(cards.get(i).getTime2());
                    }



                    context = v.getContext();
                    Intent i = new Intent(context, TimerActivity.class);
                    context.startActivity(i);

                }
            });

        }
    }





    // Create new views (invoked by the layout manager)


}
