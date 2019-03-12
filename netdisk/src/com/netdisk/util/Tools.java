package com.netdisk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
	
	/**
	 * 时间戳转化方法 yyyy-MM-dd HH:mm:ss
	 * @param fm
	 * @return
	 */
	public static String getStringDate(long time,String fm) {
		
		Date date = new Date(time);
		if(fm == null) {
			fm = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sf = new SimpleDateFormat(fm);
		
		return sf.format(date);
	}
}
