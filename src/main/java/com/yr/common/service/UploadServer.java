package com.yr.common.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

public interface UploadServer {

    /**
     * 将图片传入服务器tomcat，返回一个路径
     * @param file
     * @return String
     */
    String upload(MultipartFile file, ServletContext servletContext) throws Exception;

    /**
     * 上传到服务器
     * @return String
     */
    String uploadServer(String filesCopy, String path);

    /**
     * 将流上传到服务器
     * @param file
     * @return String
     */
    String upload(MultipartFile file);
}
