package com.yr.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class ImportExcelUtils {
    /***
     * <pre>uploadFile(springMVC文件上传  传inputStream)      
     * 修改备注：  
     * @param inputs
     * @param fileName
     * @param folderPath
     * @return</pre>
     */
    public static String uploadFile(InputStream inputs, String fileName, String folderPath) {
        // 上传物理文件到服务器硬盘
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        String uploadFileName = null;
        try {
            // 构建输入缓冲区，提高读取文件的速度
            bis = new BufferedInputStream(inputs);
            // 自动建立文件夹
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // 为了保证上传文件的唯一性，可以通过uuid来解决
            // 为了避免中文乱码问题则新生成的文件名由uuid+原来文件名的后缀组成
            uploadFileName = UUID.randomUUID().toString() + getSuffix(fileName);
            // 构建写文件的流即输出流
            fos = new FileOutputStream(new File(folderPath+"/"+uploadFileName));
            // 构建输出缓冲区，提高写文件的性能
            bos = new BufferedOutputStream(fos);
            // 通过输入流读取数据并将数据通过输出流写到硬盘文件夹
            byte[] buffer = new byte[4096];// 构建4k的缓冲区
            int s = 0;
            while ((s=bis.read(buffer)) != -1) {
                bos.write(buffer, 0, s);
                bos.flush();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                    bos = null;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (bis != null) {
                try {
                    bis.close();
                    bis = null;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (inputs != null) {
                try {
                    inputs.close();
                    inputs = null;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
        return uploadFileName;
    }

    /**
     * 后缀名的获取
     * @param fileName
     * @return
     */
    private static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index);
        return suffix;
    }
}