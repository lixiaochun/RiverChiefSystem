package common.util;

import java.security.MessageDigest;
import java.util.UUID;

/**
 *@Title: encryption
 *@Package: uil
 *@Description: encryption.java
 *@author: Sdiver mail 18605916639_wo_cn
 *@date: 2/28/17
 *@version: V1.0
 */
public class Encryption {
    /**
     *@Title: uuidFactory
     *@Description:create uuid
     *@param:
     *@return: String
     *@author: Sdiver
     *@Date: 2/28/17 4:31 PM
     */
    public String uuidFactory(){
        String uuid = UUID.randomUUID().toString();
        String newUuid = uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) +
                uuid.substring(19, 23) + uuid.substring(24);
        return  newUuid;
    }
    /**
     *@Title: MD5
     *@Description:password MD5
     *@param: String s
     *@return: String
     *@author: Sdiver
     *@Date: 2/28/17 4:32 PM
     */
    public String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            //获得MD5摘要算法�? MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘�?
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            //把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
