/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SanjeevaniApp.dao;

import sanjeevniapp.pojo.UserPojo;
import SanjeevaniApp.dbutil.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


public class UserDao {
    public static String validateUser(UserPojo user)throws SQLException{
        System.out.println(user);
        PreparedStatement ps=DBConnection.getConnection().prepareStatement("Select username from users where userid=? and password=? and usertype=?");
        ps.setString(1,user.getUserId());
        ps.setString(2,user.getPassword());
        ps.setString(3,user.getUserType());
        ResultSet rs=ps.executeQuery();
        String username=null;
        if(rs.next()){
            username=rs.getString(1);
        }
        return username;     
    }
    public static boolean ChangePassword(String userid,String pwd) throws SQLException{
        PreparedStatement ps=DBConnection.getConnection().prepareStatement("update users set password=? where userid=?");
        ps.setString(1, pwd);
        ps.setString(2, userid);
        return (ps.executeUpdate()!=0);
    }


}
