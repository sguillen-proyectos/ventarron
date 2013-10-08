package org.inf325.ventarron.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class for Date or Time working
 * 
 * @author donkeysharp
 * 
 */
public class TimeUtil {
	/**
	 * Get the current Unix time
	 * 
	 * @return
	 */
	public static long getUnixTime() {
		long timeStamp = System.currentTimeMillis() / 1000L;
		return timeStamp;
	}

	/**
	 * Converts a Unix time time stamp to a local Date
	 * 
	 * @param timeStamp
	 *            Unix time time stamp
	 * @return
	 */
	public static Date unixTimeToDate(long timeStamp) {
		Date result = new Date(timeStamp * 1000L);
		return result;
	}

	/**
	 * Converts a Date to a dd/MM/yyyy string
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
				Locale.getDefault());
		return dateFormat.format(date);
	}

	/**
	 * Converts a Unix time stamp to a dd/MM/yyyy string
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static String dateToString(long timeStamp) {
		Date date = unixTimeToDate(timeStamp);
		return dateToString(date);
	}
}
