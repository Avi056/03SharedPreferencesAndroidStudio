package com.example.a3sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tleft, tright, bleft, bright;
    View layout;
    SeekBar seekBar;
    TextView[] views;
    String TAG = "com.nawlakheavyukta.3sharedpreferences";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String sizeTag;
    String switchTag;
    Switch Switch;
    Boolean switchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.act);
        tleft = findViewById(R.id.tl);
        tright = findViewById(R.id.tr);
        bleft = findViewById(R.id.bl);
        bright = findViewById(R.id.br);
        views = new TextView[]{tleft, tright, bleft, bright};
        tleft.setOnClickListener(this);
        bleft.setOnClickListener(this);
        tright.setOnClickListener(this);
        bright.setOnClickListener(this);
        seekBar = findViewById(R.id.seekbar);
        seekBar.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(TAG, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sizeTag = "inti";
        switchTag = "switch";
        Switch = (Switch) findViewById(R.id.s);
        switchState = Switch.isChecked();
        setInitialValue();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int lastProgress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                for(TextView x: views){
                    x.setTextSize(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                lastProgress = seekBar.getProgress();
//                seekBar.setProgress(sharedPreferences.getInt(sizeTag, 0));
                for (TextView x: views){
                    x.setTextSize(sharedPreferences.getInt(sizeTag, 25));
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Snackbar snackbar = Snackbar.make(layout, "Font size changed to " + seekBar.getProgress(), Snackbar.LENGTH_LONG);
                snackbar.show();
                editor.putInt(sizeTag, seekBar.getProgress());
                editor.apply();
            }
        });
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                editor.clear();
                editor.apply();
                setInitialValue();
                return true;
            }
        });
    }
    @Override
    public void onClick(View view) {
            switchState = Switch.isChecked();
            editor.putBoolean(switchTag, Switch.isChecked());
            editor.apply();
            TextView x = (TextView) view;
            if(switchState == false) {
                x.setText("" + (Integer.parseInt(x.getText().toString()) + 1));
                x.setTextColor(Color.GRAY);
            }
            else {
                x.setText("" + (Integer.parseInt(x.getText().toString()) - 1));
                x.setTextColor(Color.RED);
            }
            editor.putString(x.getTag().toString(), x.getText().toString());
            editor.apply();
    }
    protected void onResume(){
        super.onResume();
        setInitialValue();
    }
    private void setInitialValue(){
        for(TextView x: views) {
            x.setText(sharedPreferences.getString(x.getTag().toString(),"0"));
            x.setTextSize(sharedPreferences.getInt(sizeTag, 25));
        }
        seekBar.setProgress(sharedPreferences.getInt(sizeTag, 25));
        Switch.setChecked(sharedPreferences.getBoolean(switchTag, false));

    }

    public void setSwitch(View view) {
        if(switchState == true){
            for(TextView x: views) {
                x.setTextColor(Color.GRAY);
            }
        }
        else{
            for(TextView x: views) {
                x.setTextColor(Color.RED);
            }
        }
        editor.putBoolean(switchTag, Switch.isChecked());
        editor.apply();
    }
}