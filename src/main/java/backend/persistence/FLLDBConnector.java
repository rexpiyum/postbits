package backend.persistence;

import backend.CloudDropConstants;
import backend.control.Mapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by fernando on 1/25/16.
 */
public class FLLDBConnector {

    private final static String query= "select * from sutd_dl_data where device_u_name = 'MS14' ORDER BY primary_key DESC limit 3";

    private static Connection getConnection() throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://flldb.clq15eolms3x.ap-southeast-1.rds.amazonaws.com:3306/flldb";
        String username = "admin";
        String password = "eirp2014";
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    public static String[] getFLLData() {

        String results [] = new String[3];

        try {

            Connection connection = getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                try {
                    results[rs.getInt("task") - 1] = rs.getString("reading");
                }catch(ArrayIndexOutOfBoundsException ex){
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
}
