package cn.edu.njupt.tanksms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * MD5
 * 
 * @author liu
 * 
 */
public class MD5Utils {

	/**
	 * 使用MD5对原文进行加密
	 * 
	 * @param value
	 *            原文
	 * @return MD5加密后
	 */
	public static String hex(String value) {
		StringBuilder sb = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] result = digest.digest(value.getBytes());
			sb = new StringBuilder();
			for (byte b : result) {
				String hexString = Integer.toHexString(b & 0xFF);
				if (hexString.length() == 1) {
					sb.append("0" + hexString);// 0~F
				} else {
					sb.append(hexString);
				}
			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

    /**
     * MD5加盐，实则为value+salt进行MD5
     * @param value
     * @param salt
     * @return
     */
    public static String hex_salt(String value ,String salt){
        return MD5Utils.hex(value + salt);
    }

    /**
     * 产生n位随机数，数字，大小写英文混合
     * @param n 位数
     * @return
     */
    public static String randomCode(int n){
        Random r = new Random();
        int i = 0;
        String str = "";
        String s = null;
        while (i < n) {    //这个地方的30控制产生几位随机数，这里是产生30位随机数
            switch (r.nextInt(63)) {
                case (0): s = "0"; break;
                case (1): s = "1"; break;
                case (2): s = "2"; break;
                case (3): s = "3"; break;
                case (4): s = "4"; break;
                case (5): s = "5"; break;
                case (6): s = "6"; break;
                case (7): s = "7"; break;
                case (8): s = "8"; break;
                case (9): s = "9"; break;
                case (10): s = "a"; break;
                case (11): s = "b"; break;
                case (12): s = "c"; break;
                case (13): s = "d"; break;
                case (14): s = "e"; break;
                case (15): s = "f"; break;
                case (16): s = "g"; break;
                case (17): s = "h"; break;
                case (18): s = "i"; break;
                case (19): s = "j"; break;
                case (20): s = "k"; break;
                case (21): s = "m"; break;
                case (23): s = "n"; break;
                case (24): s = "o"; break;
                case (25): s = "p"; break;
                case (26): s = "q"; break;
                case (27): s = "r"; break;
                case (28): s = "s"; break;
                case (29): s = "t"; break;
                case (30): s = "u"; break;
                case (31): s = "v"; break;
                case (32): s = "w"; break;
                case (33): s = "l"; break;
                case (34): s = "x"; break;
                case (35): s = "y"; break;
                case (36): s = "z"; break;
                case (37): s = "A"; break;
                case (38): s = "B"; break;
                case (39): s = "C"; break;
                case (40): s = "D"; break;
                case (41): s = "E"; break;
                case (42): s = "F"; break;
                case (43): s = "G"; break;
                case (44): s = "H"; break;
                case (45): s = "I"; break;
                case (46): s = "L"; break;
                case (47): s = "J"; break;
                case (48): s = "K"; break;
                case (49): s = "M"; break;
                case (50): s = "N"; break;
                case (51): s = "O"; break;
                case (52): s = "P"; break;
                case (53): s = "Q"; break;
                case (54): s = "R"; break;
                case (55): s = "S"; break;
                case (56): s = "T"; break;
                case (57): s = "U"; break;
                case (58): s = "V"; break;
                case (59): s = "W"; break;
                case (60): s = "X"; break;
                case (61): s = "Y"; break;
                case (62): s = "Z"; break;
            }
            i++;
            str = s + str;
        }

        return str ;
    }

}
