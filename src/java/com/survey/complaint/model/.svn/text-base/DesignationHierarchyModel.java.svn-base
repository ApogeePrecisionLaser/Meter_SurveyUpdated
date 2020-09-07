/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.complaint.model;

import com.survey.complaint.tableClasses.DesignationHierarchyBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author jpss
 */
public class DesignationHierarchyModel {
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
            connection = (Connection) DriverManager.getConnection(connectionString, db_userName, db_userPasswrod);//+ "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8"
        } catch (Exception e) {
            System.out.println("AdAssoSiteDetailsModel setConnection() Error: " + e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("ComplaintTypeModel CloseConnection() Error: " + e);
        }
    }

    public int getNoOfRows(String searchDesignation, String searchHigherDesignation) {
        int noOfRows = 0;
        try {
            String query = "SELECT COUNT(*) FROM (designation_hierarchy dh LEFT JOIN designation d1 ON dh.designation_id = d1.designation_id) "
                    + " LEFT JOIN designation d2 ON dh.higher_designation_id = d2.designation_id "
                    + " WHERE if('" + searchDesignation + "' = '', d1.designation like '%%', d1.designation = ?) "
                    + " AND if('" + searchHigherDesignation + "' = '', d2.designation like '%%', d2.designation = ? );";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, searchDesignation);
            pst.setString(2, searchHigherDesignation);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("DesignationHierarchyModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<DesignationHierarchyBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchDesignation, String searchHigherDesignation) {
        List<DesignationHierarchyBean> list = new ArrayList<DesignationHierarchyBean>();
        try {
            String query = "SELECT dh.designation_hierarchy_id, d1.designation, d2.designation AS higher_designation, dh.description, dh.remark FROM (designation_hierarchy dh LEFT JOIN designation d1 ON dh.designation_id = d1.designation_id) "
                    + " LEFT JOIN designation d2 ON dh.higher_designation_id = d2.designation_id "
                    + " WHERE if('" + searchDesignation + "' = '', d1.designation like '%%', d1.designation = ?) "
                    + " AND if('" + searchHigherDesignation + "' = '', d2.designation like '%%', d2.designation = ? )"
                    + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            System.out.println(lowerLimit + "," + noOfRowsToDisplay);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchDesignation);
            pstmt.setString(2, searchHigherDesignation);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DesignationHierarchyBean DesignationHierarchyBean = new DesignationHierarchyBean();
                DesignationHierarchyBean .setDesignation_hierarchy_id(rset.getInt("designation_hierarchy_id"));
                DesignationHierarchyBean .setDesignation(rset.getString("designation"));
                DesignationHierarchyBean .setHigherDesignation(rset.getString("higher_designation"));
                DesignationHierarchyBean.setDescription(rset.getString("description"));
                DesignationHierarchyBean.setRemark(rset.getString("remark"));
                //DesignationHierarchyBean .setComplaint_type_eng(rset.getString("complaint_type_eng"));
                list.add(DesignationHierarchyBean);
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintTypeModels-showData--- " + e);
        }
        return list;
    }

    public List<String> getDesignationList(String q) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        String query = "SELECT designation FROM designation ORDER BY designation ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                String designation = rset.getString("designation");
                if (designation.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(designation);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DesignationHierarchyModel --getDesignationList-- " + e);
        }
        return list;
    }
