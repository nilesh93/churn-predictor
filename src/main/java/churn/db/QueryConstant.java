/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package churn.db;

/**
 * Class used to contain the query strings
 * @author Rishanthan
 */
public class QueryConstant {
    
    
    public static final String MODELPREDICTION = "CLAS_NB_1_4_PRED";
    
    public static final String MODELPROBABILITY = "CLAS_NB_1_4_PROB";
    
    // Query used to get the prediction and probability of the prediction.
    public static final String PREDICTIONQUERY =  "WITH "
                + "\"N$10011\" as (select  \"PRIMARY_TUMOR_APPLY\".\"ABDOMINAL\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"AGE\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"AXILLAR\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"BONE\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"BONEMARROW\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"BRAIN\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"CLASS\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"DEGREEOFDIFFE\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"HISTOLOGICTYPE\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"LIVER\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"LUNG\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"MEDIASTINUM\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"NECK\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"PERITONEUM\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"PLEURA\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"SEX\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"SKIN\", "
                + "\"PRIMARY_TUMOR_APPLY\".\"SUPRACLAVICULAR\" "
                + "from \"DMUSER\".\"PRIMARY_TUMOR_APPLY\"  )"
                + ","
                + "\"N$10012\" as (SELECT "
                + "PREDICTION(\"DMUSER\".\"CLAS_NB_1_4\" USING *) \"CLAS_NB_1_4_PRED\", "
                + "PREDICTION_PROBABILITY(\"DMUSER\".\"CLAS_NB_1_4\" USING *) \"CLAS_NB_1_4_PROB\""
                + "FROM \"N$10011\" )"
                + ""
                + "SELECT * from \"N$10012\"";
    
    public static final String INPUTDATAINSERTQUERY = "Insert into DMUSER.PRIMARY_TUMOR_APPLY ("
                + "AGE,"
                + "SEX,"
                + "HISTOLOGICTYPE,"
                + "DEGREEOFDIFFE,"
                + "BONE,"
                + "BONEMARROW,"
                + "LUNG,"
                + "PLEURA,"
                + "PERITONEUM,"
                + "LIVER,"
                + "BRAIN,"
                + "SKIN,"
                + "NECK,"
                + "SUPRACLAVICULAR,"
                + "AXILLAR,"
                + "MEDIASTINUM,"
                +"ABDOMINAL"
                + ") values (";
    
}
