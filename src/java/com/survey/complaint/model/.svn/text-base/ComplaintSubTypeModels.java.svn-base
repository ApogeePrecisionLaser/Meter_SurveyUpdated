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
public class ComplaintSubTypeModels {

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

     public int getNoOfRows(String searchTypeOfComplaint, String searchSubTypeOfComplaint) {
        int noOfRows = 0;
        try {
            String query = "select count(*) from complaint_type ct, complaint_sub_type cst"
                    + " WHERE ct.complaint_type_id = cst.complaint_type_id AND if('" + searchSubTypeOfComplaint + "' = '', complaint_sub_type like '%%', complaint_sub_type = ? ) "
                    + " AND if('" + searchTypeOfComplaint + "' = '', ct.complaint_type like '%%', ct.complaint_type = ? ) ";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, searchSubTypeOfComplaint);
            pst.setString(2, searchTypeOfComplaint);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("ComplaintSubTypeModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
      public List<ComplaintTypes> showData(int lowerLimit, int noOfRowsToDisplay, String searchTypeOfComplaint, String searchSubTypeOfComplaint) {
        List<ComplaintTypes> list = new ArrayList<ComplaintTypes>();
        try {
            String query = "SELECT complaint_sub_type_id, complaint_sub_type, description , complaint_sub_type_eng, ct.complaint_type, ct.complaint_type_id FROM complaint_type ct,complaint_sub_type cst"
                    + " WHERE ct.complaint_type_id = cst.complaint_type_id "
                    + " AND if('" + searchSubTypeOfComplaint + "' = '', complaint_sub_type like '%%', complaint_sub_type = ? ) "
                    + " AND if('" + searchTypeOfComplaint + "' = '', ct.complaint_type like '%%', ct.complaint_type = ? ) "
                    + " ORDER BY complaint_sub_type LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            System.out.println(lowerLimit + "," + noOfRowsToDisplay);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchSubTypeOfComplaint);
            pstmt.setString(2, searchTypeOfComplaint);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ComplaintTypes complaintTypes = new ComplaintTypes();
                complaintTypes.setComplaint_type_id(rset.getInt("complaint_type_id"));
                complaintTypes.setComplaint_type(rset.getString("complaint_type"));
                complaintTypes .setComplaint_sub_type_id(rset.getInt("complaint_sub_type_id"));
                complaintTypes .setComplaint_sub_type(rset.getString("complaint_sub_type"));
                complaintTypes.setDescription(rset.getString("description"));
                complaintTypes .setComplaint_sub_type_eng(rset.getString("complaint_sub_type_eng"));
                list.add(complaintTypes);
            }
        } catch (Exception e) {
            System.out.println("Error:TypeOfComplintModel-showData--- " + e);
        }
        return list;
    }
     public List<String> getComplaintSubTypeList(String q) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        String query = "SELECT complaint_sub_type FROM complaint_sub_type ORDER BY complaint_sub_type ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                String complaint_type = rset.getString("complaint_sub_type");
                if (complaint_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(complaint_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Compalint Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintSubTypeModel --getComplaintTypeList-- " + e);
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
            System.out.println("Error:ComplaintSubTypeModel --getComplaintTypeList-- " + e);
        }
        return list;
    }

    public int deleteRecord(int complaint_sub_type_id) {
        String query = "DELETE FROM complaint_sub_type WHERE complaint_sub_type_id = " + complaint_sub_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: ComplaintSubTypeModel" + e);
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
        query = " insert into complaint_sub_type(complaint_sub_type , description, complaint_sub_type_eng, complaint_type_id) "
                + " values(?, ?, ?, ?)";
        String[] complaint_sub_type_id = com_complaint_type.getComplaint_sub_type_idM();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < complaint_sub_type_id.length; i++) {
                if (com_complaint_type.getComplaint_sub_type_idM()[i].equals("1")) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setString(1,com_complaint_type.getComplaint_sub_typeM()[i]);
                    pstmt.setString(2, com_complaint_type.getDescriptionM()[i]);
                    pstmt.setString(3,com_complaint_type.getComplaint_sub_type_engM()[i]);
                    pstmt.setInt(4, Integer.parseInt(com_complaint_type.getComplaint_type_idM()[i]));
                    rowsAffected = pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintSubTypeModel" + e);
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
        String query = " UPDATE complaint_sub_type "
                + " SET complaint_sub_type = ?,description =?, complaint_sub_type_eng = ?, complaint_type_id = ? where complaint_sub_type_id= ? ";

        String[] complaint_sub_type_id = com_complaint_type.getComplaint_sub_type_idM();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < complaint_sub_type_id.length; i++) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, com_complaint_type.getComplaint_sub_typeM()[i]);
                    pstmt.setString(2, com_complaint_type.getDescriptionM()[i]);
                    pstmt.setString(3, com_complaint_type.getComplaint_sub_type_engM()[i]);
                    pstmt.setInt(4, Integer.parseInt(com_complaint_type.getComplaint_type_idM()[i]));
                    pstmt.setInt(5, Integer.parseInt(complaint_sub_type_id[i]));
                    rowsAffected = pstmt.executeUpdate();
                
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintSubTypeModel" + e);
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

    public Map<Integer, String> complaintTypeList(){
        Map<Integer, String> ctList = new HashMap<Integer, String>();
        String query = "SELECT complaint_type_id, complaint_type FROM complaint_type ORDER BY complaint_type";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                ctList.put(rs.getInt("complaint_type_id"), rs.getString("complaint_type"));
            }
        }catch(Exception ex){
            System.out.println("Error: in complaintTypeList method of ComplaintSubTypeModels: " + ex);
        }
        return ctList;
    }

    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            String query = "select count(*) from  complaint_sub_type ";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error:ComplaintSubTypeModel " + e);
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
                com_complaint_type.setComplaint_type_eng(rset.getString("complaint_sub_type_eng"));
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
