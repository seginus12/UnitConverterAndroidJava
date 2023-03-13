package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bTemp, bLength, bTime, bSpeed, bVolume, bEnergy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignListener(bTemp, R.id.bTemp);
        assignListener(bLength, R.id.bLength);
        assignListener(bTime, R.id.bTime);
        assignListener(bSpeed, R.id.bSpeed);
        assignListener(bVolume, R.id.bVolume);
        assignListener(bEnergy, R.id.bEnergy);
    }

    void assignListener(Button button, int id){
        button = findViewById(id);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        if (button.getId() == R.id.bTime)
            startActivity(new Intent(MainActivity.this, TimeActivity.class));
        if (button.getId() == R.id.bTemp)
            startActivity(new Intent(MainActivity.this, TempActivity.class));
        if (button.getId() == R.id.bLength)
            startActivity(new Intent(MainActivity.this, LengthActivity.class));
        if (button.getId() == R.id.bVolume)
            startActivity(new Intent(MainActivity.this, VolumeActivity.class));
        if (button.getId() == R.id.bSpeed)
            startActivity(new Intent(MainActivity.this, SpeedActivity.class));
        if (button.getId() == R.id.bEnergy)
            startActivity(new Intent(MainActivity.this, EnergyActivity.class));
    }
}