package com.omid.osw.common.utils;

public class StringUtils {

    /**
     * 문자열이 null인지, 비어 있는지, 공백인지 확인
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 문자열이 null이 아닌지, 비어 있지 않은지, 공백이 아닌지 확인
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
