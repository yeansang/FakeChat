package com.example.nemus.fakechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;


public class MainActivity extends AppCompatActivity {

    EditText inputText = null;
    ListView screen = null;
    DBConnect dbConnect;
    ArrayAdapter<String> adapter;
    int last=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //초기화
        //database 연결
        dbConnect = new DBConnect(getApplicationContext(), "chat2.db",null,1);
        //입력창, 리스트, 리스트 어댑터 생성
        inputText = (EditText) findViewById(R.id.editText);
        screen = (ListView) findViewById(R.id.listView);
        final ArrayList<String> saveWord = new ArrayList<String>();
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,saveWord);

        //초기값 받아오기
        Vector<String> preset = dbConnect.getAll();
        if(preset != null) {
            for (int i = 0; i < preset.size(); i++) {
                adapter.add(preset.elementAt(i));
            }
        }

        //리스트 길게 클릭할때 리스너 설정
        screen.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu pop = new PopupMenu(MainActivity.this, view);
                getMenuInflater().inflate(R.menu.menu_listview,pop.getMenu());

                final int index = position;

                //팝업메뉴 리스너 설정
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.delete){
                            //String juda = adapter.getItem(index);
                            saveWord.remove(index);
                            dbConnect.remove(index);
                            adapter.notifyDataSetChanged();
                        }
                        return false;
                    }
                });
                pop.show();
                return false;
            }
        });

        //리스트 내리기 위해 설정
        screen.requestFocusFromTouch();
        //리스트 어댑터 setting
        screen.setAdapter(adapter);
    }


    public void onClick(View v) {
        if (v.getId() == R.id.button) {

            //텍스트 받아오기
            Editable memo = inputText.getText();
            inputText.setText("");

            //텍스트가 공백이 아닐때
            if(!memo.toString().equals("")) {
                //리스트에 추가
                adapter.add(memo.toString());
                //리스트 갱신
                adapter.notifyDataSetChanged();
                //db에 추가
                last++;
                dbConnect.input(memo.toString(), adapter.getCount()-1);
                //화면 내리기
                screen.setSelection(adapter.getCount()-1);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(),"Input is NULL!!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
       }
    }

}
