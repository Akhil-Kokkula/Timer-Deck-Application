package com.example.timer;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.timer.Adapter.TimerCardAdapter;
import com.example.timer.Model.CardModel;
import com.example.timer.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class AddNewCard extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newCardName;
    private EditText newTask;
    private EditText newTask2;
    private EditText time1;
    private EditText time2;
    private EditText repeatTxt;
    private Button newCardSaveButton;
    private DatabaseHandler db;


    public static AddNewCard newInstance() {
        return new AddNewCard();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_input, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newCardName = getView().findViewById(R.id.textView);
        newTask = getView().findViewById(R.id.inputTask);
        newTask2 = getView().findViewById(R.id.inputTask2);
        time1 = getView().findViewById(R.id.editTextTime);
        time2 = getView().findViewById(R.id.editTextTime2);
        repeatTxt = getView().findViewById(R.id.repeatTxtBox);
        newCardSaveButton = requireView().findViewById(R.id.saveBtn);
        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if(bundle != null) {
            isUpdate = true;
            String name = bundle.getString("blockCardName");
            newCardName.setText(name);
        }

        newCardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    newCardSaveButton.setEnabled(false);
                    newCardSaveButton.setTextColor(Color.BLACK);
                } else {
                    newCardSaveButton.setEnabled(true);
                    newCardSaveButton.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdate = isUpdate;
        System.out.println(newCardSaveButton);
        newCardSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newCardName.getText().toString();
                String task1Text = newTask.getText().toString();
                String task2Text = newTask2.getText().toString();
                String time1Text = time1.getText().toString();
                String time2Text = time2.getText().toString();
                int repeatNum = Integer.valueOf(repeatTxt.getText().toString());
                if (finalIsUpdate) {
                    //set the id counter based on on click listeners (if card clicked or add button clicked)
                    //TimeCardAdapter.val - if clicked on card, HomeScreen.counter - if clicked on add button
                    db.updateCardId(bundle.getInt("id"), MainActivity.index);
                    db.updateCardName(bundle.getInt("id"), text);
                    db.updateTask1(bundle.getInt("id"), task1Text);
                    db.updateTask2(bundle.getInt("id"), task2Text);
                    db.updateTime1(bundle.getInt("id"), time1Text);
                    db.updateTime2(bundle.getInt("id"), time2Text);
                    db.updateRepeatNumber(bundle.getInt("id"), repeatNum);
                } else {
                    CardModel card = new CardModel();
                    card.setId(MainActivity.index);
                    card.setCardName(text);
                    card.setTask1(task1Text);
                    card.setTask2(task2Text);
                    card.setTime1(time1Text);
                    card.setTime2(time2Text);
                    card.setRepeatNum(repeatNum);
                    db.insertTask(card);
                }
                dismiss();
            }
        });

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        System.out.println("here");
        Fragment parent = getParentFragmentManager().findFragmentByTag("NewFragmentTag");
        if (parent != null && parent instanceof DialogCloseListener) {
            ((DialogCloseListener)parent).handleDialogClose(dialog);
        }
        super.onDismiss(dialog);
    }
}
