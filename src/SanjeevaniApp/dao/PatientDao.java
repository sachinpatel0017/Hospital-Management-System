package SanjeevaniApp.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import SanjeevaniApp.dbutil.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import sanjeevniapp.pojo.PatientPojo;

/**
 *
 * @author Lenovo
 */
public class PatientDao {
    
    public static boolean addpatient(PatientPojo p)throws SQLException
    {
        PreparedStatement ps=DBConnection.getConnection().prepareStatement("insert into patient values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1,p.getP_id());
        ps.setString(2, p.getP_name());
        ps.setString(3,p.getS_name());
        ps.setInt(4, p.getAge());
        ps.setString(5, p.getOpd());
        ps.setString(6, p.getGender());
        ps.setString(7, p.getM_status());
        ps.setDate(8, p.getDate());
        ps.setString(9, p.getAddress());
        ps.setString(10,p.getCity());
        ps.setString(11,p.getMno());
        ps.setString(12,p.getDoctor_id());
        ps.setString(13,p.getW_no());
        ps.setString(14,p.getB_no());
        ps.setString(15,p.getActive());
        
        return (ps.executeUpdate()!=0);
        
    }
     public static ArrayList<PatientPojo>getAllAppointment()throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from patient ");
       
        ResultSet rs= ps.executeQuery();
        ArrayList<PatientPojo> PatientList = new ArrayList<PatientPojo>();
        while(rs.next())
        {
            PatientPojo obj = new PatientPojo();
            obj.setP_id(rs.getString("p_id"));
            obj.setP_name(rs.getString("s_name"));
            obj.setOpd(rs.getString("opd"));
            
            PatientList.add(obj);
        }
        return PatientList;
    }
      public static ArrayList<PatientPojo>getAllPatientDetail()throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from patient ");
       
        ResultSet rs= ps.executeQuery();
        ArrayList<PatientPojo> PatientList = new ArrayList<PatientPojo>();
        while(rs.next())
        {
            PatientPojo obj = new PatientPojo();
            obj.setP_id(rs.getString("p_id"));
            obj.setP_name(rs.getString("f_name"));
            obj.setS_name(rs.getString("s_name"));
            obj.setAge(rs.getInt("age"));
            obj.setOpd(rs.getString("opd"));
            obj.setGender(rs.getString("gender"));
            obj.setM_status(rs.getString("m_status"));
            obj.setDate(rs.getDate("p_date"));
            obj.setAddress(rs.getString("address"));
            obj.setCity(rs.getString("city"));
            obj.setMno(rs.getString("phone_no"));
            obj.setDoctor_id(rs.getString("Doctor_id"));
            obj.setW_no(rs.getString("W_no"));
            obj.setB_no(rs.getString("B_no"));
            obj.setActive(rs.getString("active"));
            
            PatientList.add(obj);
        }
        return PatientList;
    }
    public static String getNewId()throws SQLException
    {
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery("select count(*) from patient");
        int id = 1;
        if(rs.next())
        {
    
            String empid = rs.getString(1);
            System.out.println("gffffffffff   "+empid);
        //    int eno = Integer.parseInt(empid.substring(1));
           id =id+ Integer.parseInt(empid);
        
        String sr = "p"+id;
        System.out.println("==================="+sr);
        return sr;
        }
        else
            return "p1";
    }
//    public static boolean UpdatePatient(PatientPojo p)throws SQLException
//{
//    PreparedStatement ps = DBConnection.getConnection().prepareStatement("update patient set f_name=?,s_name=?,age=?,opd=?,gender=?,m_status=?,p_date=?,address=?,city=?,phone_no=?,doctor_id=?,active='Y' where p_id=?");
//    ps.setString(1,p.getP_name());
//    ps.setString(2,p.getS_name());
//    ps.setInt(3,(int)p.getAge());
//    ps.setString(4,p.getOpd());
//    ps.setString(5, p.getGender());
//    ps.setString(6,p.getM_status());
//    ps.setDate(7,p.getDate());
//    ps.setString(8, p.getAddress());
//    ps.setString(9, p.getCity());
//    ps.setString(10,p.getMno());
//    ps.setString(11,p.getDoctor_id());
////    String rohit = p.getEmpid();
////    System.out.println(rohit);
//    int result=ps.executeUpdate();
//    if(result==1)
//        return true;
//    return false;
//}
    public static PatientPojo getAllPatientDetailById(String eno)throws SQLException
    {
      PatientPojo e=null;
       PreparedStatement ps = DBConnection.getConnection().prepareStatement("select * from patient  where p_id=?");
       ps.setString(1,eno);
       ResultSet rs =ps.executeQuery();
       if(rs.next())
       {
         e=new PatientPojo();
         e.setP_id(rs.getString(1));
         e.setP_name(rs.getString(2));
         e.setS_name(rs.getString(3));
         e.setAge(rs.getInt(4));
         e.setOpd(rs.getString(5));
         e.setGender(rs.getString(6));
         e.setM_status(rs.getString(7));
         e.setDate(rs.getDate(8));
         e.setAddress(rs.getString(9));
         e.setCity(rs.getString(10));
         e.setMno(rs.getString(11));
         e.setDoctor_id(rs.getString(12));
         e.setW_no(rs.getString(13));
          e.setB_no(rs.getString(14));
         e.setActive(rs.getString(15));
       }
       return e; 
    }
    public static ArrayList<PatientPojo> getAllPatientId() throws SQLException
    {
        ArrayList<PatientPojo> docId=new ArrayList<>();
        ResultSet rs=DBConnection.getConnection().createStatement().executeQuery("select p_id from patient ");
        while(rs.next())
        {
            PatientPojo d=new PatientPojo();
            d.setP_id(rs.getString(1));
            docId.add(d);
        }
        return docId;
    }
    
    public static PatientPojo getAllPatient(String eno)throws SQLException
    {
      
       PreparedStatement ps = DBConnection.getConnection().prepareStatement("select * from patient  where p_id=?");
       ps.setString(1,eno);
       ResultSet rs =ps.executeQuery();
        PatientPojo e=new PatientPojo();
       if(rs.next())
       {
        
         e.setP_id(rs.getString(1));
         e.setP_name(rs.getString(2));
         e.setS_name(rs.getString(3));
         e.setAge(rs.getInt(4));
         e.setOpd(rs.getString(5));
         e.setGender(rs.getString(6));
         e.setM_status(rs.getString(7));
         e.setDate(rs.getDate(8));
         e.setAddress(rs.getString(9));
         e.setCity(rs.getString(10));
         e.setMno(rs.getString(11));
         e.setDoctor_id(rs.getString(12));
           System.out.println("Your Data are - "+e);
        
       }
       return e; 
    }
      public static HashSet<String> getPatientid()throws SQLException{
        HashSet<String> al=new HashSet();
        ResultSet rs= DBConnection.getConnection().createStatement().executeQuery("select p_id from patient");
        while(rs.next()){
            String id=rs.getString(1);
            al.add(id);
        }
        return al;
    }
   public static boolean removePatient(String a)throws SQLException{
        PreparedStatement ps=DBConnection.getConnection().prepareStatement("update patient set active='n' where p_id=?");
        ps.setString(1, a);
        return ps.executeUpdate()==1;
   
}
}
