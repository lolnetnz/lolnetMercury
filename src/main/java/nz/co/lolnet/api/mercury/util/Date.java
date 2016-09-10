package nz.co.lolnet.api.mercury.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {
	
	public static String getDateTime() {
		return getDate() + " " + getTime(); 
	}
	
	public static String getDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return simpleDateFormat.format(calendar.getTime());
	}
	
	public static String getTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		return simpleDateFormat.format(calendar.getTime());
	}
}