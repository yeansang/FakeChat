package com.example.nemus.fakechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Vector;


public class MainActivity extends AppCompatActivity {

    EditText memoToUp = null;
    ListView up = null;
    DBConnect dbConnect;
    ArrayAdapter<String> adapter;
    int last=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //초기화
        dbConnect = new DBConnect(getApplicationContext(), "chat.db",null,1);
        memoToUp = (EditText) findViewById(R.id.editText);
        up = (ListView) findViewById(R.id.listView);
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);

        Vector<String> preset = dbConnect.getAll();

        if(preset != null) {
            for (int i = 0; i < preset.size(); i++) {
                adapter.add(preset.elementAt(i));
                last++;
            }
        }

        up.requestFocusFromTouch();
        up.setAdapter(adapter);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            Editable memo = memoToUp.getText();
            memoToUp.setText("");
            adapter.add(memo.toString());

            dbConnect.input(memo.toString());

            adapter.notifyDataSetChanged();
            last++;
            up.setSelection(last);
       }
    }

}
