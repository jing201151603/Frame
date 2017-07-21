package com.jing.dao.dataModel;


import com.jing.dao_lib.annotion.DbField;
import com.jing.dao_lib.annotion.DbPrimaryField;
import com.jing.dao_lib.annotion.DbTable;

/**
 * Created by ShuWen on 2017/2/9.
 */

@DbTable(value = "tb_user")
public class User {

    @DbPrimaryField(value = "id")
    public Integer id;
    @DbField(value = "user_name")
    public String name;
    @DbField(value = "user_address")
    public String address;
    @DbField(value = "user_psw")
    public String psw;
    @DbField(value = "user_status")
    public Integer status;
    @DbField(value = "user_id")
    public String user_id;
    @DbField(value = "isMe")
    public Boolean isMe;
    @DbField(value = "myAge")
    public Double myAge;
    @DbField(value = "myPhone")
    public Long myPhone;
    @DbField(value = "aShort")
    public Short aShort;

    public User(String name, String address, String psw, Integer status, String user_id, Boolean isMe, Double myAge, Long myPhone, Short aShort) {
        this.name = name;
        this.address = address;
        this.psw = psw;
        this.status = status;
        this.user_id = user_id;
        this.isMe = isMe;
        this.myAge = myAge;
        this.myPhone = myPhone;
        this.aShort = aShort;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Boolean getMe() {
        return isMe;
    }

    public void setMe(Boolean me) {
        isMe = me;
    }

    public Double getMyAge() {
        return myAge;
    }

    public void setMyAge(Double myAge) {
        this.myAge = myAge;
    }

    public Long getMyPhone() {
        return myPhone;
    }

    public void setMyPhone(Long myPhone) {
        this.myPhone = myPhone;
    }

    public Short getaShort() {
        return aShort;
    }

    public void setaShort(Short aShort) {
        this.aShort = aShort;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", psw='" + psw + '\'' +
                ", status=" + status +
                ", user_id='" + user_id + '\'' +
                ", isMe=" + isMe +
                ", myAge=" + myAge +
                ", myPhone=" + myPhone +
                ", aShort=" + aShort +
                '}';
    }
}
