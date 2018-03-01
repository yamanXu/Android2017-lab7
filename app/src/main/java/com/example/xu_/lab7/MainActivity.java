package com.example.xu_.lab7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText ET_newPass;
    EditText ET_confirmPass;
    Button BT_OK;
    Button BT_CLEAR;
    CheckBox CB_newPass;
    CheckBox CB_confirmPass;

    public static int MODE = MODE_PRIVATE;
    public static final String PREFERENCE_NAME = "SavePassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BT_OK = (Button)findViewById(R.id.ok);
        BT_CLEAR = (Button)findViewById(R.id.clear);
        ET_newPass = (EditText)findViewById(R.id.newPassword);
        ET_confirmPass = (EditText)findViewById(R.id.confirmPassword);
        CB_newPass = (CheckBox)findViewById(R.id.newCheckBox);
        CB_confirmPass = (CheckBox)findViewById(R.id.confirmCheckBox);


        sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
        if(sharedPreferences.getString("Password","").equals("")){
            firstStart();
        }
        else{
            ET_confirmPass.setVisibility(View.INVISIBLE);
            CB_confirmPass.setVisibility(View.INVISIBLE);
            ET_newPass.setHint("Password");
            ET_newPass.setText("");
            secondStart();
        }
    }

    public void firstStart(){
        BT_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = ET_newPass.getText().toString();
                String confirmPassword = ET_confirmPass.getText().toString();
                if(newPassword.equals(""))
                    Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                else if(!newPassword.equals(confirmPassword))
                    Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Password", newPassword);
                    editor.commit();
                    Toast.makeText(MainActivity.this, "Password Match", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, FileEdit.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        BT_CLEAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ET_newPass.setText("");
                ET_confirmPass.setText("");
            }
        });
        CB_newPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    ET_newPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    ET_newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        CB_confirmPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    ET_confirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    ET_confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    public void secondStart(){
        BT_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = ET_newPass.getText().toString();
                String password = sharedPreferences.getString("Password", "");
                if(input.equals(""))
                    Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                else if(!input.equals(password))
                    Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(MainActivity.this, "Password Match", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, FileEdit.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, 1);
                }
            }
        });
        BT_CLEAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ET_newPass.setText("");
            }
        });
        CB_newPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    ET_newPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    ET_newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if((Intent.FLAG_ACTIVITY_CLEAR_TOP &intent.getFlags())!=0){
            finish();
        }
    }
    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            finish();
//            return true;
//        }
//        return false;
//    }
}
