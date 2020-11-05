package com.example.colortilescanvas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    EditText tilesX, tilesY, tiles, colors, duration, interval;
    Spinner motionEvents;
    CheckBox epilepsy, square;
    String motionEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        /*tilesX = findViewById(R.id.tilesX);
        tilesY = findViewById(R.id.tilesY);*/
        tiles = findViewById(R.id.tiles);
        colors = findViewById(R.id.colors);

        /*motionEvents = findViewById(R.id.motionEvents);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.motionEvents));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motionEvents.setAdapter(adapter);

        motionEvents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                motionEvent = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        square = findViewById(R.id.square);
        epilepsy = findViewById(R.id.epilepsy);
        epilepsy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { //делает поле длительности и интервала видимым/невидимым в зависимости от чекбокса
                if (epilepsy.isChecked()) {
                    duration.setVisibility(View.VISIBLE);
                    interval.setVisibility(View.VISIBLE);
                } else {
                    duration.setVisibility(View.GONE);
                    interval.setVisibility(View.GONE);
                }
            }
        });

        duration = findViewById(R.id.duration);
        interval = findViewById(R.id.interval);
    }

    public void onGame(View view) {
//        if (/*tilesX.getText().toString().equals("") || tilesY.getText().toString().equals("") ||*/
//                tiles.getText().toString().equals("") || colors.getText().toString().equals("")) {
//            Toast.makeText(this, "pls type numbers!", Toast.LENGTH_SHORT).show();
//            return;
//        }

        intent = new Intent(this, TilesActivity.class);
        try { //если не ловить исключение, приложение вылетит при каком-либо незаполненном поле
            /*intent.putExtra("tilesX", Integer.parseInt(tilesX.getText().toString()));
            intent.putExtra("tilesY", Integer.parseInt(tilesY.getText().toString()));
            intent.putExtra("motionEvent", motionEvent);*/

            intent.putExtra("square", square.isChecked());
            intent.putExtra("tiles", Integer.parseInt(tiles.getText().toString()));
            intent.putExtra("colors", Integer.parseInt(colors.getText().toString()));

            intent.putExtra("epilepsy", epilepsy.isChecked());
            intent.putExtra("duration", Integer.parseInt(duration.getText().toString()));
            intent.putExtra("interval", Integer.parseInt(interval.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivity(intent); //запускает активити с плитками
    }
}