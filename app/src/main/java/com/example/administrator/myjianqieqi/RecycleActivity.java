package com.example.administrator.myjianqieqi;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
        init();
    }

    private void init() {
        rlv = findViewById(R.id.rlv);
        //rlv.setLayoutManager(new GridLayoutManager(this, 2));
        GridLayoutManager layoutManager = new GridLayoutManager(this,3, GridLayoutManager.VERTICAL,false);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        List<Myitem> stringing = new ArrayList<>();
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        stringing.add(new Myitem(R.drawable.login_bc));
        HomeAdapter homeAdapter = new HomeAdapter(R.layout.activity_imag, stringing);
        //homeAdapter.notifyItemChanged();
        homeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        homeAdapter.isFirstOnly(false);
        homeAdapter.setNotDoAnimationCount(0);

        homeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecycleActivity.this, "onItemChildClick" + position, Toast.LENGTH_SHORT).show();
            }
        });
            //当设置多种布局的时候，下面一个方法  可以设置每个条目一排显示几条数据。
        //另外书签的文档有描述完善的多布局的使用。
//        gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return getItemViewType(position) == ITEM_TYPE.ITEM_TYPE_Theme.ordinal()
//                        ? gridManager.getSpanCount() : 1;
//            }
//        });
        //成功区分几种不同的recycleview的不同的布局
        rlv.setLayoutManager(layoutManager);
        homeAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                if (position==0){
                    return gridLayoutManager.getSpanCount();//position = 0 的item的weight值为3
                }else {
                    return 1;
                }
            }
        });
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
//            LinearLayout.LayoutParams para = (LinearLayout.LayoutParams) view.getLayoutParams();
//            para.height = 200 + (layoutPosition % 3) * 30;
//            view.setLayoutParams(para);
            helper.addOnClickListener(R.id.iv_imageview);
        }

        @Override
        public int getItemCount() {
            return super.getItemCount();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        protected void startAnim(Animator anim, int index) {
            super.startAnim(anim, index);
            if (index < 2)
                anim.setStartDelay(index * 150);
        }

//        @Override
//        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//            super.onAttachedToRecyclerView(recyclerView);
//            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//            if(manager instanceof GridLayoutManager) {
//                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
//                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                    @Override
//                    public int getSpanSize(int position) {
//                        if (position==2){
//
//                            return 1;
//                        }else {
//                            return 2;
//                        }
//                    }
//                });
//            }
//        }
    }
}
