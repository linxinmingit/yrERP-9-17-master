package com.yr.depot.controller;

import com.yr.core.redis.JedisManager;
import com.yr.depot.service.WareTypeService;
import com.yr.entitys.Log.Log;
import com.yr.entitys.bo.depotBo.WareBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.depot.Ware;
import com.yr.depot.service.WareService;
import com.yr.entitys.user.User;
import com.yr.log.service.LogService;
import com.yr.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * 仓库商品的controller层
 */
@Controller
@RequestMapping(value = "wares")
public class WareController {
    @Autowired
    private WareService ws;
    @Autowired
    private JedisManager jedisManager;
    public static String path = "C:/Users/Administrator/Desktop/photo";//图片路径
    @Autowired
    private WareTypeService wts;
    @Autowired
    @Qualifier("logServiceImpl")
    private LogService logServices;//日志

    @ModelAttribute
    public void Pojo(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("wareBo", ws.getWare(id));
        }
    }

    /**
     * 用于跳转数据查询页面
     *
     * @return
     */
    @RequestMapping(value = "waresTable", method = RequestMethod.GET)
    public String getListPage() {
        return "wareList";
    }

    /**
     * 用于跳转到添加页面和修改页面
     *
     * @return
     */
    @RequestMapping(value = "waresTable/add", method = RequestMethod.GET)
    public String getAddPage(Map<String, Object> map) {
        map.put("wareBo", new Ware());
        map.put("wares", wts.getWareType());
        return "wareAU";
    }

    /**
     * 用于跳转到添加页面和修改页面
     *
     * @return
     */
    @RequestMapping(value = "waresTable/{id}", method = RequestMethod.GET)
    public String getUpdatePage(@PathVariable Integer id, Map<String, Object> map) {
        map.put("wareBo", ws.getWare(id));
        map.put("wares", wts.getWareType());
        return "wareAU";
    }

    /**
     * 商品添加方法，用于前台添加数据
     *
     * @param ware
     * @return
     */
    @RequestMapping(value = "waresTable", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addWare(@ModelAttribute("wareBo") Ware ware, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        ware.setCreateEmp(user.getName());
        boolean bool = false;
        try {
            bool = ws.add(ware);
            Log log = new Log();
            log.setModular("仓库商品模块");
            log.setTable("wares");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(1);
            //log.setFieldOldValue();  //新增数据忽略前置
            //log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
        } catch (Exception e) {
            Log log = new Log();
            log.setModular("仓库商品模块");
            log.setTable("wares");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            //log.setFieldOldValue();  //新增数据忽略前置
            //log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
            e.printStackTrace();
        }
        if (bool) {
            return "{\"code\":1,\"msg\":\"添加成功\"}";
        } else {
            return "{\"code\":2,\"msg\":\"添加失败\"}";
        }
    }

    /**
     * 实现静态头像上传，这里先将图片上传到服务器上
     *
     * @param file
     * @param request
     * @param response
     * @return Map<String                                                               ,                                                               Object>
     * @throws IOException
     */
    @RequestMapping(value = "waresTable/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam("files") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long startTime = FileUtils.getTimeStamp();//获得当前时间的时间戳
        String path = request.getServletContext().getRealPath("/") + "photos";//获取服务器路径         --这里要改成服务器的路径
        String fileName = file.getOriginalFilename();//获得文件名
        fileName = startTime + fileName.substring(fileName.lastIndexOf("."), fileName.length());//构建出一个唯一的文件名
        File filepath = new File(path, fileName);//构建成一个file对象
        //判断目标文件所在的目录是否存在
        if (!filepath.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            filepath.getParentFile().mkdirs();
        }
        //将内存中的数据写入磁盘
        file.transferTo(filepath);
        Map<String, Object> map = new HashMap<>();
        map.put("url", request.getServletContext().getContextPath() + File.separator + "photos" + File.separator + fileName);
        return map;
    }

    /**
     * 根据id来删除商品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "waresTable/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteWare(@PathVariable Integer[] id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        boolean bool = false;
        try {
            bool = ws.delete(id);
            Log log = new Log();
            log.setModular("仓库商品模块");
            log.setTable("wares");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(2);
            //log.setFieldOldValue();  //新增数据忽略前置
            //log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
        } catch (Exception e) {
            Log log = new Log();
            log.setModular("仓库商品模块");
            log.setTable("wares");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            //log.setFieldOldValue();  //新增数据忽略前置
            //log.setFieldNewValue(purchaseOrderBo.getPurchaseOrder().toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
            e.printStackTrace();
        }
        if (bool) {
            return "{\"code\":1,\"msg\":\"删除成功\"}";
        } else {
            return "{\"code\":2,\"msg\":\"删除失败\"}";
        }
    }

    /**
     * 根据id来修改商品的信息
     *
     * @param ware
     * @param map
     * @return
     */
    @RequestMapping(value = "waresTable", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateWare(@ModelAttribute("wareBo") Ware ware, Map<String, Object> map, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String oldWare = ws.getWare(ware.getId()).toString();
        ware.setUpdateEmp(user.getName());
        boolean bool = false;
        try {
            bool = ws.update(ware);
            Log log = new Log();
            log.setModular("仓库商品模块");
            log.setTable("wares");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(3);
            //修改前的值
            log.setFieldOldValue(oldWare);  //新增数据忽略前置
            //修改后的值
            log.setFieldNewValue(ware.toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
        } catch (Exception e) {
            Log log = new Log();
            log.setModular("仓库商品模块");
            log.setTable("wares");
            //模块的操作类型（0抛异常，1新增，2删除，3修改，4用户登录，5用户退出）
            log.setType(0);
            //修改前的值
            // log.setFieldOldValue(oldWare);  //新增数据忽略前置
            //修改后的值
            // log.setFieldNewValue(ware.toString());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            log.setCreateEmp(user.getName());
            logServices.addLog(log);
            e.printStackTrace();
        }
        if (bool) {
            return "{\"code\":1,\"msg\":\"修改成功\"}";
        } else {
            return "{\"code\":2,\"msg\":\"修改失败\"}";
        }
    }

    /**
     * 商品查询方法，前台可以通过这个方法进行数据的查询
     *
     * @param ware
     * @param map
     * @return 查询数据
     */
    @RequestMapping(value = "waresTable/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryWare(Page<WareBo> ware, WareBo wares, Map<String, Object> map) {
        ware.setT(wares);
        String json = ws.query(ware);
        map.put("wareBo", json);
        return json;
    }

    /**
     * 用于判断编号是否存在
     *
     * @param ware
     * @return
     */
    @RequestMapping(value = "waresTable/checkTypeCode", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String checkTypeCode(Ware ware) {
        boolean bool = ws.getWareByCode(ware);
        if (bool) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 通过用户的id请求返回图像的字节流
     * <p>
     * 不需要
     */
    @RequestMapping("/waresTable/icons/{id}")
    public void getIcons(@PathVariable(value = "id") Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Ware ware = ws.getWare(id);//根据id获得user对象
        byte[] data = FileUtils.getFileFlow(ware.getWarePhoto());//调用方法将流传出
        response.setContentType("image/png");
        OutputStream stream = response.getOutputStream();
        stream.write(data);//将图片以流的形式返回出去
        stream.flush();
        stream.close();
    }
}
