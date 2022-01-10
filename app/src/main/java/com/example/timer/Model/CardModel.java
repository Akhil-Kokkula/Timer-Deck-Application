package com.example.timer.Model;

import java.util.ArrayList;

public class CardModel {
    private int id;
    private String cardName;
    private int repeatNum;
    private String task1;
    private String task2;
    private String time1;
    private String time2;
    private ArrayList<String> tasks = new ArrayList<String>();
    private ArrayList<String> times = new ArrayList<String>();

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
        times.add(time1);
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
        times.add(time2);
    }


    public ArrayList<String> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<String> times) {
        this.times = times;
    }


    public String getTask1() {
        return task1;
    }

    public void setTask1(String task1) {
        this.task1 = task1;
        tasks.add(task1);
    }

    public String getTask2() {
        return task2;
    }

    public void setTask2(String task2) {
        this.task2 = task2;
        tasks.add(task2);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getRepeatNum() {
        return repeatNum;
    }

    public void setRepeatNum(int repeatNum) {
        this.repeatNum = repeatNum;
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }

    public void setTasks(String input) {
        this.tasks = tasks;
    }
}
