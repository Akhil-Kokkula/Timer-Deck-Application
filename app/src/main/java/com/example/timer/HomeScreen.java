package com.example.timer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.timer.Adapter.TimerCardAdapter;
import com.example.timer.Model.TimerCardModel;
import com.example.timer.Utils.DatabaseHandler;
import com.example.timer.Utils.DatabaseHandler2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import android.content.Intent;



public class HomeScreen extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // TODO: Rename and change types of parameters
    private RecyclerView timerCards;
    private TimerCardAdapter timerCardsAdapter;
    private List<TimerCardModel> timerCardsList;
    private DatabaseHandler2 db2;
    public static FragmentTransaction transaction;

    public static int counter; //incremented after every fragment

    ImageButton add;

    public HomeScreen() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_screen, container, false);

        timerCards = v.findViewById(R.id.timers);
        timerCards.setLayoutManager(new LinearLayoutManager(getActivity()));
        timerCardsList = new ArrayList<>();
        db2 = new DatabaseHandler2(getActivity());
        db2.openDatabase();

        System.out.println(db2.getLastId());

        if (db2.getLastId() > 0) {
            counter = db2.getLastId() + 1;
        } else {
            counter = 1;
        }

        timerCardsAdapter = new TimerCardAdapter(db2, this);
        timerCards.setAdapter(timerCardsAdapter);

        timerCardsList = db2.getAllCards();
        for (int i = 0; i < timerCardsList.size(); i++) {
            System.out.println(timerCardsList.get(i).toString());
        }
        timerCardsAdapter.setTasks(timerCardsList);


        add = v.findViewById(R.id.addBtn);
        add.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        MainActivity.index = counter;
        transaction = getParentFragmentManager().beginTransaction();
        Fragment timerCardCreation = new TimerCardCreation();
        transaction.replace(R.id.container, timerCardCreation, "NewFragmentTag").addToBackStack("fragment");
        transaction.commit();
        counter++;
    }

}