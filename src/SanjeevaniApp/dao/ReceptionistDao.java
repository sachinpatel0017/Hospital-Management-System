/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SanjeevaniApp.dao;

import sanjeevniapp.pojo.EmpPojo;
import sanjeevniapp.pojo.UserPojo;
import SanjeevaniApp.dbutil.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;

/**
 *
 * @author Sachin Patel
 */
public class ReceptionistDao {
    public static boolean addReceptionist(UserPojo user) throws SQLException{
      PreparedStatement ps=DBConnection.getConnection().prepareStatement("insert into users values(?,?,?,?,?)");
     ps.setString(1,user.getUserId());
     ps.setString(2,user.getUserName());
     ps.setString(3,user.getEmpId());
     ps.setString(4,user.getPassword());
     ps.setString(5,user.getUserType());
      int x=ps.executeUpdate();
      return x>0;  
    }
       public static ArrayList<EmpPojo> getAllRecep() throws SQLException{
        Connection conn=DBConnection.getConnection();
      Statement st=conn.createStatement();
    ResultSet rs=st.executeQuery("Select * from employees where role='receptionist' ");
    ArrayList<EmpPojo>empList=new ArrayList<>();
    while (rs.next()){
        EmpPojo e= new EmpPojo();
        e.setEmpid(rs.getString(1));
         e.setEmpname(rs.getString(2));
         e.setJob(rs.getString(3));
           e.setSal(rs.getDouble(4));
           empList.add(e);
    }
    return empList;
   }
  public static EmpPojo findRecepById(String empid)throws SQLException
    {
        EmpPojo e=null;
       PreparedStatement ps = DBConnection.getConnection().prepareStatement("select * from employees where empid=(?)");
       ps.setString(1,empid);
       ResultSet rs =ps.executeQuery();
       if(rs.next())
       {
         e=new EmpPojo();
         e.setEmpid(rs.getString(1));
         e.setEmpname(rs.getString(2));
       
       }
       return e; 
    }
   public static boolean removeReceptionist(String Empid)throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("delete employees where empid=(?)");
        ps.setString(1, Empid);
        int x=ps.executeUpdate();
        return x>0;
    }
       public static HashMap<String,String> getNonregisteredReceptionistList() throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry="Select empid,empname from employees where role='receptionist' and empid not in (Select empid from users where usertype='receptionist')";
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(qry);
        HashMap<String,String> receptionist=new HashMap();
        while(rs.next()){
            String id=rs.getString(1);
            String name=rs.getString(2);
            receptionist.put(id, name);
        }
        return receptionist;
    }
    public static HashMap<String,String> getReceptionList() throws SQLException{
      Connection conn=DBConnection.getConnection();
        String qry="Select userid,username from users where usertype='receptionist'";
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(qry);
        HashMap<String,String> receptionist=new HashMap();
        while(rs.next()){
            String id=rs.getString(1);
            String name=rs.getString(2);
            receptionist.put(id, name);
        }
        return receptionist;   
    }
}
