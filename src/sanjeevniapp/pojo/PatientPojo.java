/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanjeevniapp.pojo;
import java.sql.Date;
/**
 *
 * @author HP
 */
public class PatientPojo {

    public PatientPojo() {
    }

    @Override
    public String toString() {
        return "PatientPojo{" + "P_id=" + P_id + ", P_name=" + P_name + ", S_name=" + S_name + ", opd=" + opd + ", gender=" + gender + ", M_status=" + M_status + ", address=" + address + ", city=" + city + ", mno=" + mno + ", doctor_id=" + doctor_id + ", w_no=" + w_no + ", b_no=" + b_no + ", active=" + active + ", date=" + date + ", age=" + age + '}';
    }

    public String getP_id() {
        return P_id;
    }

    public void setP_id(String P_id) {
        this.P_id = P_id;
    }

    public String getP_name() {
        return P_name;
    }

    public void setP_name(String P_name) {
        this.P_name = P_name;
    }

    public String getS_name() {
        return S_name;
    }

    public void setS_name(String S_name) {
        this.S_name = S_name;
    }

    public String getOpd() {
        return opd;
    }

    public void setOpd(String opd) {
        this.opd = opd;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getM_status() {
        return M_status;
    }

    public void setM_status(String M_status) {
        this.M_status = M_status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getW_no() {
        return w_no;
    }

    public void setW_no(String w_no) {
        this.w_no = w_no;
    }

    public String getB_no() {
        return b_no;
    }

    public void setB_no(String b_no) {
        this.b_no = b_no;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    private String P_id,P_name,S_name,opd,gender,M_status,address,city,mno,doctor_id,w_no,b_no,active;

    private Date date;
    private int age;

    public PatientPojo(String P_id, String P_name, String S_name, String opd, String gender, String M_status, String address, String city, String mno, String doctor_id, Date date, int age,String w_no,String b_no,String active) {
        this.P_id = P_id;
        this.P_name = P_name;
        this.S_name = S_name;
        this.opd = opd;
        this.gender = gender;
        this.M_status = M_status;
        this.address = address;
        this.city = city;
        this.mno = mno;
        this.doctor_id = doctor_id;
        this.date = date;
        this.age = age;
        this.w_no=w_no;
        this.b_no=b_no;
        this.active=active;
    }

   
    
}
