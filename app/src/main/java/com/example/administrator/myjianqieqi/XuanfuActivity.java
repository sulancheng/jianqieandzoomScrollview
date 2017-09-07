package com.example.administrator.myjianqieqi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.floatingactionmenu.FloatingActionsMenu;
import toan.android.floatingactionmenu.ObservableScrollView;

public class XuanfuActivity extends Activity {
    @BindView(R.id.scroll_view)
    ObservableScrollView mScrollView;
    @BindView(R.id.list)
    LinearLayout mListContainer;
    @BindView(R.id.multiple_actions)
    FloatingActionsMenu mMenuMultipleActions;
    @BindView(R.id.action_a)
    FloatingActionButton mFloatingActionButtonA;
    @BindView(R.id.action_b)
    FloatingActionButton mFloatingActionButtonB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuanfu);
        ButterKnife.bind(this);
        mMenuMultipleActions.attachToScrollView(mScrollView);
        mMenuMultipleActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("点击","点击到了！");
            }
        });
        mFloatingActionButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuMultipleActions.collapse();
            }
        });
        mFloatingActionButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuMultipleActions.collapse();
            }
        });

    }
}
