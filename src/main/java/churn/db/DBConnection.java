/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package churn.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    
    private static Connection connection = null;
    
    /**
     * Singleton getter method to get the single instance of the DBConnection.
     * @return 
     */
    public static Connection getConnection() {
        if(connection==null){
            connection= createConnection();
        }
        return connection;
    }
   
    /**
     * This method is used to establish the DB Connection with Oracle
     * @return Connection - Database connection
     */
    private static Connection createConnection(){
        System.out.println("[Log][Info][createConnection]: Oracle JDBC Connection Staring");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("[Log][Error][createConnection]: Oracle JDBC driver not found");
            e.printStackTrace();
            return null;
        }
        
        System.out.println("[Log][Info][createConnection]: Oracle JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@127.0.0.1:1521:orcl", "dmuser",
                    "orcl");
        } catch (SQLException e) {

            System.out.println("[Log][Error][createConnection]: Connection Failed");
            e.printStackTrace();
            return null;
        }
        if (connection != null) {
            System.out.println("[Log][Succes][createConnection]: Connection successfully established");
        } else {
            System.out.println("[Log][Error][createConnection]: Connection attempt failed");
        }
        return connection;
    }     
}
