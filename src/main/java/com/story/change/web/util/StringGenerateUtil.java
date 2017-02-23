package com.story.change.web.util;

/**
 * Created by story on 2017/1/16 0016.
 * 工具类，生成指定字符串
 */
public class StringGenerateUtil {

    /**
     * 生成长度为6的验证码
     * @return
     */
    public static String getPhoneCheck() {
        int i = (int) (Math.random() * 1000000);
        return String.format("%06d", i);
    }

}
