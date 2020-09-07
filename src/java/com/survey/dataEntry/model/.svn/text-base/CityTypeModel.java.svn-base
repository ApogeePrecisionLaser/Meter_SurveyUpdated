/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.survey.tableClasses.CityTypeBean;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JPSS
 */
public class CityTypeModel {
 private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
 public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("CityTypeModel setConnection() Error: " + e);
        }
    }

 public int insertRecord(CityTypeBean cityTypeBean) {

        String query = "INSERT INTO city (city_name, pin_code, std_code, active) VALUES (?,?,?,?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, cityTypeBean.getCity_name());
            pstmt.setString(2, cityTypeBean.getPin_code());
            pstmt.setString(3, cityTypeBean.getStd_code());
            pstmt.setString(4, cityTypeBean.getActive());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;

    }

 public int updateRecord(CityTypeBean cityTypeBean) {
        String query = "UPDATE city SET city_name=?, pin_code=?, std_code=?, active=? WHERE city_id=?";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, cityTypeBean.getCity_name());
            pstmt.setString(2, cityTypeBean.getPin_code());
            pstmt.setString(3, cityTypeBean.getStd_code());
            pstmt.setString(4, cityTypeBean.getActive());
            pstmt.setInt(3, cityTypeBean.getCity_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("error while updating record........." + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

 public List<String> getCityType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT city_id, city_name FROM city GROUP BY city_name ORDER BY city_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_type = rset.getString("city_name");
                if (city_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such City Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getCityType ERROR inside CityTypeModel - " + e);
        }
        return list;
    }

 public int getNoOfRows(String searchCityType) {
        String query = " SELECT Count(*) "
                + " FROM city "
                + " WHERE IF('" + searchCityType + "' = '', city_name LIKE '%%',city_name =?) "
                + " ORDER BY city_name ";
        int noOfRows = 0;
        try {
            java.sql.PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchCityType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows city type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

 public List<CityTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchCityType) {
        List<CityTypeBean> list = new ArrayList<CityTypeBean>();

        String query = " SELECT city_id, city_name, pin_code, std_code, active "
                + " FROM city "
                + " WHERE IF('" + searchCityType + "' = '', city_name LIKE '%%',city_name =?) "
                + " ORDER BY city_name "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchCityType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CityTypeBean cityType = new CityTypeBean();
                cityType.setCity_id(rset.getInt("city_id"));
                cityType.setCity_name(rset.getString("city_name"));
                cityType.setPin_code(rset.getString("pin_code"));
                cityType.setStd_code(rset.getString("std_code"));
                cityType.setActive(rset.getString("active"));
                list.add(cityType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

 public int deleteRecord(int city_type_id) {
        String query = "DELETE FROM city WHERE city_id=" + city_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Error Record cannot be deleted.....";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

 public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection taxTypeModel:" + e);
        }
    }

 public String getMessage() {
        return message;
    }

 public String getMsgBgColor() {
        return msgBgColor;
    }

 public Connection getConnection() {
        return connection;
    }

 public void setConnection(Connection connection) {
        this.connection = connection;
    }

 public String getConnectionString() {
        return connectionString;
    }

 public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

 public String getDb_password() {
        return db_password;
    }

 public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

 public String getDb_username() {
        return db_username;
    }

 public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

 public String getDriverClass() {
        return driverClass;
    }

 public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

}
