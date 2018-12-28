package com.guyu.tetris;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewHolder {
    private final SparseArray<View> mViews;
    private final View mConvertView;
    @SuppressWarnings("unused")
	private int mPosition;

    public ViewHolder(Context context, ViewGroup viewGroup, int layoutId, int position){
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup viewGroup, int layoutId, int position) {
        return (new ViewHolder(context, viewGroup, layoutId, position));
    }

    @SuppressWarnings("unchecked")
	public <T extends View> T getView(int layoutId) {
        View view = mViews.get(layoutId);
        if (view == null) {
            view = mConvertView.findViewById(layoutId);
            mViews.put(layoutId, view);
        }
        return (T)view;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
