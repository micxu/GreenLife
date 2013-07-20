package com.buaa.greenlife.util;

import java.security.MessageDigest;

public class MD5Util {

	
	    private static String byteArrayToHexString(byte[] bts) {
	          StringBuffer des = new StringBuffer();
	          String tmp = null;

	          for (int i = 0; i < bts.length; i++) {
	          tmp = (Integer.toHexString(bts[i] & 0xFF));
	          if (tmp.length() == 1) {
	             des.append("0");
	          }
	           des.append(tmp);
	       }
	        return des.toString();
	    }
	    
    public synchronized static String encodeByMD5(String originString) {
	      if (originString != null) {
	         try {
	                 MessageDigest md = MessageDigest.getInstance("MD5");
	                 byte[] results = md.digest(originString.getBytes());
	                 String resultString = byteArrayToHexString(results);
	            return resultString.toUpperCase();
	         } catch (Exception ex) {
	            ex.printStackTrace();
	         }
	      }
	      return originString;
	   }
}
