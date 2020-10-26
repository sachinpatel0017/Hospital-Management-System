/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SanjeevaniApp.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Sachin Patel
 */
public class DBConnection {
    private static Connection conn;
    static{
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            conn=DriverManager.getConnection("jdbc:oracle:thin:@//LAPTOP-1RHCKM15:1521/xe","myhms","student");
            JOptionPane.showMessageDialog(null,"Connection done successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(ClassNotFoundException cnfe){
               JOptionPane.showMessageDialog(null,"Can not load driver!", "Error!", JOptionPane.ERROR_MESSAGE);
               cnfe.printStackTrace();
        }catch(SQLException sqlex){
             JOptionPane.showMessageDialog(null,"problem in db!", "Error!", JOptionPane.ERROR_MESSAGE);
             sqlex.printStackTrace();
        }
    
    }
    public static Connection getConnection(){
        return conn;
    }
    public static void closeConnection(){
        try{
            if(conn!=null){
            conn.close();
            JOptionPane.showMessageDialog(null,"Connection close successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(SQLException sqlex){
             JOptionPane.showMessageDialog(null,"problem in closing connection!", "Error!", JOptionPane.ERROR_MESSAGE);
             sqlex.printStackTrace();
    }
    }
}
