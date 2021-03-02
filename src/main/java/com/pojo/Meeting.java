package com.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author god
 * @date 2019年 12月28日 13:59:12
 */

@Table(name = "t_meeting")
public class Meeting implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(insertable = false,name = "id")//sqlserver 这个不加不行
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String persontype;
    private String customertype;
    private String customercompanyname;
    private String customername;
    private String position;
    private String stay;
    private String roomtype;
    private String roomnum;
    private String tablenum;
    private String memo;

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", persontype='" + persontype + '\'' +
                ", customertype='" + customertype + '\'' +
                ", customercompanyname='" + customercompanyname + '\'' +
                ", customername='" + customername + '\'' +
                ", position='" + position + '\'' +
                ", stay='" + stay + '\'' +
                ", roomtype='" + roomtype + '\'' +
                ", roomnum='" + roomnum + '\'' +
                ", tablenum='" + tablenum + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersontype() {
        return persontype;
    }

    public void setPersontype(String persontype) {
        this.persontype = persontype;
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public String getCustomercompanyname() {
        return customercompanyname;
    }

    public void setCustomercompanyname(String customercompanyname) {
        this.customercompanyname = customercompanyname;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStay() {
        return stay;
    }

    public void setStay(String stay) {
        this.stay = stay;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getRoomnum() {
        return roomnum;
    }

    public void setRoomnum(String roomnum) {
        this.roomnum = roomnum;
    }

    public String getTablenum() {
        return tablenum;
    }

    public void setTablenum(String tablenum) {
        this.tablenum = tablenum;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
