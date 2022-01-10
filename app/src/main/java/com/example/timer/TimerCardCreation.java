package com.example.timer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timer.Adapter.CardBlockAdapter;
import com.example.timer.Model.CardModel;
import com.example.timer.Model.TimerCardModel;
import com.example.timer.Utils.DatabaseHandler;
import com.example.timer.Utils.DatabaseHandler2;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TimerCardCreation extends Fragment implements DialogCloseListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public static final String TAG = "ActionBottomDialog";

    // TODO: Rename and change types of parameters

    private RecyclerView blocks;
    private CardBlockAdapter cardsAdapter;
    private FloatingActionButton fab;
    private Button enterCardInfo;
    private List<CardModel> cardList;
    private DatabaseHandler db;
    private DatabaseHandler2 db2;
    private TextView timerCardName;
    public static int counter = 1;


    public TimerCardCreation() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer_card_creation, container, false);
        timerCardName = view.findViewById(R.id.cardName);

        db2 = new DatabaseHandler2(getActivity());
        db2.openDatabase();

        if (db2.getLastId() > 0) {
            counter = db2.getLastId() + 1;
        } else {
            counter = 1;
        }

        db = new DatabaseHandler(getActivity());
        db.openDatabase();
        cardList = new ArrayList<>();
        blocks = view.findViewById(R.id.cardRecyclerView);
        blocks.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardsAdapter = new CardBlockAdapter(db,this);
        blocks.setAdapter(cardsAdapter);
        fab = view.findViewById(R.id.addCard);
        //getCardsById method should be implemented. Cards of id from TimerCardAdapter val will be retrieved.
        cardList = db.getCardsById(MainActivity.index);

        cardsAdapter.setTasks(cardList);
        enterCardInfo = view.findViewById(R.id.enterCardInfo);

        String timerName = db2.getCardById(MainActivity.index).getCardTimerName();
        System.out.println(timerName);
        if (timerName != null) {
            timerCardName.setText(timerName);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                AddNewCard dialog = new AddNewCard();
                dialog.show(transaction, "dialog");
            }
        });

        enterCardInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = timerCardName.getText().toString();
                //need to make user add name to set
                if (timerName != null) {
                    db2.updateCardName(MainActivity.index, name);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("home", 0);
                } else {
                    TimerCardModel item = new TimerCardModel();
                    //This is the id of timerCard
                    item.setId(counter++);
                    System.out.println(item.getId());
                    item.setCardTimerName(name);
                    db2.insertTask(item);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("home", 0);
                }

            }
        });

        return view;
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        System.out.println("here as well");
        cardList = db.getCardsById(MainActivity.index);
        cardsAdapter.setTasks(cardList);
        cardsAdapter.notifyDataSetChanged();
    }

}