package com.example.filepersistencetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  private EditText mEditText;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mEditText = findViewById(R.id.edit);
    String inputText = load();
    if(TextUtils.isEmpty(inputText)){
      mEditText.setText(inputText);
      mEditText.setSelection(inputText.length());
      Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
    }
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    String inputText = mEditText.getText().toString();
    save(inputText);
  }

  private void save(String inputText) {
    FileOutputStream out = null;
    BufferedWriter writer = null;
    try{
      out = openFileOutput("data", Context.MODE_PRIVATE);
      writer = new BufferedWriter(new OutputStreamWriter(out));
      writer.write(inputText);

    }catch (Exception e){
      e.printStackTrace();
    }finally {
      try{
          if(writer != null){
            writer.close();
          }
      }catch (Exception e){
        e.printStackTrace();
      }
    }
  }


  private String load() {
    FileInputStream input = null;
    BufferedReader reader = null;
    StringBuilder content = new StringBuilder();
    try{
      input = openFileInput("data");
      reader = new BufferedReader(new InputStreamReader(input));
      String line = "";
      while((line = reader.readLine()) != null){
        content.append(line);
      }
    }catch (Exception e){
      e.printStackTrace();
    }finally {
      if(reader != null){
        try {
          reader.close();
        }catch (Exception e){
          e.printStackTrace();
        }
      }
    }
    return content.toString();
  }
}