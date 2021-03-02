package com.dao;


import com.pojo.Meeting;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;


public interface meetingDao extends Mapper<Meeting> {

    @SelectProvider(type = getSql.class,method ="get1")
    public List<Meeting> findAll(@Param("start") String start,@Param("end") String end,@Param("pe") String pe,@Param("cucn")String cucn,@Param("cun")String cun,@Param("tn")String tn);

    @SelectProvider(type = getSql.class,method ="getcount")
    public Integer getTotalcount(@Param("pe") String pe,@Param("cucn") String cucn,@Param("cun")String cun,@Param("tn")String tn);

    @Select("select distinct persontype from t_meeting")
    public List<String> findPersontype();

    @Select("select count(*) as num,tablenum from t_meeting group by tablenum order by tablenum asc ")
    public List<Map<String,Object>> findsit();

    @Select("select customername from t_meeting where tablenum =#{tablenum}")
    public List<String> findByTablenum(@Param("tablenum") String tablenum);

    @Select("select *from t_meeting where customername like '%${customername}%'")
    public  List<Meeting> searchInfo(@Param("customername") String customername);

    @Delete("delete from t_meeting")
    public int clear();

    @SelectProvider(type = getSql.class,method ="get2")
    public List<Meeting> searchman(@Param("cucn")String cucn,@Param("cun")String cun,@Param("tn")String tn);


    //内部类
    class getSql {

        public String getcount(Map<String, Object> para){
            String sql="";
            sql+="select count(*) from t_meeting where 1=1 ";

            Object pe = para.get("pe");
            String s = pe.toString();
            if (StringUtils.isNotEmpty(s)){
                sql+=(" and  persontype = '"+s+"' ");
            }

            Object cucn = para.get("cucn");
            String cucn1 = cucn.toString();
            if (StringUtils.isNotEmpty(cucn1)){
                sql+=(" and  customercompanyname like '%"+cucn1+"%'");
            }

            Object cun = para.get("cun");
            String cun1 = cun.toString();
            if (StringUtils.isNotEmpty(cun1)){
                sql+=(" and  customername like '%"+cun1+"%'");
            }

            Object tn = para.get("tn");
            String tn1 = tn.toString();
            if (StringUtils.isNotEmpty(tn1)){
                sql+=(" and  tablenum like '%"+tn1+"%'");
            }

            return sql;


        }


        public String get1(Map<String, Object> para){//这个方法必须用这参数

            //String sql="select * from (select row_number() over(order by id desc) as rowid,* from t_meeting ) num where num.rowid between #{start} and #{end}";

            String sql="select * from (select row_number() over(order by id desc) as rowid,* from t_meeting where 1=1 ";

            Object pe = para.get("pe");
            String s = pe.toString();
            if (StringUtils.isNotEmpty(s)){
                sql+=(" and  persontype = '"+s+"' ");
            }

            Object cucn = para.get("cucn");
            String cucn1 = cucn.toString();
            if (StringUtils.isNotEmpty(cucn1)){
                sql+=(" and  customercompanyname like '%"+cucn1+"%'");
            }

            Object cun = para.get("cun");
            String cun1 = cun.toString();
            if (StringUtils.isNotEmpty(cun1)){
                sql+=(" and  customername like '%"+cun1+"%'");
            }

            Object tn = para.get("tn");
            String tn1 = tn.toString();
            if (StringUtils.isNotEmpty(tn1)){
                sql+=(" and  tablenum like '%"+tn1+"%'");
            }


            sql+= (") num where num.rowid between "+para.get("start")+" and " +para.get("end"));
            return sql;
        }



        public String get2(Map<String, Object> para){//这个方法必须用这参数

            //String sql="select * from (select row_number() over(order by id desc) as rowid,* from t_meeting ) num where num.rowid between #{start} and #{end}";

            String sql="select * from  t_meeting where 1=1 ";


            Object cucn = para.get("cucn");
            String cucn1 = cucn.toString();
            if (StringUtils.isNotEmpty(cucn1)){
                sql+=(" and  customercompanyname like '%"+cucn1+"%'");
            }

            Object cun = para.get("cun");
            String cun1 = cun.toString();
            if (StringUtils.isNotEmpty(cun1)){
                sql+=(" and  customername like '%"+cun1+"%'");
            }

            Object tn = para.get("tn");
            String tn1 = tn.toString();
            if (StringUtils.isNotEmpty(tn1)){
                sql+=(" and  tablenum like '%"+tn1+"%'");
            }

            return sql;
        }
    }
}
