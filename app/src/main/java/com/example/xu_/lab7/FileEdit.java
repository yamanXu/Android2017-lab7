package com.example.xu_.lab7;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xu_ on 2017/12/8.
 */

public class FileEdit extends AppCompatActivity {

    EditText ET_fileTitle;
    EditText ET_fileContent;
    Button BT_save;
    Button BT_load;
    Button BT_clear;
    Button BT_delete;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_edit);

        ET_fileTitle = (EditText)findViewById(R.id.fileTitle);
        ET_fileContent = (EditText)findViewById(R.id.fileContent);
        BT_save = (Button)findViewById(R.id.save);
        BT_load = (Button)findViewById(R.id.load);
        BT_clear = (Button)findViewById(R.id.clear);
        BT_delete = (Button)findViewById(R.id.delete);

        BT_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try(FileOutputStream fileOutputStream = openFileOutput(ET_fileTitle.getText().toString(),MODE_PRIVATE)){
                    String content = ET_fileContent.getText().toString();
                    fileOutputStream.write(content.getBytes());
                    Toast.makeText(FileEdit.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                    Log.i("TAG", "Successfully saved file.");
                }catch (IOException ex){
                    Toast.makeText(FileEdit.this, "Fail to save file", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Fail to save file.");
                }
            }
        });
        BT_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try(FileInputStream fileInputStream = openFileInput(ET_fileTitle.getText().toString())){
                    byte[] content_byte = new byte[fileInputStream.available()];
                    fileInputStream.read(content_byte);
                    String contents = new String(content_byte);
                    ET_fileContent.setText(contents);
                    Toast.makeText(FileEdit.this, "Load successfully", Toast.LENGTH_SHORT).show();
                    Log.i("TAG", "Successfully read file.");
                }catch (IOException ex){
                    Toast.makeText(FileEdit.this, "Fail to read file", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Fail to read File.");
                }
            }
        });
        BT_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ET_fileContent.setText("");
            }
        });
        BT_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FileEdit.this.getApplicationContext().deleteFile(ET_fileTitle.getText().toString())){
                    Toast.makeText(FileEdit.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(FileEdit.this, "Fail to delete file", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FileEdit.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            finish();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
