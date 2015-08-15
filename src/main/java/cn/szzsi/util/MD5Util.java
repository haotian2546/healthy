package cn.szzsi.util;

import java.security.MessageDigest;

/**
 * Created by Yishe on 8/15/2015.
 */
public class MD5Util{
    public final static String crypt(String s,String salt){
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try{
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(s.getBytes("UTF-8"));
            mdInst.update(salt.getBytes("UTF-8"));
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for(int i = 0;i < j;i++){
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch(Exception e){
            e.printStackTrace();
            return "asdfsagfdgrtyhfghrdthydfgjfthsfgdbfrtgagr";
        }
    }

    public static void main(String[] args){
        System.out.println(MD5Util.crypt("20121221","ahsashas"));
        System.out.println(MD5Util.crypt("20121221","ahsashas"));
        System.out.println(MD5Util.crypt("20121221","ahsashas"));
        System.out.println(MD5Util.crypt("20121221","ahsashas"));
    }

}
