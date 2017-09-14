package com.example.administrator.myjianqieqi.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/7.
 * <p/>
 * 在安卓API
 * private Map<Integer, View> mViews = new HashMap<Integer, View>();
 * 如果Map的key是 Integer类型的数据Google推荐用SparseArray
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * 一行代码去
 * <p/>
 * if (convertView == null) {
 * <p/>
 * convertView = View.inflate(mContext, R.layout.newspager_item_layout, null);
 * new ViewHolder(convertView);
 * <p/>
 * CommentViewHolder：
 * 1.加载xml布局，缓存到convertView, 不用每次都去加载布局
 * 2.封装xml布局的子控件，不用每次都去findViewById
 * <p/>
 * }
 */
public class CommentViewHolder {


    public static CommentViewHolder getCommentViewHolder(Context context, View convertView, int resource) {

        if (convertView == null) {

            convertView = View.inflate(context, resource, null);
            return new CommentViewHolder(convertView);
        } else {

            return (CommentViewHolder) convertView.getTag();
        }
    }
    public View convertView;

    private CommentViewHolder(View convertView) {

        this.convertView = convertView;
        convertView.setTag(this);
    }

    //集合封装View，键默认是integer.
    private SparseArray<View> mViews = new SparseArray<View>();

    //根据xml的控件的资源id返回对应控件
    public <T extends View> T getView(int resId) {

        View view = mViews.get(resId);
        if (view == null) {

            view = convertView.findViewById(resId);
            mViews.put(resId, view);
        }
        return (T) view;
    }


    public <T extends View> T getView(int resId, Class<T> type) {

        View view = mViews.get(resId);
        if (view == null) {

            view = convertView.findViewById(resId);
            mViews.put(resId, view);
        }
        return (T) view;
    }

    public TextView getTextView(int resId) {

        return getView(resId, TextView.class);
    }

    public ImageView getImageView(int resId) {

        return getView(resId, ImageView.class);
    }
}
