package com.yr.util;


import org.springframework.util.StringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    /**
     * 找到from图片的路径,读出to图片路径的流,用to的流覆盖掉from的流		移动图片
     * @param from
     * @param to
     */
    public static void fileCover(String from, String to){
        if(!StringUtils.isEmpty(from) && !StringUtils.isEmpty(to)){//StringUtils.isEmpty是spring的工具类，可以不为空，也不为Null(注意:可见值一定要写前面)
            File files = new File(from);//需要更改的图片file对象
            File file = new File(to);//需要上传的图片file对象
            if(file.exists()){//判断是否存在
                if(!files.getParentFile().exists()){//如果本地目录没有就创建目录
                    files.getParentFile().mkdirs();
                }
                if(!files.exists()){//如果本地文件不存在就创建文件
                    try {
                        files.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                FileInputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                try {
                    inputStream = new FileInputStream(file);//创建输入流对象
                    byte[] bytes = new byte[inputStream.available()];//创建一个符合输入流中文件长度的byte数组
                    inputStream.read(bytes);//读入byte中
                    fileOutputStream = new FileOutputStream(files);//创建输出流
                    fileOutputStream.write(bytes);//将流覆盖
                    file.delete();//覆盖后将原本的图片删除掉
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        fileOutputStream.close();//关闭流
                        inputStream.close();//关闭流
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 根据文件返回流
     * @param fileName
     */
    public static byte[] getFileFlow(String fileName){
        File file = new File(fileName);
        //将文件读入byte数组
        FileInputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(file);
            data = new byte[(int)file.length()];
            inputStream.read(data);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * 返回当前时间戳，这个是唯一的
     * synchronized 使用户不能同时进入，也就是文件名不可能相同
     * @return Long
     */
    public synchronized static Long getTimeStamp(){
        return System.currentTimeMillis();
    }

    /**
     * 获得文件路径删除
     */
    public static void delete(String path){
        File file = new File(path);
        if(file.exists()){//判断是否存在,存在就删除
            file.delete();
        }
    }
}
