package com.example.timer.Model;

public class TimerCardModel {
    private int id;
    private String cardTimerName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardTimerName() {
        return cardTimerName;
    }

    public void setCardTimerName(String cardTimerName) {
        this.cardTimerName = cardTimerName;
    }

    public String toString() {
        return getCardTimerName();
    }




}
