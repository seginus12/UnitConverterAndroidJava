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

public class LengthActivity extends AppCompatActivity implements View.OnClickListener {

    final String[] timeUnits = new String[]{
            "Meter",
            "Mile",
            "Yard",
            "Foot",
            "Inch"
    };

    final HashMap<Pair<String, String>, String> mults = new HashMap<Pair<String, String>, String>() {
        {
            put(new Pair<>("Meter", "Meter"), "1");
            put(new Pair<>("Meter", "Mile"), "0.000621371");
            put(new Pair<>("Meter", "Yard"), "1.09361");
            put(new Pair<>("Meter", "Foot"), "3.281");
            put(new Pair<>("Meter", "Inch"), "39.37");

            put(new Pair<>("Mile", "Meter"), "1609");
            put(new Pair<>("Mile", "Mile"), "1");
            put(new Pair<>("Mile", "Yard"), "1760");
            put(new Pair<>("Mile", "Foot"), "5280");
            put(new Pair<>("Mile", "Inch"), "63360");

            put(new Pair<>("Yard", "Meter"), "0.9144");
            put(new Pair<>("Yard", "Mile"), "0.000568182");
            put(new Pair<>("Yard", "Yard"), "1");
            put(new Pair<>("Yard", "Foot"), "3");
            put(new Pair<>("Yard", "Inch"), "36");

            put(new Pair<>("Foot", "Meter"), "0.3048");
            put(new Pair<>("Foot", "Mile"), "0.000189394");
            put(new Pair<>("Foot", "Yard"), "0.3333333333");
            put(new Pair<>("Foot", "Foot"), "1");
            put(new Pair<>("Foot", "Inch"), "12");

            put(new Pair<>("Inch", "Meter"), "0.0254");
            put(new Pair<>("Inch", "Mile"), "0.0000157828282828");
            put(new Pair<>("Inch", "Yard"), "0.0277778");
            put(new Pair<>("Inch", "Foot"), "0.0833334");
            put(new Pair<>("Inch", "Inch"), "1");

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