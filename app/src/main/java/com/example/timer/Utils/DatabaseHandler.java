package com.example.timer.Utils;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.fragment.app.FragmentActivity;

import com.example.timer.Model.CardModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "timerDatabase";
    private static final String TIMER_TABLE = "timer";
    private static final String ID = "id";
    private static final String CARD_NAME = "cardName";
    private static final String REPEAT_NUM = "repeatNum";
    private static final String TASK1 = "task1";
    private static final String TASK2 = "task2";
    private static final String TASK1_TIME = "task1Time";
    private static final String TASK2_TIME = "task2Time";
    private static final String CREATE_TIMER_TABLE = "CREATE TABLE " + TIMER_TABLE + "(" + ID + " INTEGER, "
                                                    + CARD_NAME + " TEXT, " + REPEAT_NUM + " INTEGER, " + TASK1 + " TEXT, " + TASK2 + " TEXT, " + TASK1_TIME + " TEXT, " + TASK2_TIME + " TEXT)";
    public static SQLiteDatabase db;

    public DatabaseHandler(FragmentActivity context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TIMER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TIMER_TABLE);
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertTask(CardModel card) {
        ContentValues cv = new ContentValues();
        //set the id based on counter value
        cv.put(ID, card.getId());
        cv.put(CARD_NAME, card.getCardName());
        cv.put(REPEAT_NUM, card.getRepeatNum());
        cv.put(TASK1, card.getTask1());
        cv.put(TASK2, card.getTask2());
        cv.put(TASK1_TIME, card.getTime1());
        cv.put(TASK2_TIME, card.getTime2());
        db.insert(TIMER_TABLE, null, cv);
    }

    @SuppressLint("Range")
    public List<CardModel> getAllCards() {
        List<CardModel> cards = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(TIMER_TABLE, null, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                       CardModel card = new CardModel();
                       card.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                       card.setCardName(cursor.getString(cursor.getColumnIndex(CARD_NAME)));
                       card.setRepeatNum(cursor.getInt(cursor.getColumnIndex(REPEAT_NUM)));
                       card.setTask1(cursor.getString(cursor.getColumnIndex(TASK1)));
                       card.setTask2(cursor.getString(cursor.getColumnIndex(TASK2)));
                       card.setTime1(cursor.getString(cursor.getColumnIndex(TASK1_TIME)));
                       card.setTime2(cursor.getString(cursor.getColumnIndex(TASK2_TIME)));
                       cards.add(card);
                    } while(cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cursor.close();
        }

        return cards;
    }

    @SuppressLint("Range")
    public static List<CardModel> getCardsById(int id) {
        List<CardModel> cards = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(TIMER_TABLE, null, "ID = " + id, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        CardModel card = new CardModel();
                        card.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        card.setCardName(cursor.getString(cursor.getColumnIndex(CARD_NAME)));
                        card.setRepeatNum(cursor.getInt(cursor.getColumnIndex(REPEAT_NUM)));
                        card.setTask1(cursor.getString(cursor.getColumnIndex(TASK1)));
                        card.setTask2(cursor.getString(cursor.getColumnIndex(TASK2)));
                        card.setTime1(cursor.getString(cursor.getColumnIndex(TASK1_TIME)));
                        card.setTime2(cursor.getString(cursor.getColumnIndex(TASK2_TIME)));
                        cards.add(card);
                    } while(cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cursor.close();
        }

        return cards;
    }



    public void updateCardId(int id, int cardId) {
        ContentValues cv = new ContentValues();
        cv.put(ID, cardId);
        db.update(TIMER_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateCardName(int id, String cardName) {
        ContentValues cv = new ContentValues();
        cv.put(CARD_NAME, cardName);
        db.update(TIMER_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateRepeatNumber(int id, int repeatNum) {
        ContentValues cv = new ContentValues();
        cv.put(REPEAT_NUM, repeatNum);
        db.update(TIMER_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateTask1(int id, String list1) {
        ContentValues cv = new ContentValues();
        cv.put(TASK1, list1);
        db.update(TIMER_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateTask2(int id, String list2) {
        ContentValues cv = new ContentValues();
        cv.put(TASK2, list2);
        db.update(TIMER_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateTime1(int id, String time1) {
        ContentValues cv = new ContentValues();
        cv.put(TASK1, time1);
        db.update(TIMER_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateTime2(int id, String time2) {
        ContentValues cv = new ContentValues();
        cv.put(TASK2, time2);
        db.update(TIMER_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteCard(int id) {
        db.delete(TIMER_TABLE, ID + "=?", new String[] {String.valueOf(id)});
    }



}
