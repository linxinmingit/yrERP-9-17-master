package com.yr.common.service.impl;

import com.yr.common.service.UploadServer;
import com.yr.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Service
public class UploadServerImpl implements UploadServer{
    public static String path = "C:\\Users\\Administrator\\Desktop\\photo";//图片路径

    /**
     * 将图片传入服务器tomcat，返回一个路径
     * @param file
     * @return String
     */
    public String upload(MultipartFile file, ServletContext servletContext) throws Exception {
        Long startTime = FileUtils.getTimeStamp();//获得当前时间的时间戳
        String path = servletContext.getRealPath("/") + "photos";//获取服务器路径         --这里要改成服务器的路径
        String fileName = file.getOriginalFilename();//获得文件名
        fileName = startTime + fileName.substring(fileName.lastIndexOf("."), fileName.length());//构建出一个唯一的文件名
        File filepath=new File(path,fileName);//构建成一个file对象
        //判断目标文件所在的目录是否存在
        if(!filepath.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            filepath.getParentFile().mkdirs();
        }
        //将内存中的数据写入磁盘
        file.transferTo(filepath);
        String photoPath = servletContext.getContextPath() + File.separator + "photos" + File.separator + fileName;
        return photoPath;
    }

    /**
     * 上传到服务器
     * @return String
     */
    public String uploadServer(String filesCopy, String path){
        String phone = String.valueOf(FileUtils.getTimeStamp());
        File file = new File(path, phone + ".jpg");//第一个是父级文件路径，第二个是文件名
        if(!file.getParentFile().exists()){//判断父级路径是否存在
            file.mkdir();//创建文件夹
        }
        if(!file.exists()){//如果文件不存在满足条件
            try {
                file.createNewFile();//创建该文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String phoneStr = this.path + File.separator + phone + ".jpg";//组成一个图片的路径字符串
        //截取指定路径组成一个本地路径
        if(!filesCopy.equals("E:\\idea\\yrERP\\yrERP-9-17\\src\\main\\webapp\\images\\587c589d26802.jpg")){//这是默认显示的图片路径
            filesCopy = path + "photos" + filesCopy.substring(filesCopy.lastIndexOf("\\"),filesCopy.length());
        }
        FileUtils.fileCover(phoneStr, filesCopy);//将读取的流覆盖创建的图片
        return phoneStr;
    }

    /**
     * 将流上传到服务器
     * @return String
     */
    public String upload(MultipartFile file){
        Long startTime = FileUtils.getTimeStamp();//获得当前时间的时间戳
        String fileName = file.getOriginalFilename();//获得文件名
        fileName = startTime + fileName.substring(fileName.lastIndexOf("."), fileName.length());//构建出一个唯一的文件名
        File filepath = new File("/static",fileName);//构建成一个file对象
        //判断目标文件所在的目录是否存在
        if(!filepath.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            filepath.getParentFile().mkdirs();
        }
        //将内存中的数据写入磁盘
        try {
            file.transferTo(filepath);
        }catch (Exception e){
            e.printStackTrace();
        }
        String photoPath = "http://192.168.1.77/static" + File.separator + fileName;
        return photoPath;
    }

}
