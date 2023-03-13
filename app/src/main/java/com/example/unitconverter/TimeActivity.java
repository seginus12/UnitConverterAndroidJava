package com.example.unitconverter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import java.text.DecimalFormat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.HashMap;

public class TimeActivity extends AppCompatActivity implements View.OnClickListener {

    final String[] timeUnits = new String[]{
            "Seconds",
            "Minutes",
            "Hours",
            "Days"
    };

    final HashMap<Pair<String, String>, String> mults = new HashMap<Pair<String, String>, String>() {
        {
            put(new Pair<>("Days", "Seconds"), "86400");
            put(new Pair<>("Days", "Minutes"), "1440");
            put(new Pair<>("Days", "Hours"), "24");
            put(new Pair<>("Days", "Days"), "1");
            put(new Pair<>("Hours", "Seconds"), "3600");
            put(new Pair<>("Hours", "Minutes"), "60");
            put(new Pair<>("Hours", "Hours"), "1");
            put(new Pair<>("Hours", "Days"), "0.0416666666666667");
            put(new Pair<>("Minutes", "Seconds"), "60");
            put(new Pair<>("Minutes", "Minutes"), "1");
            put(new Pair<>("Minutes", "Hours"), "0.0166666666666667");
            put(new Pair<>("Minutes", "Days"), "0.0006944444444444");
            put(new Pair<>("Seconds", "Seconds"), "1");
            put(new Pair<>("Seconds", "Minutes"), "0.0166666666666667");
            put(new Pair<>("Seconds", "Hours"), "0.0002777777777777");
            put(new Pair<>("Seconds", "Days"), "0.000011574074074074");
        }
    };

    Button bFromUnit, bToUnit, bAC, bC, b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bDot, bRevert;
    TextView fromTextView, toTextView;
    EditText fromEdit, toEdit;
    String selectedUnit;

    private static final DecimalFormat round = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        fromTextView = (TextView) findViewById(R.id.fromText);
        fromTextView.setText(timeUnits[0]);
        toTextView = (TextView) findViewById(R.id.toText);
        toTextView.setText(timeUnits[0]);
        fromEdit = (EditText) findViewById(R.id.et_fromUnit);
        toEdit = (EditText) findViewById(R.id.et_toUnit);
        assignButtonListener(bFromUnit, R.id.bFromUnit);
        assignButtonListener(bToUnit, R.id.bToUnit);
        assignButtonListener(b1, R.id.b1);
        assignButtonListener(b2, R.id.b2);
        assignButtonListener(b3, R.id.b3);
        assignButtonListener(b4, R.id.b4);
        assignButtonListener(b5, R.id.b5);
        assignButtonListener(b6, R.id.b6);
        assignButtonListener(b7, R.id.b7);
        assignButtonListener(b8, R.id.b8);
        assignButtonListener(b9, R.id.b9);
        assignButtonListener(b0, R.id.b0);
        assignButtonListener(bC, R.id.bC);
        assignButtonListener(bAC, R.id.bAC);
        assignButtonListener(bDot, R.id.bDot);
        assignButtonListener(bRevert, R.id.bRevert);
        fromEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!fromEdit.getText().toString().equals("")) {
                    getResult();
                } else
                    toEdit.setText("");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    void assignButtonListener(Button button, int id) {
        button = findViewById(id);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        if (button.getId() == R.id.bToUnit) {
            Dialog unitList = onCreateDialog(button);
            unitList.show();
            return;
        }
        if (button.getId() == R.id.bFromUnit) {
            Dialog unitList = onCreateDialog(button);
            unitList.show();
            return;
        }
        if (button.getId() == R.id.bAC) {
            fromEdit.setText("");
            return;
        }
        if (button.getId() == R.id.bC) {
            if (!fromEdit.getText().toString().equals(""))
                fromEdit.setText(fromEdit.getText().toString().substring(0, fromEdit.length() - 1));
            return;
        }
        if (button.getId() == R.id.bDot) {
            if (fromEdit.getText().toString().equals("") || fromEdit.getText().toString().contains("."))
                return;
        }
        if (fromEdit.getText().toString().equals("0") && button.getId() == R.id.b0)
            return;
        if (button.getId() == R.id.bRevert) {
            String t = fromTextView.getText().toString();
            fromTextView.setText(toTextView.getText().toString().replace(",", "."));
            toTextView.setText(t);
            fromEdit.setText(toEdit.getText().toString());
            return;
        }
        fromEdit.append(button.getText().toString());
    }

    String getLastSymbol(String string) {
        return string.substring(string.length() - 1);
    }

    void getResult() {
        String fromUnit = fromTextView.getText().toString();
        String toUnit = toTextView.getText().toString();
        double value = Double.parseDouble(fromEdit.getText().toString());
        Pair<String, String> key = new Pair<>(fromUnit, toUnit);
        double mult = Double.parseDouble(mults.get(key));
        double result = value * mult;
        if (result % 1 == 0)
            toEdit.setText(Integer.toString((int)result));
        else
            toEdit.setText(round.format(result));
    }

    public Dialog onCreateDialog(Button button) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose unit")
                .setSingleChoiceItems(timeUnits, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                selectedUnit = Arrays.asList(timeUnits).get(item);
                            }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (button.getId() == R.id.bFromUnit)
                            fromTextView.setText(selectedUnit);
                        if (button.getId() == R.id.bToUnit)
                            toTextView.setText(selectedUnit);
                        if (!fromEdit.getText().toString().equals(""))
                            getResult();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}