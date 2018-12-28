package com.guyu.tetris;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;
import java.util.Set;
public class CacheUtils {
	String fileName;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public CacheUtils(Context context, String fileName)
    {
    	 this.fileName=fileName;
    	 preferences=context.getSharedPreferences(this.fileName,Context.MODE_PRIVATE);
         editor=preferences.edit();
    }
    public void putValue(String key, String value) {
    	editor.putString(key, value);
    	editor.commit();
    }
    @SuppressWarnings("unchecked")
	public void putValue(String key, List<String> value) {
    	editor.putStringSet(key, (Set<String>)value);
    	editor.commit();
    }
    public void putValue(String key,boolean value)
    {
        editor.putBoolean(key,value);
        editor.commit();
    }
    public String getValue(String key,String def)
    {
        return preferences.getString(key,def);
    }
    public void clearCache()
    {
        editor.clear();
        editor.commit();
    }
}
