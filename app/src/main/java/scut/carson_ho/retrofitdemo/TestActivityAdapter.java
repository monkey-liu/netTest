package scut.carson_ho.retrofitdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import scut.carson_ho.retrofitdemo.bean.ResultListBean;

/**
 * Created by 圆子弹 on 2018/3/15.
 */

public class TestActivityAdapter extends BaseAdapter {

    protected Context mContext;
    protected ArrayList<ResultListBean> mValues;
    protected LayoutInflater mInflater;

    public TestActivityAdapter(Context context, ArrayList<ResultListBean> list) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mValues = list;
    }

    public Context getContext() {
        return mContext;
    }
    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=new ViewHolder();
        view=mInflater.inflate(R.layout.activity_item,null);
        holder.tv_activity=(TextView)view.findViewById(R.id.tv_activity);
        holder.tv_activity.setText(mValues.get(mValues.size()-1-i).name+": "+mValues.get(mValues.size()-1-i).score);
        return view;
    }
    public ArrayList<ResultListBean> getList() {
        return mValues;
    }


    public void update(ArrayList<ResultListBean> values) {
        mValues = values;
        notifyDataSetInvalidated();
        notifyDataSetChanged();
    }


    public class ViewHolder {
        TextView tv_activity;
    }
}
