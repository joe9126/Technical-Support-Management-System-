
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JOE
 */
public class Fixer {
    Home a=new Home();
    public Fixer(){
        try {
            
            String sql="GRANT ALL PRIVILEGES ON *.* TO root@'localhost' IDENTIFIED BY 'janito12345678' WITH GRANT OPTION";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect=(Connection)DriverManager.getConnection(a.server,a.username,a.dbpword);
            PreparedStatement pst=(PreparedStatement)connect.prepareStatement(sql);
           int h=pst.executeUpdate(); int i=0;
           
            if(h>0){
            System.out.println("successful");
            }else{
             System.out.println("failed");
            }
                    } catch (ClassNotFoundException ex) {
            Logger.getLogger(Fixer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Fixer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String args[]){
   Fixer f=new Fixer();
   java.awt.EventQueue.invokeLater(new Runnable(){
   public void run(){
         new Fixer();
     }
   });
     
    }
}
