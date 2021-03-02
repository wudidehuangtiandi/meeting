package com.controller;

import com.google.gson.Gson;
import com.pojo.Meeting;
import com.service.meetingServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 年会人员管理
 *
 * @author gc
 * @date 2019年 12月28日 11:19:06
 */

@Controller
@RequestMapping("/meeting")
public class meetingController {

    @Autowired
    private meetingServiceImpl meetingService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/getsit")
    public String getSit() {
        return "sit";
    }

    @RequestMapping("/search")
    public String getSearch() {
        return "search";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Map<String, Object> findAll(@RequestBody String body) { //请求头为A/json
        Gson gson = new Gson();
        Map map = gson.fromJson(body, Map.class);

        Object page1 = map.get("page");
        String s = "";
        if (page1!=null){
             s = page1.toString();
        }


        Object rows1 = map.get("rows");
        String r ="";
        if (rows1!=null){
            r=rows1.toString();
        }


        Object persontype1 = map.get("persontype");
        String pe = persontype1.toString();

        Object customercompanyname1 = map.get("customercompanyname");
        String cucn = customercompanyname1.toString();

        Object customername1 = map.get("customername");
        String cun = customername1.toString();

        Object tablenum1 = map.get("tablenum");
        String tn = tablenum1.toString();

        if (StringUtils.isEmpty(s)) {
            s = "1.0";
        }
        if (StringUtils.isEmpty(r)) {
            r = "30.0";
        }
        Map<String, Object> all = meetingService.findAll(s,r,pe,cucn,cun,tn);
        return all;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload2(HttpServletRequest request, MultipartFile upload) throws IOException, ParseException{//注意 不是解析器哪个对象 是MultipartFile//对象名必须是上传文件的name

        ArrayList<String> String1 = new ArrayList<String>();
        String[] strings = new String[]{};
        strings= new String[]{"persontype", "customertype", "customercompanyname", "customername", "position", "stay", "roomtype", "roomnum", "tablenum", "memo"};
        String1.addAll(Arrays.asList(strings));

        File file = new File("D://Assects");
        if (!file.exists()) {
            file.mkdirs();//创一个文件夹
        }
        String path = "D://Assects";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String format = simpleDateFormat.format(new Date());
        String id = format.replace("-", "");
        String name = upload.getOriginalFilename();//要拿这个名字
        String s = id + "_" + name;
        upload.transferTo(new File(path, s));
        //到这里上传成功下面开始读取

        String filepath = path + "/" + s;
        filepath = filepath.replace("\\", "/");

        File file1 = new File(filepath);
        String1.addAll(Arrays.asList(strings));
        Integer i = meetingService.insertExcel(file1, String1);
        if(i!=0){
            return  i.toString();
        }else {
            return "";
        }

    }

    @RequestMapping(value = "/add",produces = "text/plain;charset=UTF-8" )//配置上produces则要配置对应的编码应为默认就不走自定义的StringHttpMessageConverter了，不配置会变成8859-1
    @ResponseBody
    public String add( Meeting meeting){
        int save = meetingService.save(meeting);
        if (save>0){
            return "新增成功";
        }else {
            return "新增失败";
        }
    }

    @RequestMapping(value = "/del",produces = "text/plain;charset=UTF-8" )
    @ResponseBody
    public String del( String[] ids){
        int delete = meetingService.delete(ids);
        if (delete>0){
            return "删除成功";
        }else {
            return "删除失败";
        }
    }

    @RequestMapping(value = "/updlist" )
    @ResponseBody
    public Meeting del(String id){
        Meeting findinfo = meetingService.findinfo(id);
        return findinfo;
    }

    @RequestMapping(value = "/upd",produces = "text/plain;charset=UTF-8" )
    @ResponseBody
    public String upd( Meeting meeting){
        int upd = meetingService.upd(meeting);
        if (upd>0){
            return "操作成功";
        }else {
            return "操作失败";
        }
    }

    @RequestMapping(value = "/clear",produces = "text/plain;charset=UTF-8" )
    @ResponseBody
    public String clear( ){
        int clear = meetingService.clear();
        if (clear>0){
            return "操作成功";
        }else {
            return "操作失败";
        }
    }

    @RequestMapping(value = "/findselect" )
    @ResponseBody
    public  Map<String, Object> findSelect( ){
        Map<String, Object> persontype = meetingService.findPersontype();
        return persontype;
    }

    @RequestMapping(value = "/sitplan" )
    @ResponseBody
    public  Map<String, Object> findSit( ){
        Map<String, Object> persontype = meetingService.findSit();
        return persontype;
    }


    //外部查询
    @RequestMapping(value = "/searchinfo" )
    @ResponseBody
    public  Map<String, Object> searchInfo(String customername){
        List<Meeting> meetings = meetingService.searchInfo(customername);
        HashMap<String, Object> stringObjectHashMap = new HashMap<String, Object>();
        stringObjectHashMap.put("rows",meetings);
        return stringObjectHashMap;
    }
    //外部查询2
    @RequestMapping(value = "/searchman" )
    @ResponseBody
    public  Map<String, Object> searchman(@RequestBody String body){
        Gson gson = new Gson();
        Map map = gson.fromJson(body, Map.class);

        Object customercompanyname1 = map.get("customercompanyname");
        String cucn = customercompanyname1.toString();

        Object customername1 = map.get("customername");
        String cun = customername1.toString();

        Object tablenum1 = map.get("tablenum");
        String tn = tablenum1.toString();

        Map<String, Object> searchman = meetingService.searchman(cucn, cun, tn);

        return searchman;

    }



}
