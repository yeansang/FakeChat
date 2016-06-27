package com.example.nemus.fakechat;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    EditText memoToUp = null;
    TextView up = null;
    String saveText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memoToUp = (EditText) findViewById(R.id.editText);
        up = (TextView) findViewById(R.id.textView);
        up.setText("");
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            Editable memo = memoToUp.getText();
            //saveText += memo.toString() + "\n";
            //up.setText(saveText);
            up.append(memo.toString()+"\n");
       }
    }

}
