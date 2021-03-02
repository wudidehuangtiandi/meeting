package com.service;

import com.dao.meetingDao;
import com.pojo.Meeting;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 年会人员管理
 *
 * @author gc
 * @date 2019年 12月28日 13:41:41
 */
@Service
public class meetingServiceImpl {

    @Autowired
    private meetingDao meetingDao;

    public Map<String,Object> findAll(String pageNumber, String pageSize,String pe, String cucn,String cun,String tn) {
        int p1 = Integer.parseInt(pageNumber.substring(0,pageNumber.lastIndexOf(".")));
        int p2 = Integer.parseInt(pageSize.substring(0,pageSize.lastIndexOf(".")));

        Integer start =(p1-1)*p2;
        Integer end = p1*p2;
        String s = start.toString();
        String s1 = end.toString();
        List<Meeting> all = meetingDao.findAll(s, s1,pe,cucn,cun,tn);

        Integer totalcount = meetingDao.getTotalcount(pe,cucn,cun,tn);


        HashMap<String, Object> stringObjectHashMap = new HashMap<String, Object>();
        stringObjectHashMap.put("rows",all);
        stringObjectHashMap.put("total",totalcount);
        return stringObjectHashMap;

    }

    public int insertExcel(File file, List<String> list)  {
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(file);
        } catch (IOException e) {
            return 0;
        } catch (BiffException e) {
            return 0;
        }
        //选择第一个工作表
        Sheet sheet = workbook.getSheet(0);
        //读取单元格

        List<Meeting> list1 = new ArrayList<Meeting>();
        int m = 0;
        try {
            for (int i = 1; i < sheet.getRows(); i++) {//第一行为标题不需要,
               Meeting meeting = new Meeting();

                Cell persontype = sheet.getCell(0, i);
                String persontype1 = persontype.getContents();
                if (StringUtils.isNotEmpty(persontype1)) {
                    meeting.setPersontype(persontype1);
                } else {
                    meeting.setPersontype("");
                }

                Cell customertype = sheet.getCell(1, i);
                String customertype1 = customertype.getContents();
                if (StringUtils.isNotEmpty(customertype1)) {
                    meeting.setCustomertype(customertype1);
                } else {
                    meeting.setCustomertype("");
                }

                Cell customercompanyname = sheet.getCell(2, i);
                String customercompanyname1 = customercompanyname.getContents();
                if (StringUtils.isNotEmpty(customercompanyname1)) {
                    meeting.setCustomercompanyname(customercompanyname1);
                } else {
                    meeting.setCustomercompanyname("");
                }

                Cell customername = sheet.getCell(3, i);
                String customername1 = customername.getContents();
                if (StringUtils.isNotEmpty(customername1)) {
                    meeting.setCustomername(customername1);
                } else {
                    meeting.setCustomername("");
                }

                Cell position = sheet.getCell(4, i);
                String position1 = position.getContents();
                if (StringUtils.isNotEmpty(position1)) {
                    meeting.setPosition(position1);
                } else {
                    meeting.setPosition("");
                }

                Cell stay = sheet.getCell(5, i);
                String stay1 = stay.getContents();
                if (StringUtils.isNotEmpty(stay1)) {
                    meeting.setStay(stay1);
                } else {
                    meeting.setStay("");
                }

                Cell roomtype = sheet.getCell(6, i);
                String roomtype1 = roomtype.getContents();
                if (StringUtils.isNotEmpty(roomtype1)) {
                    meeting.setRoomtype(roomtype1);
                } else {
                    meeting.setRoomtype("");
                }

                Cell roomnum = sheet.getCell(7, i);
                String roomnum1 = roomnum.getContents();
                if (StringUtils.isNotEmpty(roomnum1)) {
                    meeting.setRoomnum(roomnum1);
                } else {
                    meeting.setRoomnum("");
                }

                Cell tablenum = sheet.getCell(8, i);
                String tablenum1 = tablenum.getContents();
                if (StringUtils.isNotEmpty(tablenum1)) {
                    meeting.setTablenum(tablenum1);
                } else {
                    meeting.setTablenum("");
                }

                Cell memo = sheet.getCell(9, i);
                String memo1 = memo.getContents();
                if (StringUtils.isNotEmpty(memo1)) {
                    meeting.setMemo(memo1);
                } else {
                    meeting.setMemo("");
                }



                list1.add(meeting);
            }
            workbook.close();

            for (Meeting meeting : list1) {
                int i =meetingDao.insert(meeting);//通用mapper直接解决了返回值问题，不然返回id需要单独定义

                m+=i;
            }
            return m;
        } catch (Exception e) {
            return 0;
        }

    }

    public int save(Meeting meeting) {
        int insert = meetingDao.insert(meeting);
        return insert;
    }

    public int delete(String[] ids) {
        int a =0;
        for (String id : ids) {
            int i1 = Integer.parseInt(id);
            int i = meetingDao.deleteByPrimaryKey(i1);
            a+=i;
        }
        return a;

    }

    public Meeting findinfo(String id) {
        int i = Integer.parseInt(id);
        Meeting meeting = meetingDao.selectByPrimaryKey(i);
        return meeting;
    }

    public int upd(Meeting meeting) {
        //null会被更新
        int i = meetingDao.updateByPrimaryKey(meeting);
        return i;
    }

    public Map<String,Object> findPersontype() {
        List<String> persontype = meetingDao.findPersontype();
        HashMap<String, Object> stringObjectHashMap = new HashMap<String, Object>();
        stringObjectHashMap.put("type",persontype);
        return stringObjectHashMap;
    }

    public Map<String,Object> findSit() {
        List<Map<String, Object>> findsit = meetingDao.findsit();
        HashMap<String, Object> map = new LinkedHashMap<String, Object>();//hashmap无序linkedhashmap有序
        for (Map<String, Object> stringObjectMap : findsit) {
            Object tablenum = stringObjectMap.get("tablenum");
            String s = tablenum.toString();
            List<String> byTablenum = meetingDao.findByTablenum(s);
            map.put(s,byTablenum);
        }
        HashMap<String, Object> stringObjectHashMap = new HashMap<String, Object>();
        stringObjectHashMap.put("rows",map);
        return stringObjectHashMap;
    }

    public List<Meeting> searchInfo(String customername) {
        List<Meeting> meetings = meetingDao.searchInfo(customername);
        return meetings;
    }

    public int clear() {
        int clear = meetingDao.clear();
        return clear;
    }

    public Map<String,Object> searchman(String cucn, String cun, String tn) {


        List<Meeting> all = meetingDao.searchman(cucn,cun,tn);
        HashMap<String, Object> stringObjectHashMap = new HashMap<String, Object>();
        stringObjectHashMap.put("rows",all);
        stringObjectHashMap.put("total",0);
        return stringObjectHashMap;
    }
}
