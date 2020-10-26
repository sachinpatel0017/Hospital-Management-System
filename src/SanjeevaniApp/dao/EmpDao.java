/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SanjeevaniApp.dao;

import sanjeevniapp.pojo.EmpPojo;
import SanjeevaniApp.dbutil.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Sachin Patel
 */
public class EmpDao {
   public static String getnewEmpId() throws SQLException{
     Connection conn=DBConnection.getConnection();
      Statement st=conn.createStatement();
    ResultSet rs=st.executeQuery("Select max(EmpId) from employees");
        int id=1;
        rs.next();
        String empid=rs.getString(1);
        int eno=Integer.parseInt(empid.substring(1));
        id=id+eno;
        String sr="E"+id;
        System.out.println(sr);
        return sr;
     
   }
   public static boolean addEmployee(EmpPojo e)throws SQLException{
      PreparedStatement ps=DBConnection.getConnection().prepareStatement("insert into employees values(?,?,?,?)");
     ps.setString(1,e.getEmpid());
     ps.setString(2,e.getEmpname());
     ps.setString(3,e.getJob());
     ps.setDouble(4,e.getSal());
      int x=ps.executeUpdate();
      return x==1;
   }
   public static ArrayList<EmpPojo> getAllEmp() throws SQLException{
        Connection conn=DBConnection.getConnection();
      Statement st=conn.createStatement();
    ResultSet rs=st.executeQuery("Select * from employees");
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
   public static EmpPojo findEmpById(String empid)throws SQLException
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
         e.setSal(rs.getDouble(4));
         e.setEmprole(rs.getString(3));
       }
       return e; 
    }
   
   public static boolean updateEmp(EmpPojo p) throws SQLException
     {
         
         PreparedStatement ps = DBConnection.getConnection().prepareStatement("update employees set empname=?,role=?,sal=? where empid=?");
         ps.setString(1,p.getEmpname());
         ps.setDouble(3,p.getSal());
         ps.setString(4,p.getEmpid());
         ps.setString(2,p.getEmprole());
        
         int result=ps.executeUpdate();
         
         if(result==1)
             return true;
         return false;
     }
    public static boolean removeEmployee(String Empid)throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("delete employees where empid=(?)");
        ps.setString(1, Empid);
        int x=ps.executeUpdate();
        return x>0;
    }
    


}






