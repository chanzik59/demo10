package com.example.batch;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @author chenzhiqin
 * @date 4/5/2023 16:44
 * @info XX
 */
public class Test {
    static String charset="GBK";

    public static void main(String[] args) throws UnsupportedEncodingException {
        String abc = "中国国  发展交afdsafdsa5431132中国国发展交中国国发展交中国国发展交中国国发展交中国国发展交通中国发展交通中国发展交通银行有限公司";

        String s1 = sub(abc, charset, 40);
        String s = subByByteSize(abc, charset, 40);
        System.out.println(s.getBytes(charset).length);

        System.out.println(s);
        System.out.println(s1.getBytes(charset).length);
        System.out.println(s1);
        System.out.println("m3");
        System.out.println("基准");

    }


    public static String sub(String content, String charset, int maxSize) throws UnsupportedEncodingException {

        if (content.getBytes(charset).length <= maxSize || !StringUtils.hasLength(content)) {
            return content;
        }
        String temp = "";
        for (char aChar : content.toCharArray()) {
            temp += aChar;
            if (temp.getBytes(charset).length > 40) {
                return temp.substring(0, temp.length() - 1);
            }
        }
        return content;
    }


    public static String subByByteSize(String result, String charset, int maxSize) {
        try {
            if (StringUtils.isEmpty(result)) {
                return result;
            }
            byte[] bytes = result.getBytes(Charset.forName(charset));
            if (bytes.length > maxSize) {
                result = new String(bytes, 0, maxSize, charset);
                result = result.getBytes(Charset.forName(charset)).length > maxSize ? result.substring(0, result.length() - 1) : result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.replaceAll("�","");
    }
}
