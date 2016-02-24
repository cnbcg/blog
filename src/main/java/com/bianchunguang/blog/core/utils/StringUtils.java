package com.bianchunguang.blog.core.utils;

import javax.servlet.http.HttpServletRequest;

public class StringUtils {

    public static String getRootUrl(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        return basePath;
    }
}
