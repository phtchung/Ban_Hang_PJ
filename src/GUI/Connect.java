/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI; 
import java.sql.*;

public class Connect {
    Connection cnn = null;
    public Connection getConnectDB(){
        try{
            String uRL = "jdbc:sqlserver://localhost:1433;databaseName=CSDL_Project";
            String user = "sa";
            String pass = "123"; 
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cnn = DriverManager.getConnection(uRL, user, pass);
            System.out.println(cnn);
            System.out.println("Ket noi thanh cong");
        } catch (Exception e){
            System.out.println("Khong ket noi duoc");
        }
        return cnn;
    }
}
