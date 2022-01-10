package com.example.timer.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.fragment.app.FragmentActivity;

import com.example.timer.Model.CardModel;
import com.example.timer.Model.TimerCardModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler2 extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "timerCardsDatabase";
    private static final String TIMER_CARD_TABLE = "timerCards";
    private static final String ID = "id";
    private static final String TIMER_CARD = "timerCard";
    private static final String CREATE_TIMER_CARD_TABLE = "CREATE TABLE " + TIMER_CARD_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TIMER_CARD + " TEXT)";
    public static SQLiteDatabase db;

    public DatabaseHandler2(FragmentActivity context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TIMER_CARD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TIMER_CARD_TABLE);
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public long insertTask(TimerCardModel card) {
        ContentValues cv = new ContentValues();
        cv.put(TIMER_CARD, card.getCardTimerName());
        long val = db.insert(TIMER_CARD_TABLE, null, cv);
        return val;
    }

    @SuppressLint("Range")
    public List<TimerCardModel> getAllCards() {
        List<TimerCardModel> cards = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(TIMER_CARD_TABLE, null, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        TimerCardModel card = new TimerCardModel();
                        card.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        card.setCardTimerName(cursor.getString(cursor.getColumnIndex(TIMER_CARD)));
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
    public TimerCardModel getCardById(int id) {
        TimerCardModel card = new TimerCardModel();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(TIMER_CARD_TABLE, null, "ID = " + id, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        card.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        card.setCardTimerName(cursor.getString(cursor.getColumnIndex(TIMER_CARD)));
                    } while(cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cursor.close();
        }

        return card;
    }

    @SuppressLint("Range")
    public int getLastId() {
        String selectQuery = "SELECT  * FROM " + "timerCards";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            cursor.moveToLast();
            return cursor.getInt(cursor.getColumnIndex(ID));
        }
        return -1;
    }


    public void updateCardName(int id, String cardName) {
        ContentValues cv = new ContentValues();
        cv.put(TIMER_CARD, cardName);
        db.update(TIMER_CARD_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteCard(int id) {
        db.delete(TIMER_CARD, ID + "=?", new String[] {String.valueOf(id)});
    }
}
