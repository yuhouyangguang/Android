package com.guyu.tetris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    @SuppressWarnings("unused")
	private final LayoutInflater mInflater;
    private final int mItemLayoutId;
    Context mContext;
    List<T> mDatas;

    public CommonAdapter(Context context, List<T> mDatas, int mLayoutId)
    {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
        this.mItemLayoutId = mLayoutId;
    }

    public void setDatas(List<T> datas){
        if (datas != null){
            this.mDatas = datas;
            notifyDataSetChanged();
        }
    }

    public void addDatas(List<T> datas){
        if (datas != null){
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void addData(T data){
        if(data!=null){
            mDatas.add(data);
            notifyDataSetChanged();
        }
    }

    public void deleteData(T data){
        if (mDatas!=null){
            mDatas.remove(data);
        }
    }

    public void clearData(){
        if(mDatas!=null){
            mDatas.clear();
            notifyDataSetChanged();
        }
    }
    protected List<T> getData(){
        return mDatas;
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder,getItem(position));
        return viewHolder.getConvertView();
    }
    public abstract void convert(ViewHolder helper,T item);
    public ViewHolder getViewHolder(int position, View convertView, ViewGroup parent)
    {
        return ViewHolder.get(mContext,convertView,parent,mItemLayoutId,position);
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }
}
