package com.guyu.tetris;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import java.util.List;

@SuppressWarnings("rawtypes")
public class BlockAdapter extends CommonAdapter{
    Context context;
    List<Integer> mDatas;
    @SuppressWarnings("unchecked")
	public BlockAdapter( Context context, List mDatas, int mLayoutId )
    {
        super(context, mDatas, mLayoutId);
        this.context	= context;
        this.mDatas	= mDatas;
    }
    @Override
    public void convert( ViewHolder helper, Object item )
    {
        ImageView imageView = helper.getView( R.id.adapter_image );

        Integer integer = (Integer) item;
        if ( integer > 0 )
        {
            imageView.setImageResource( Square.color[integer - 1] );
        }else {
            imageView.setBackgroundColor( Color.parseColor( "#29505B" ) );
        }
    }
}
