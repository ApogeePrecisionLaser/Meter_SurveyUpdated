/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.complaint.model;

import com.survey.complaint.tableClasses.ComplaintTypes;
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
public class ComplaintTypeModels {

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
//          System.out.println("ComplaintTypeModel setConnection() Error: " + e);
//        }
//    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("ComplaintTypeModel CloseConnection() Error: " + e);
        }
    }

     public int getNoOfRows(String searchTypeOfComplaint) {
        int noOfRows = 0;
        try {
            String query = "select count(*) from complaint_type  "
                    + " WHERE if('" + searchTypeOfComplaint + "' = '', complaint_type like '%%', complaint_type = ? ) ";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, searchTypeOfComplaint);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("TypeOfComplaintModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
      public List<ComplaintTypes> showData(int lowerLimit, int noOfRowsToDisplay, String searchTypeOfComplaint) {
        List<ComplaintTypes> list = new ArrayList<ComplaintTypes>();
        try {
            String query = "SELECT complaint_type_id, complaint_type, remark FROM complaint_type  "
                    + " WHERE if('" + searchTypeOfComplaint + "' = '', complaint_type like '%%', complaint_type = ? )  ORDER BY complaint_type "
                    + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            System.out.println(lowerLimit + "," + noOfRowsToDisplay);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchTypeOfComplaint);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ComplaintTypes complaintTypes = new ComplaintTypes();
                complaintTypes .setComplaint_type_id(rset.getInt("complaint_type_id"));
                complaintTypes .setComplaint_type(rset.getString("complaint_type"));
                complaintTypes.setDescription(rset.getString("remark"));
                //complaintTypes .setComplaint_type_eng(rset.getString("complaint_type_eng"));
                list.add(complaintTypes);
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintTypeModels-showData--- " + e);
        }
        return list;
    }
     public List<String> getComplaintTypeList(String q) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        String query = "SELECT complaint_type FROM complaint_type ORDER BY complaint_type ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                String complaint_type = rset.getString("complaint_type");
                if (complaint_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(complaint_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Compalint Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintTypeModels --getComplaintTypeList-- " + e);
        }
        return list;
    }

    public int deleteRecord(int complaint_type_id) {
        String query = "DELETE FROM complaint_type WHERE complaint_type_id = " + complaint_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: ComplaintTypeModels " + e);
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

    public int insertRecord(ComplaintTypes com_complaint_type) {
        int rowsAffected = 0;
        String query = null;
        query = " insert into complaint_type(complaint_type , remark) "
                + " values(?, ?)";
        String[] complaint_type_id = com_complaint_type.getComplaint_type_idM();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < complaint_type_id.length; i++) {
                if (com_complaint_type.getComplaint_type_idM()[i].equals("1")) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setString(1,com_complaint_type.getComplaint_typeM()[i]);
                    pstmt.setString(2, com_complaint_type.getDescriptionM()[i]);
                    //pstmt.setString(3,com_complaint_type.getComplaint_type_engM()[i]);
                    rowsAffected = pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintTypeModels " + e);
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

    public int updateRecord(ComplaintTypes com_complaint_type) {
        int rowsAffected = 0;
        String query = " UPDATE complaint_type "
                + " SET complaint_type = ?,remark =? where complaint_type_id= ? ";

        String[] complaint_type_id = com_complaint_type.getComplaint_type_idM();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < complaint_type_id.length; i++) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, com_complaint_type.getComplaint_typeM()[i]);
                    pstmt.setString(2, com_complaint_type.getDescriptionM()[i]);
                    //pstmt.setString(3, com_complaint_type.getComplaint_type_engM()[i]);
                    pstmt.setInt(3, Integer.parseInt(complaint_type_id[i]));
                    rowsAffected = pstmt.executeUpdate();
                
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintTypeModels " + e);
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
            String query = "select count(*) from  complaint_type ";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error:ComplaintRegisterModel " + e);
        }
        return noOfRows;
    }
/*
    public List<ComplaintTypes> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<ComplaintTypes> list = new ArrayList<ComplaintTypes>();

        String query = "select * from  complaint_type  "
                + "  LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                ComplaintTypes com_complaint_type = new ComplaintTypes();
                com_complaint_type.setComplaint_type_id(rset.getInt("complaint_type_id"));
                com_complaint_type.setDescription(rset.getString("description"));
                com_complaint_type.setComplaint_type(rset.getString("complaint_type"));
                com_complaint_type.setComplaint_type_eng(rset.getString("complaint_type_eng"));
             //   String complaint_type=  rset.getString("complaint_type");
           
          //        System.out.println("complaint_type is="+complaint_type);
                list.add(com_complaint_type);
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel" + e);
        }
        return list;
    }
*/
 }
