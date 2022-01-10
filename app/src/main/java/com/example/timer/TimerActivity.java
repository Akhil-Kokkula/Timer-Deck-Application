package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.timer.Adapter.TimerCardAdapter;
import com.example.timer.Model.CardModel;
import com.example.timer.Model.TimerCardModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimerActivity extends AppCompatActivity {


    private List<CardModel> cards;
    private ImageButton playPause;
    private TextView txtViewCountDown;
    private TextView txtTimerNameDisplay;
    private TextView txtCardNameDisplay;
    private TextView currTask;
    private TextView upcomingTask;
    private TextView repeatNum;
    private long inputTime;
    private boolean stop;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        cards = TimerCardAdapter.cards;


        currTask = findViewById(R.id.currentTask);
        repeatNum = findViewById(R.id.repeatNumDisplay);
        upcomingTask = findViewById(R.id.upcomingTask);
        playPause = findViewById(R.id.playPause);
        playPause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        txtViewCountDown = findViewById(R.id.timer);
        txtTimerNameDisplay = findViewById(R.id.timerCardName);
        txtCardNameDisplay = findViewById(R.id.cardNameDisplay);

        //txtTimerNameDisplay.setText(timer.getCardTimerName());

        ArrayList<String> updatedTimes = new ArrayList<>();
        ArrayList<String> cardNames = new ArrayList<>();
        ArrayList<String> updatedTasks = new ArrayList<>();
        ArrayList<String> repeatNums = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++) {

            for (int j = 0; j < cards.get(i).getRepeatNum(); j++) {
                updatedTimes.add(cards.get(i).getTimes().get(0));
                updatedTimes.add(cards.get(i).getTimes().get(1));
                updatedTasks.add(cards.get(i).getTasks().get(0));
                updatedTasks.add(cards.get(i).getTasks().get(1));
                repeatNums.add(String.valueOf(cards.get(i).getRepeatNum()));
                repeatNums.add(String.valueOf(cards.get(i).getRepeatNum()));
                cardNames.add(cards.get(i).getCardName());
                cardNames.add(cards.get(i).getCardName());
            }


        }

        clock(updatedTimes, repeatNums, updatedTasks, cardNames);


    }


    public void clock(ArrayList<String> times, ArrayList<String> repeat, ArrayList<String> tasks, ArrayList<String> cardNames) {
        stop = false;

        String time = times.get(0);
        String[] split = time.split(":");
        int hoursVal = Integer.valueOf(split[0]);
        int minutesVal = Integer.valueOf(split[1]);
        int secondsVal = Integer.valueOf(split[2]);
        System.out.println(hoursVal + " " + minutesVal + " " + secondsVal);
        inputTime = (hoursVal * 3600000) + (minutesVal * 60000) + (secondsVal * 1000);

        String task = tasks.get(0);
        String repeatVal = repeat.get(0);

        txtCardNameDisplay.setText(cardNames.get(0));
        repeatNum.setText(repeatVal + "x");
        currTask.setText(task);

        if (tasks.size() > 1) {
            upcomingTask.setText(tasks.get(1));
        } else {
            upcomingTask.setText("None");
        }

        int hours = (int) (inputTime / 1000) / 3600;
        int minutes = (int) ((inputTime / 1000) % 3600) / 60;
        int seconds = (int) (inputTime / 1000) % 60;

        String timeFormat;
        if (hours > 0) {
            timeFormat = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0) {
            timeFormat = String.format("%02d:%02d", minutes, seconds);
        } else {
            timeFormat = String.format("%02d", seconds);
        }



        txtViewCountDown.setText(timeFormat);

        Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            @NonNull
            public void run() {
                int hours = (int) (inputTime / 1000) / 3600;
                int minutes = (int) ((inputTime / 1000) % 3600) / 60;
                int seconds = (int) (inputTime / 1000) % 60;

                String timeFormat;
                if (hours > 0) {
                    timeFormat = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                } else if (minutes > 0) {
                    timeFormat = String.format("%02d:%02d", minutes, seconds);
                } else {
                    timeFormat = String.format("%02d", seconds);
                }

                if (inputTime >= 0) {
                    txtViewCountDown.setText(timeFormat);
                    System.out.println(timeFormat);
                    handler.postDelayed(this, 1000);
                    inputTime = inputTime - 1000;
                } else {
                    if (tasks.size() > 1 && times.size() > 1) {
                        tasks.remove(0);
                        times.remove(0);
                        repeat.remove(0);
                        cardNames.remove(0);
                        clock(times, repeat, tasks, cardNames);
                    }
                }

            }
        };

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop = !stop;
                System.out.println(stop);
                if (stop) {
                    handler.removeCallbacks(runnable);
                    playPause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                } else {
                    handler.postDelayed(runnable,1000);
                    playPause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                }
            }
        });

        handler.postDelayed(runnable,1000);


    }

}