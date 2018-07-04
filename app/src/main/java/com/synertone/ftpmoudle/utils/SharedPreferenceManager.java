package com.synertone.ftpmoudle.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author luming 2016/12/9
 * 
 */
public class SharedPreferenceManager {
	
	private static final String  SYNERTONE_A="synertone";

	/**
	 * 保存Ingeter
	 * @param cext
	 * @param key
	 * @param value
	 */
	public static void saveInt(Context cext,String key,int value){
		SharedPreferences sp=cext.getSharedPreferences(SYNERTONE_A, Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}
	
	/**
	 * 获取Integer
	 * @param cext
	 * @param key
	 * @return
	 */
	public static int getInt(Context cext,String key){
		SharedPreferences sp=cext.getSharedPreferences(SYNERTONE_A, Context.MODE_PRIVATE);
	    return sp.getInt(key, -1);
	}
	
	/**
	 * 保存String
	 * @param cext
	 * @param key
	 * @param value
	 */
	public static void saveString(Context cext,String key,String value){
		SharedPreferences sp=cext.getSharedPreferences(SYNERTONE_A, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
	
	/**
	 * 获取String
	 * @param cext
	 * @param key
	 * @return
	 */
	public static String  getString(Context cext,String key){
		SharedPreferences sp=cext.getSharedPreferences(SYNERTONE_A, Context.MODE_PRIVATE);
	    return sp.getString(key,null);
	}
	/**
	 * 保存String
	 * @param cext
	 * @param key
	 * @param value
	 */
	public static void saveBoolean(Context cext,String key,boolean value){
		SharedPreferences sp=cext.getSharedPreferences(SYNERTONE_A, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * 获取String
	 * @param cext
	 * @param key
	 * @return
	 */
	public static boolean  getBoolean(Context cext,String key){
		SharedPreferences sp=cext.getSharedPreferences(SYNERTONE_A, Context.MODE_PRIVATE);
		return sp.getBoolean(key,true);
	}
	

}
