package com.example.administrator.myjianqieqi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CehuaDelAcivity extends Activity {
    @BindView(R.id.lv_list)
    ListView lv_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cehua_del_acivity);
        ButterKnife.bind(this);
        String[ ] list ={"jhda","dsad","dasdas"};
        lv_list.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,list));
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }
}
