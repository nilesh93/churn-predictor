/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package churn.db;

import churn.model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataMiningManager {

    public static Customer getPredictionResult(Customer cus) throws SQLException {
        String sql = "WITH \n" +
                "/* START OF SQL FOR NODE: CHURN_DB1 */\n" +
                "\"N$10010\" as (select '"+cus.age+"' as AGE, \n" +
                "'"+cus.balance+"' as BALANCE, \n" +
                "'"+cus.creditScore+"' as CREDITSCORE, \n" +
                "'"+cus.customerId+"' as CUSTOMERID, \n" +
                "'"+cus.estimatedCal+"' as ESTIMATEDSAL, \n" +
                "'null' as EXITED, \n" +
                "'"+cus.gender+"' as GENDER, \n" +
                "'"+cus.geography+"' as GEOGRAPHY, \n" +
                "'"+cus.id+"' as ID,\n" +
                "'"+cus.hasCrCard+"'as HASCRCARD, \n" +
                "'"+cus.isActiveMember+"' as ISACTIVEMEMBER , \n" +
                "'"+cus.noOfProducts+"' as NUMOFPRODUCTS, \n" +
                "'"+cus.rowNumber+"' as ROWNUMBER, \n" +
                "'"+cus.surname+"' as SURNAME, \n" +
                "'"+cus.tenure+"' as TENURE\n" +
                "from dual\n" +
                ")\n" +
                "/* END OF SQL FOR NODE: CHURN_DB1 */\n" +
                ",\n" +
                "/* START OF SQL FOR NODE: Apply */\n" +
                "\"N$10011\" as (SELECT /*+ inline */\n" +
                "\"ID\"\n" +
                ", PREDICTION(\"DMUSER\".\"CLAS_DT_1_7\" USING *) \"CLAS_DT_1_7_PRED\"\n" +
                ", PREDICTION_PROBABILITY(\"DMUSER\".\"CLAS_DT_1_7\" USING *) \"CLAS_DT_1_7_PROB\"\n" +
                " FROM \"N$10010\" )\n" +
                "/* END OF SQL FOR NODE: Apply */\n" +
                "select * from \"N$10011\"";


        Statement stmt = null;
        Connection connection = DBConnection.getConnection();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                cus.exited = rs.getInt(QueryConstant.MODELPREDICTION);
                cus.probability=rs.getFloat(QueryConstant.MODELPROBABILITY);

                System.out.println("[Log][Info][getPredictionResult]: Prediction: "+ cus.exited + "\t");
                System.out.println("[Log][Info][getPredictionResult]: Probability: " + cus.probability + "\t");
            }
        } catch (SQLException e) {
             System.out.println("[Log][Error][getPredictionResult]: " + e );
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        
        return cus;
    }

    public static void insertNewData(Customer cus)throws SQLException {
        Statement stmt = null;
        String query = "INSERT INTO CHURN_INPUT " +
                "(ID, ROWNUMBER, CUSTOMERID, SURNAME, CREDITSCORE, GEOGRAPHY, GENDER, AGE, TENURE, BALANCE, NUMOFPRODUCTS, HASCRCARD, ISACTIVEMEMBER, " +
                "ESTIMATEDSAL) \n" +
                "VALUES (SE.nextval, (SELECT  MAX(ROWNUMBER)+1  from DMUSER.CHURN_INPUT), (SELECT  MAX(CUSTOMERID) + 1 from DMUSER.CHURN_INPUT)," +
                " '"+cus.surname+"'," +
                " "+cus.creditScore+", " +
                "'"+cus.creditScore+"'," +
                " '"+cus.gender+"'," +
                " '"+cus.age+"'," +
                " '"+cus.tenure+"', " +
                ""+cus.balance+"," +
                " "+cus.noOfProducts+"," +
                " "+cus.hasCrCard+", " +
                ""+cus.isActiveMember+", " +
                ""+cus.estimatedCal+"" +
                " )\n" +
                "\n";

        System.out.println(query);
        Connection connection = DBConnection.getConnection();
        try {
            stmt = connection.createStatement();
            int  rs = stmt.executeUpdate(query);
            if(rs>0){
                System.out.println("[Log][Info][insertNewData] New values inserted" + rs);
            }
        } catch (SQLException e) {
            System.out.println("[Log][Error][insertNewData]: " + e );
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static Customer getLastInsertedCustomer(Customer cus) throws SQLException {
        Statement stmt = null;
        Connection connection = DBConnection.getConnection();
        String query = "select * from CHURN_INPUT m  where m.ID = (select MAX(s.ID) FROM CHURN_INPUT s )";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {

                cus.id= rs.getLong("ID");
                cus.customerId = rs.getLong("CUSTOMERID");
                cus.rowNumber = rs.getLong("ROWNUMBER");

                System.out.println("[Log][Info][getLastInsertedCustomer]: Customer: "+ rs.getLong("ID"));

            }
        } catch (SQLException e) {
            System.out.println("[Log][Error][getLastInsertedCustomer]: " + e );
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

        return cus;
    }

 }
