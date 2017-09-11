package com.example.administrator.myjianqieqi;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.myjianqieqi.bean.Myitem;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用一个关于recycle 的adapter 使得recycle变得很简单
 * Created by sucheng
 * on 2017/9/8 14:54
 */
public class RecycleActivity extends Activity {
    public RecyclerView rlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        inIT();
    }

    private void inIT() {
        rlv = (RecyclerView) findViewById(R.id.rlv);
        //rlv.setLayoutManager(new GridLayoutManager(this, 2));
        rlv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        List<Myitem> stringing = new ArrayList<>();
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        HomeAdapter homeAdapter = new HomeAdapter(R.layout.activity_imag, stringing);
        rlv.setAdapter(homeAdapter);
    }
    public class HomeAdapter extends BaseQuickAdapter<Myitem, BaseViewHolder> {
        public HomeAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Myitem item) {
            ImageView view = helper.getView(R.id.iv_imageview);
            int layoutPosition = helper.getLayoutPosition();
            helper.setBackgroundRes(R.id.iv_imageview, item.getBack());
            //手动更改高度，不同位置的高度有所不同
            LinearLayout.LayoutParams para = (LinearLayout.LayoutParams) view.getLayoutParams();
            para.height = 200 + (layoutPosition % 3) * 30;
            view.setLayoutParams(para);
        }

        @Override
        public int getItemCount() {
            return super.getItemCount();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
    }
}
