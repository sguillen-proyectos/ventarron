package org.inf325.ventarron.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
	/**
	 * Calculates the md5 sum for a given string
	 * {@link http://stackoverflow.com/questions/4846484/md5-or-other-hashing-in-android}
	 * @param message
	 * @return
	 */
	public static String calculateMD5(final String message) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(message.getBytes());
			byte[] messageDigest = digest.digest();
			
			StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < messageDigest.length; i++) {
	            String h = Integer.toHexString(0xFF & messageDigest[i]);
	            while (h.length() < 2)
	                h = "0" + h;
	            hexString.append(h);
	        }
	        return hexString.toString();
		
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
