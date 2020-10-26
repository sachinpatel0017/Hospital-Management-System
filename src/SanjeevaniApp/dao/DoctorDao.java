/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SanjeevaniApp.dao;

import sanjeevniapp.pojo.DocPojo;
import sanjeevniapp.pojo.UserDetails;
import sanjeevniapp.pojo.UserPojo;
import SanjeevaniApp.dbutil.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import sanjeevniapp.pojo.PatientPojo;

/**
 *
 * @author Sachin Patel
 */
public class DoctorDao {
    public static ArrayList<String> getAllDoctorsId()throws SQLException{
       ArrayList<String>docId=new ArrayList<>();
       ResultSet rs=DBConnection.getConnection().createStatement().executeQuery("Select doctorid from doctors");
       while(rs.next())
       {
           docId.add(rs.getString(1));
           
       }
       return docId;
    } 
    public static HashMap<String,String> loadDoctorIdAndName()throws SQLException{
        HashMap<String,String> hm=new HashMap();
        ResultSet rs= DBConnection.getConnection().createStatement().executeQuery("select empid,empname from employees where role='doctor' and empid not in (select empid from users where usertype='doctor')");
        while(rs.next()){
            String id= rs.getString(1);
            String name= rs.getString(2);
            hm.put(id, name);
        }
        return hm;
    }
    public static String getDocId()throws SQLException{
        ResultSet rs=DBConnection.getConnection().createStatement().executeQuery("select max(doctorid) from doctors");
        
        rs.next();
        String docid=rs.getString(1);
        int finalid=Integer.parseInt(docid.substring(3));
        int id=finalid+1;
        docid="doc"+id;
        return docid;
    }
    public static boolean addDoctors(UserDetails u)throws SQLException{
        PreparedStatement ps=DBConnection.getConnection().prepareStatement("insert into users values(?,?,?,?,?)");
        ps.setString(1, u.getUserid());
        ps.setString(2, u.getUsername());
        ps.setString(4, u.getPassword());
        ps.setString(3, u.getEmpid());
        ps.setString(5, u.getUsertype());
        int x=ps.executeUpdate();
        return x==1;    
    }
    public static boolean addDoctorsInDoctorTable(DocPojo d)throws SQLException{
        PreparedStatement ps=DBConnection.getConnection().prepareStatement("insert into doctors values(?,?,?,?,?)");
        ps.setString(1, d.getUserid());
        ps.setString(2, d.getDoctorid());
        ps.setString(3, d.getQualifications());
        ps.setString(4, d.getSpecialist());
        ps.setString(5, d.getStatus());
        int rs=ps.executeUpdate();
        return rs==1;
    }
    public static boolean updateDoctors(UserPojo d)throws SQLException{
        PreparedStatement ps=DBConnection.getConnection().prepareStatement("update users set password=? where userid=?");
        ps.setString(1, d.getPassword());
        ps.setString(2, d.getUserId());
        int rs=ps.executeUpdate();
        return rs==1;
    }
    public static HashMap<String,String> getDoctorIdAndName()throws SQLException{
        HashMap<String,String> hm=new HashMap();
        ResultSet rs= DBConnection.getConnection().createStatement().executeQuery("select userid,username from users where usertype='doctor'");
        while(rs.next()){
            String id= rs.getString(1);
            String name= rs.getString(2);
            hm.put(id, name);
        }
        return hm;
    }
    public static boolean removeDoctor(String a)throws SQLException{
        PreparedStatement ps=DBConnection.getConnection().prepareStatement("update doctors set active='n' where userid=?");
        ps.setString(1, a);
        return ps.executeUpdate()==1;
    }
    public static HashSet<String> getDoctorid()throws SQLException{
        HashSet<String> al=new HashSet();
        ResultSet rs= DBConnection.getConnection().createStatement().executeQuery("select userid from doctors where Active='y'");
        while(rs.next()){
            String id=rs.getString(1);
            al.add(id);
        }
        return al;
    }
    public static ArrayList<DocPojo> getDoctorDetails()throws SQLException{
            Connection conn=DBConnection.getConnection();
      Statement st=conn.createStatement();
    ResultSet rs=st.executeQuery("Select * from doctors");
    ArrayList<DocPojo>empList=new ArrayList<>();
    while (rs.next()){
        DocPojo e= new DocPojo();
        e.setUserid(rs.getString(1));
         e.setDoctorid(rs.getString(2));
          e.setQualifications(rs.getString(3));
           e.setSpecialist(rs.getString(4));
           e.setStatus(rs.getString(5));
           empList.add(e);
    }
    return empList;
    }
    public static String getDid(String uid)throws SQLException{
        System.out.println(uid);
        PreparedStatement ps=DBConnection.getConnection().prepareStatement("select doctorid from doctors where userid=?");
        ps.setString(1, uid);
        ResultSet rs=ps.executeQuery();
        String did=null;
        while(rs.next()){
          did=rs.getString(1);  
        }
        return did;
    }
    public static ArrayList<PatientPojo> getDAPP(String did) throws SQLException{
        ArrayList<PatientPojo> al=new ArrayList<>();
        PreparedStatement ps=DBConnection.getConnection().prepareStatement("select p_id,f_name,opd from patient where doctor_id=?");
        ps.setString(1, did);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            PatientPojo pp=new PatientPojo();
            
            pp.setP_id(rs.getString(1));
            pp.setP_name(rs.getString(2));
            pp.setOpd(rs.getString(3));
            al.add(pp);
        }
        return al;
    }        
    
}
