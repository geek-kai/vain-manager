package com.vain.manager.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5加密
 *
 */
public class MD5Util {
	/**
	 * MD5加密
	 */
	public static String getMD5Str(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "error";
		}
	}

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D','E', 'F' };

	public static String toHexString(byte[] b) { // String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * MD5加密  返回字符串是大写
	 * @param str
	 * @return
	 */
	public static String getMD5Encrypt(String str) {
		try { // Create MD5 Hash
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		System.out.println(MD5Util.getMD5Str("vain123"+"f74518bf-9b68-4f42-a680-eb3abdeaa1d6"));
	}
}
