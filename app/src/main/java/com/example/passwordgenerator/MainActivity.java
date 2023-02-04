package com.example.passwordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    Button btn_generate;
    Button btn_copy;
    CheckBox num_check;
    CheckBox alpha_check;
    CheckBox mixed_check;
    TextView password;
    SeekBar length_seekbar;
    TextView length_txt;

    // default settings
    int check = 0;
    int length = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // associate with xml
        btn_generate = findViewById(R.id.generate_btn);
        btn_copy = findViewById(R.id.copy_btn);

        num_check = findViewById(R.id.num_check);
        alpha_check = findViewById(R.id.alpha_check);
        mixed_check = findViewById(R.id.mixed_check);
        length_txt = findViewById(R.id.length_txt);
        length_seekbar = findViewById(R.id.length_bar);
        password = findViewById(R.id.password);

        // setting password invisible
        password.setVisibility(View.INVISIBLE);

        // setting length text
        length_txt.setText("Length: " + length);

        // setting seekbar
        length_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                length = seekBar.getProgress();
                length_txt.setText("Length: " + length);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        // checking what password
        num_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 1;
                alpha_check.setChecked(false);
                mixed_check.setChecked(false);
            }
        });

        alpha_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 2;
                num_check.setChecked(false);
                mixed_check.setChecked(false);
            }
        });

        mixed_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 3;
                num_check.setChecked(false);
                alpha_check.setChecked(false);
            }
        });

        // generating password
        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please select a type!", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    StringBuilder pass = generatePassword(check, length);
                    password.setText(pass);
                    password.setVisibility(View.VISIBLE);
                }
            }
        });

        // copy password into notes
        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("password", password.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast toast = Toast.makeText(getApplicationContext(), "Copied!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    // declaring function of generation
    StringBuilder generatePassword(int check, int length){

        String nums = "1234567890";
        String alpha = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnmèòàùì";
        String alphanums = "1234567890QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnmèòàùì";
        String mixed = "1234567890!£$)(/&%.-;,<>#@][*+'?QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnmèòàùì";

        StringBuilder password_gen= new StringBuilder();

        SecureRandom random = new SecureRandom();

        if(check == 1){
            for (int i = 0;i<length;i++){
                int val = random.nextInt(nums.length());
                password_gen.append(nums.charAt(val));
            }
        }else if(check == 2){
            for (int i = 0;i<length;i++){
                int val = random.nextInt(alpha.length());
                password_gen.append(alpha.charAt(val));
            }
        } else{
            for (int i = 0;i<length;i++){
                int val = random.nextInt(mixed.length());
                password_gen.append(mixed.charAt(val));
            }
        }


        return password_gen;
    }


}