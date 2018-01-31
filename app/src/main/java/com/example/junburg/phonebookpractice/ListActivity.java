package com.example.junburg.phonebookpractice;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ListActivity extends AppCompatActivity {
    private static final int ADD_USER = 20; // DetailActivty로 부터 값을 반환받기 위해 사용되는 인텐트의 상수
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // 머터리얼 디자인에 사용되는 플로팅 액션 버, UI 위에 떠있는 듯한 효과를 줌
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floating_action_btn);
        /*
         플로팅 액션 버튼을 클릭하면 DetailActivity로 이동하고, DetailActivity로 부터 값을 반환 받기 위해
         startActivityForResult함수 사용
        */

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                startActivityForResult(intent, ADD_USER);
            }
        });
    }
}
