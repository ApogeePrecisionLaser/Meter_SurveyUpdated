/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.complaint.model;

import com.survey.complaint.tableClasses.ComplaintStatus;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Shruti
 */
public class ComplaintStatusModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPasswrod;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public void setDriverClass(String driverclass) {
        this.driverClass = driverclass;
    }

    public void setConnectionString(String connectionstring) {
        this.connectionString = connectionstring;
    }

    public void setDb_userName(String username) {
        this.db_userName = username;
    }

    public void setDb_userPasswrod(String pass) {
        this.db_userPasswrod = pass;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_userName, db_userPasswrod);
        } catch (Exception e) {
            System.out.println("AdAssoSiteDetailsModel setConnection() Error: " + e);
        }
    }
//    public void setConnection(Connection con) {
//        try {
//
//            connection = con;
//        } catch (Exception e) {
//          System.out.println("ComplaintStatusModel setConnection() Error: " + e);
//        }
//    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("ComplaintStatusModel CloseConnection() Error: " + e);
        }
    }

    public int deleteRecord(int complaint_status_id) {
        String query = "DELETE FROM complaint_status WHERE complaint_status_id = " + complaint_status_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel" + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int insertRecord(ComplaintStatus com_status) {
        int rowsAffected = 0;
        String query = null;
        query = " insert into complaint_status(complaint_status , description) "
                + " values(?, ?)";
        String[] complaint_status_id = com_status.getComplaint_status_idM();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < complaint_status_id.length; i++) {
                if (com_status.getComplaint_status_idM()[i].equals("1")) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, com_status.getComplaint_statusM()[i]);
                    pstmt.setString(2, com_status.getDescriptionM()[i]);
                    rowsAffected = pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel" + e);
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

    public int updateRecord(ComplaintStatus com_status) {
        int rowsAffected = 0;
        String query = " UPDATE complaint_status  "
                + " SET complaint_status = ?,description =?  where complaint_status_id = ? ";

        String[] complaint_status_id = com_status.getComplaint_status_idM();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < complaint_status_id.length; i++) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, com_status.getComplaint_statusM()[i]);
                    pstmt.setString(2, com_status.getDescriptionM()[i]);
                    pstmt.setInt(3, Integer.parseInt(complaint_status_id[i]));
                    rowsAffected = pstmt.executeUpdate();
                
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel" + e);
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

    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            String query = "select count(*) from  complaint_status ";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error:ComplaintRegisterModel " + e);
        }
        return noOfRows;
    }

    public List<ComplaintStatus> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<ComplaintStatus> list = new ArrayList<ComplaintStatus>();

        String query = "select * from  complaint_status "
                + "  LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                ComplaintStatus com_status = new ComplaintStatus();
                com_status.setComplaint_status_id(rset.getInt("complaint_status_id"));
                com_status.setDescription(rset.getString("description"));
                com_status.setComplaint_status(rset.getString("complaint_status"));
                list.add(com_status);
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel" + e);
        }
        return list;
    }
}