List<String> desig_list = new ArrayList<String>();
    public List<String> getHigherDesignationList(String q, int designation_id) {
        List<String> list = new ArrayList<String>();
                deslist.removeAll(deslist);

        q = q.trim();
        int count = 0;
         getDesignationID(designation_id);
        String query = "SELECT designation_id, designation FROM designation ORDER BY designation ";
        //String dhquery = "SELECT designation_id FROM designation_hierarchy WHERE higher_designation_id = "+ designation_id ;
        try {
            String str = "";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                int id = rset.getInt("designation_id");
                Iterator itr = deslist.iterator();
                int i = 0;
                if(designation_id != 0)
                 while(itr.hasNext()){
                     int a =(Integer) itr.next();
                     if(id == a){
                         i = 1;
                         break;
                     }
                    }
                if(i == 0){
                    String complaint_type = rset.getString("designation");
                    if (complaint_type.toUpperCase().startsWith(q.toUpperCase())) {
                        list.add(complaint_type);
                        count++;
                    }
                }
            }
            
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:DesignationHierarchyModel --getDesignationList-- " + e);
        }
        return list;
    }
    List<Integer> deslist = new ArrayList<Integer>();
    public void getDesignationID(int higher_Desig_id) {
        deslist.add(higher_Desig_id);
        int id = 0;
        String query = "SELECT designation_id FROM designation_hierarchy WHERE higher_designation_id = " + higher_Desig_id;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            String str = "";
            if(higher_Desig_id == 0){
                //return ;
            }
            while (rset.next()) {
                id = rset.getInt("designation_id");
                getDesignationID(id);                
            }
            //desig_list.add(str);


        } catch (Exception e) {
            System.out.println("Error:DesignationHierarchyModel --getDesignationList-- " + e);
        }
        //return ;
    }

    public int deleteRecord(int designation_hierarchy_id) {
        String query = "DELETE FROM designation_hierarchy WHERE designation_hierarchy_id = " + designation_hierarchy_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: DesignationHierarchyModel --deleteRecord-- " + e);
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

    public int insertRecord(DesignationHierarchyBean designationHierarchyBean) {
        int rowsAffected = 0;
        String query = null;
        query = " insert into designation_hierarchy(designation_id, higher_designation_id, description, remark) "
                + " values(?, ?, ?, ?)";
        String[] designation_hierarchy_id = designationHierarchyBean.getDesignationHierarchy_idM();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < designation_hierarchy_id.length; i++) {
                if (designationHierarchyBean.getDesignationHierarchy_idM()[i].equals("1")) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setInt(1, getDesignationId(designationHierarchyBean.getDesignationM()[i]));
                    pstmt.setInt(2, getDesignationId(designationHierarchyBean.getHigherDesignationM()[i]));
                    pstmt.setString(3, designationHierarchyBean.getDescriptionM()[i]);
                    pstmt.setString(4, designationHierarchyBean.getRemarkM()[i]);
                    rowsAffected = pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: DesignationHierarchyModel --insertRecord-- " + e);
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

    public int updateRecord(DesignationHierarchyBean designationHierarchyBean) {
        int rowsAffected = 0;
        String query = " UPDATE designation_hierarchy "
                + " SET designation_id = ?, higher_designation_id = ?, description = ?, remark = ? WHERE designation_hierarchy_id= ? ";

        String[] designation_hierarchy_id = designationHierarchyBean.getDesignationHierarchy_idM();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < designation_hierarchy_id.length; i++) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setInt(1, getDesignationId(designationHierarchyBean.getDesignationM()[i]));
                    pstmt.setInt(2, getDesignationId(designationHierarchyBean.getHigherDesignationM()[i]));
                    pstmt.setString(3, designationHierarchyBean.getDescriptionM()[i]);
                    pstmt.setString(4, designationHierarchyBean.getRemarkM()[i]);
                    pstmt.setInt(5, Integer.parseInt(designationHierarchyBean.getDesignationHierarchy_idM()[i]));
                    rowsAffected = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error: DesignationHierarchyModel --updateRecord-- " + e);
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

    public int getDesignationId(String designation){
        int designation_id = 0;
        String query = "SELECT designation_id FROM designation WHERE designation = '"+ designation +"'";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next()){
                designation_id = rs.getInt("designation_id");
            }
        }catch(Exception ex){
            System.out.println("ERROR: DesignationHierarchyModel --getDesignationId--");
        }
        return designation_id;
    }

    /*public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }*/

}
