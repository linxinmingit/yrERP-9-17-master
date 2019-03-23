package com.yr.supplier.controller;

import com.yr.common.service.UploadServer;
import com.yr.core.redis.JedisManager;
import com.yr.entitys.bo.supplierBO.SupplierBo;
import com.yr.entitys.bo.supplierBO.SupplierWareBo;
import com.yr.entitys.page.Page;
import com.yr.entitys.supplier.SuppWareType;
import com.yr.entitys.supplier.SupplierWares;
import com.yr.supplier.service.SuppWareTypeService;
import com.yr.supplier.service.SupplierWareService;
import com.yr.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *供应商品的controller层，用于后台代码和前台页面交互
 */
@Controller
@RequestMapping(value = "supp_wares")
public class SupplierWareController {
    @Autowired
    private SupplierWareService sws;
    @Autowired
    private SuppWareTypeService swsType;
    @Autowired
    private UploadServer uploadServerImpl;
    @Autowired
    private JedisManager jedisManager;
    public static String path = "C:/Users/Administrator/Desktop/photo";//图片路径//上传到的地方
    @ModelAttribute
    public void Pojo (@RequestParam(value="id",required = false)Integer id, Map<String,Object> map){
        if (id != null&&id !=0) {
            SupplierWareBo supplierWareBo=sws.getById(id);
            map.put("supplierWareBo",supplierWareBo);
        }
    }
    /**
     * 跳转到拥有供应商的查询列表，没有数据操作
     * @return
     */
    @RequestMapping(value = "/supplierTable",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    public String list(){
        return "supp_waresList";
    }
    /**
     * 供应商品查询方法，前台可以通过这个方法进行数据的查询
     * @param page
     * @param supplierWareBo
     * @return 查询数据
     */
    @RequestMapping(value = "/supplierTable/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryWare(Page<SupplierWareBo> page,SupplierWareBo supplierWareBo){
        page.setT(supplierWareBo);
        String json = sws.query(page);
        return json;
    }

    /**
     * 用于跳转到添加页面和修改页面
     * @return
     */
    @RequestMapping(value = "/supplierTable/add",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    public String getAddPage(Map<String , Object>map){
        map.put("supplierWareBo",new SupplierWareBo());
        List<SuppWareType> listType=swsType.getSuppWareType();
        map.put("typeList",listType);

        return "supp_waresAU";
    }

    /**
     * 供应商品添加方法，用于前台添加数据
     * @param supplierWareBo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/supplierTable",method =RequestMethod.POST,produces="application/json;charset=UTF-8")
    public String addWare(SupplierWareBo supplierWareBo, @RequestParam(value="filesCopy",required = false) String filesCopy, HttpServletRequest request){

        supplierWareBo.getSupplierWare().setCreateTime(new Date());
        supplierWareBo.getSupplierWare().setSuppPhoto(filesCopy);

        sws.add(supplierWareBo);
        return "{\"code\":1,\"msg\":\"添加成功\"}";
    }


    /**
     * 实现静态头像上传，这里先将图片上传到服务器上
     * @param file
     * @param request
     * @param response
     * @return Map<String,Object>
     * @throws IOException
     */
    @RequestMapping(value="/supplierTable/upload",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadFile(@RequestParam("files") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = uploadServerImpl.upload(file);
        Map<String,Object> map = new HashMap<>();
        map.put("url",url);
        return map;
    }
    /**
     * 根据id来删除供应商品
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/supplierTable/{id}",method = RequestMethod.DELETE,produces="application/json;charset=UTF-8")
    public String deleteWare(@PathVariable("id") Integer id){
        sws.delete(id);
        return "{\"code\":1,\"msg\":\"删除成功\"}";
    }


    /**
     * 根据id回显修改供应商商品数据
     * @param id
     * @param map 放入map中存放request，方便页面拿取
     * @return
     */
    @RequestMapping(value = "/supplierTable/{id}",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    public String upEcho(@PathVariable Integer id,Map<String, Object> map) {
        List<SuppWareType> listType=swsType.getSuppWareType();
        map.put("typeList",listType);
        map.put("supplierWareBo",sws.getById(id));
        return "supp_waresAU";
    }
    /**
     * 根据id来修改供应商品的信息
     * @param supplierWareBo
     * @param map
     * @return
     */
    @RequestMapping(value = "/supplierTable",method = RequestMethod.PUT,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String updateWare(SupplierWareBo supplierWareBo,@RequestParam(value="filesCopy",required = false) String filesCopy, Map<String,Object>map){
        supplierWareBo.getSupplierWare().setUpdateTime(new Date());
        supplierWareBo.getSupplierWare().setSuppPhoto(filesCopy);
        sws.update(supplierWareBo);
        return "{\"code\":1,\"msg\":\"修改成功\"}";
    }

    /**
     * 通过用户的id请求返回图像的字节流
     */
    @RequestMapping("supplierTable/icons/{id}")
    public void getIcons(@PathVariable(value="id") Integer id , HttpServletRequest request, HttpServletResponse response) throws IOException {
        SupplierWareBo supplierWareBo  = sws.getById(id);//根据id获得user对象
        byte[] data = FileUtils.getFileFlow(supplierWareBo.getSupplierWare().getSuppPhoto());//调用方法将流传出
        response.setContentType("image/png");
        OutputStream stream = response.getOutputStream();
        stream.write(data);//将图片以流的形式返回出去
        stream.flush();
        stream.close();
    }
}
