package backend.persistence;

import backend.CloudDropConstants;
import backend.service.Device;
import backend.control.Mapping;

import java.sql.*;
import java.util.HashMap;

/**
 * Created by fernando on 6/23/15.
 */
public class DBConnector {

    static final String WRITE_MAPPING_OBJECT_SQL = "INSERT INTO mappings (address, object) VALUES(?,?) " +
            "ON DUPLICATE KEY UPDATE    \n" +
            "address=?, object=?";

    static final String WRITE_DEVICE_OBJECT_SQL = "INSERT INTO devices (address, object) VALUES(?,?) " +
            "ON DUPLICATE KEY UPDATE    \n" +
            "address=?, object=?";

    static final String LOAD_MAPPING_OBJECTS_SQL = "SELECT object FROM mappings";

    static final String LOAD_DEVICE_OBJECTS_SQL = "SELECT object FROM devices";

    static final String DELETE_ALL_MAPPINGS_SQL = "DELETE FROM mappings";

    static final String DELETE_ALL_DEVICES_SQL = "DELETE FROM devices";

    private static Connection getConnection() throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://" + CloudDropConstants.host + ":3306/CLOUD_DROP?autoDeserialize=true";
        String username = "root";
        String password = "";
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    public static void saveMapping(String key, Mapping mapping) {

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(WRITE_MAPPING_OBJECT_SQL);
            preparedStatement.setString(1, key);
            preparedStatement.setObject(2, mapping);
            preparedStatement.setString(3, key);
            preparedStatement.setObject(4, mapping);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveDevice(String key, Device device) {

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(WRITE_DEVICE_OBJECT_SQL);
            preparedStatement.setString(1, key);
            preparedStatement.setObject(2, device);
            preparedStatement.setString(3, key);
            preparedStatement.setObject(4, device);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadMappings(HashMap<String, Mapping> mappingsList) {

        try {
            Connection connection = getConnection();
            PreparedStatement pstmt = connection.prepareStatement(LOAD_MAPPING_OBJECTS_SQL);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Mapping mapping = (Mapping) rs.getObject(1);
                mappingsList.put(mapping.getAddress(),mapping);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadDevices(HashMap<String, Device> deviceList) {

        try {
            Connection connection = getConnection();
            PreparedStatement pstmt = connection.prepareStatement(LOAD_DEVICE_OBJECTS_SQL);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Device device = (Device) rs.getObject(1);
                deviceList.put(device.getAddress(), device);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void clearAll(){

        try {
            Connection connection = getConnection();
            PreparedStatement pstmt = connection.prepareStatement(DELETE_ALL_DEVICES_SQL);
            pstmt.executeUpdate();
            pstmt = connection.prepareStatement(DELETE_ALL_MAPPINGS_SQL);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
