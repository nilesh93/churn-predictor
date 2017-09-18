/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package churn.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class representing an DataMiningManager.
 * @author Rishanthan
 */
public class DataMiningManager {
    
    /**
     * Method used to get the prediction results.
     * @param connection - DB Connection.
     * @return
     * @throws SQLException 
     */
    public static String[] getPredictionResult(Connection connection) throws SQLException {
        Statement stmt = null;
        String[] results = new String[2];

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(QueryConstant.PREDICTIONQUERY);
            while (rs.next()) {
                results[0]= rs.getString(QueryConstant.MODELPREDICTION);
                results[1]=rs.getString(QueryConstant.MODELPROBABILITY);

                System.out.println("[Log][Info][getPredictionResult]: Prediction: "+ results[0] + "\t");
                System.out.println("[Log][Info][getPredictionResult]: Probability: "+ results[1] + "\t");
            }
        } catch (SQLException e) {
             System.out.println("[Log][Error][getPredictionResult]: " + e );
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        
        return results;
    }
    
    /**
     * Method used to insert the user entered new data into database.
     * @param connection
     * @param AGE
     * @param SEX
     * @param HISTOLOGICTYPE
     * @param DEGREEOFDIFFE
     * @param BONE
     * @param BONEMARROW
     * @param LUNG
     * @param PLEURA
     * @param PERITONEUM
     * @param LIVER
     * @param BRAIN
     * @param SKIN
     * @param NECK
     * @param SUPRACLAVICULAR
     * @param AXILLAR
     * @param MEDIASTINUM
     * @param ABDOMINAL
     * @throws SQLException 
     */

    // todo
    public static void insertNewData(Connection connectioon, int AGE, int SEX, int HISTOLOGICTYPE,
            int DEGREEOFDIFFE, int BONE, int BONEMARROW, int LUNG, int  PLEURA, int PERITONEUM,
            int LIVER, int BRAIN, int SKIN, int NECK, int SUPRACLAVICULAR, int AXILLAR,
            int MEDIASTINUM, int ABDOMINAL)throws SQLException {
        Statement stmt = null;
        
        String query = QueryConstant.INPUTDATAINSERTQUERY 
                + AGE +"," + SEX+"," + HISTOLOGICTYPE +"," + DEGREEOFDIFFE +","
                + BONE +"," + BONEMARROW +"," + LUNG +"," + PLEURA +","
                + PERITONEUM +"," + LIVER +"," + BRAIN+"," + SKIN +","
                + NECK +"," + SUPRACLAVICULAR +"," + AXILLAR +"," + MEDIASTINUM +","
                + ABDOMINAL + ")";

        try {
            stmt = connectioon.createStatement();
            int  rs = stmt.executeUpdate(query);
            if(rs>0){
                System.out.println("[Log][Info][insertNewData] New values inserted");
            }
        } catch (SQLException e) {
            System.out.println("[Log][Error][insertNewData]: " + e );
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * Method used to delete the previous data in the database table.
     * @param connection - DB Connection
     * @throws SQLException 
     */
    public static void deletePreviousData(Connection connection) throws SQLException {
        
        if (!checkDataExists(connection)) {
            return;
        }
        // todo
        Statement stmt = null;
        String query = "DELETE FROM DMUSER.PRIMARY_TUMOR_APPLY";
        
        try {
            stmt = connection.createStatement();
            int  rs = stmt.executeUpdate(query);
            
            if(rs>0){
                System.out.println("[Log][Succes][deletePreviousData]: Database Cleared");
            }
        } catch (SQLException e) {
             System.out.println("[Log][Error][deletePreviousData]: " + e );
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    
    /**
     * Method used to check whether any data exists in the table.
     * @param connection - DB Connection.
     * @return - boolean
     * @throws SQLException 
     */
    public static boolean checkDataExists(Connection connection) throws SQLException{
        
        System.out.println("[Log][Info][checkDataExists]: Start");
        
        Statement stmt = null;
        // todo
        String query = "SELECT * FROM DMUSER.PRIMARY_TUMOR_APPLY";
        boolean result = true;
        
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                result = true;
            }  
        } catch (SQLException e) {
             System.out.println("[Log][Error][checkDataExists]:"+ e );
             result = false;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        
        System.out.println("[Log][Info][checkDataExists]: Finish");
        
        return result;
    }
}
