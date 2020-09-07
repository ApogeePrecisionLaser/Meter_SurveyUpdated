/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.complaint.model;

import com.survey.complaint.tableClasses.PersonHierarchyBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpss
 */
public class PersonHierarchyModel {
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
            System.out.println("PersonHierarchyModel setConnection() Error: " + e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("PersonHierarchyModel CloseConnection() Error: " + e);
        }
    }

    public int getNoOfRows(String searchOfficeType, String searchDesignatedPerson) {
        int noOfRows = 0;
        try {
            String query = "SELECT count(*) FROM person_hierarchy ph, key_person kp1, key_person kp2, org_office of, org_office_type oft "
                    + " WHERE kp1.key_person_id = ph.designated_person_id AND kp2.key_person_id = ph.higher_designated_person_id AND of.org_office_id = kp1.org_office_id AND oft.office_type_id = of.office_type_id "
                    + " AND IF('" + searchOfficeType + "'='', oft.office_type LIKE '%%', oft.office_type = '" + searchOfficeType + "') "
                    + " AND IF('" + searchDesignatedPerson + "'='', kp1.key_person_name LIKE '%%', kp1.key_person_name = '" + searchDesignatedPerson + "') ";
            PreparedStatement pst = connection.prepareStatement(query);
            //pst.setString(1, searchTypeOfComplaint);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("PersonHierarchyModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<PersonHierarchyBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchOfficeType, String searchDesignatedPerson) {
        List<PersonHierarchyBean> list = new ArrayList<PersonHierarchyBean>();
        try {
            String query = "SELECT person_hierarchy_id, oft.office_type, kp1.key_person_name AS designated_person, kp2.key_person_name AS higher_designated_person, ph.description, ph.remark, d1.designation, d2.designation as higher_designation "
                    + " FROM person_hierarchy ph, key_person kp1, key_person kp2, org_office of, org_office_type oft, designation_hierarchy dh, designation d1, designation d2 "
                    + " WHERE d1.designation_id = dh.designation_id AND d2.designation_id = dh.higher_designation_id AND dh.designation_hierarchy_id = ph.designation_hierarchy_id AND kp1.key_person_id = ph.designated_person_id AND kp2.key_person_id = ph.higher_designated_person_id AND of.org_office_id = kp1.org_office_id AND oft.office_type_id = of.office_type_id "
                    + " AND IF('" + searchOfficeType + "'='', oft.office_type LIKE '%%', oft.office_type = '" + searchOfficeType + "') "
                    + " AND IF('" + searchDesignatedPerson + "'='', kp1.key_person_name LIKE '%%', kp1.key_person_name = '" + searchDesignatedPerson + "') "
                    + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            System.out.println(lowerLimit + "," + noOfRowsToDisplay);
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, searchTypeOfComplaint);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                PersonHierarchyBean personHierarchyBean = new PersonHierarchyBean();
                personHierarchyBean.setPerson_hierarchy_id(rset.getInt("person_hierarchy_id"));
                personHierarchyBean.setOffice_type(rset.getString("office_type"));
                personHierarchyBean.setDesignated_person(rset.getString("designated_person"));
                personHierarchyBean.setDesignation(rset.getString("designation"));
                personHierarchyBean.setHigher_designation(rset.getString("higher_designation"));
                personHierarchyBean.setHigher_designated_person(rset.getString("higher_designated_person"));
                personHierarchyBean.setDescription(rset.getString("description"));
                personHierarchyBean.setRemark(rset.getString("remark"));
                list.add(personHierarchyBean);
            }
        } catch (Exception e) {
            System.out.println("Error:PersonHierarchyModel--showData--- " + e);
        }
        return list;
    }

    public List<String> getOfficeTypeList(String q) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        String query = "SELECT office_type FROM org_office_type ORDER BY office_type ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                String office_type = rset.getString("office_type");
                if (office_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(office_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Office Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:PersonHierarchyModel --getOfficeTypeList-- " + e);
        }
        return list;
    }

    public List<String> getDesignatedPersonList(String q, String office_type) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        String query = "SELECT kp.key_person_name FROM org_office_type oot, org_office of, key_person kp "
                + " WHERE kp.org_office_id = of.org_office_id "
                + " AND of.office_type_id = oot.office_type_id "
                + " AND IF('" + office_type + "'='', oot.office_type LIKE '%%', oot.office_type='" + office_type + "')";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                String key_person_name = rset.getString("key_person_name");
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Designated person exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:PersonHierarchyModel --getDesignatedPersonList-- " + e);
        }
        return list;
    }

    public List<String> getHigherDesignatedPersonList(String q, String office_type, String designated_person) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        String query = "SELECT kp1.key_person_name FROM org_office_type oot, org_office of, key_person kp1, key_person kp2, designation_hierarchy dh "
                + " WHERE kp1.designation_id = dh.higher_designation_id AND dh.designation_id = kp2.designation_id "
                + " AND kp2.org_office_id = of.org_office_id AND of.office_type_id = oot.office_type_id "
                + " AND IF('" + office_type + "'='', oot.office_type LIKE '%%', oot.office_type='" + office_type + "')"
                + " AND IF('" + designated_person + "'='', kp2.key_person_name LIKE '%%', kp2.key_person_name = '" + designated_person + "') "
                + " GROUP BY dh.higher_designation_id";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                String key_person_name = rset.getString("key_person_name");
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            //getOffice(office_type, designated_person)
            if (count == 0) {
                list.add("No such Higher Designated Person exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:PersonHierarchyModel --getHigherDesignatedPersonList-- " + e);
        }
        return list;
    }

    public String getOffice(String office_type, String designated_person){
        String query = "SELECT oot.office_type FROM org_office_type oot, org_office of, key_person kp1 "
                + " WHERE " //kp1.designation_id = dh.higher_designation_id AND dh.designation_id = kp2.designation_id "
                + " kp1.org_office_id = of.org_office_id AND of.office_type_id = oot.office_type_id "
                //+ " AND IF('" + office_type + "'='', oot.office_type LIKE '%%', oot.office_type='" + office_type + "')"
                + " AND IF('" + designated_person + "'='', kp1.key_person_name LIKE '%%', kp1.key_person_name = '" + designated_person + "') ";
                //+ " GROUP BY dh.higher_designation_id";

        int count = 0;
        String office = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                office = rset.getString("office_type");
            }



            if (count == 0) {
                //list.add("No such Higher Designated Person exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:PersonHierarchyModel --getHigherDesignatedPersonList-- " + e);
        }
        return office;
    }

    public int deleteRecord(int person_hierarchy_id) {
        String query = "DELETE FROM person_hierarchy WHERE person_hierarchy_id = " + person_hierarchy_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: PersonHierarchyModel --deleteRecord-- " + e);
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

    
    public int insertRecord(PersonHierarchyBean personHierarchyBean) {
        int rowsAffected = 0;
        String query = null;
        query = " insert into person_hierarchy(designation_hierarchy_id, designated_person_id, higher_designated_person_id, description, remark) "
                + " values(?, ?, ?, ?, ?)";
        String[] person_hierarchy_id = personHierarchyBean.getPerson_hierarchy_idM();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < person_hierarchy_id.length; i++) {
                if (personHierarchyBean.getPerson_hierarchy_idM()[i].equals("1")) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setInt(1, getDesignationHierarchyId(personHierarchyBean.getOffice_typeM()[i], personHierarchyBean.getDesignated_personM()[i], personHierarchyBean.getHigher_designated_personM()[i]));
                    pstmt.setInt(2, getKeyPersonId(personHierarchyBean.getDesignated_personM()[i], personHierarchyBean.getOffice_typeM()[i]));
                    pstmt.setInt(3, getHigherKeyPersonId(personHierarchyBean.getHigher_designated_personM()[i], personHierarchyBean.getOffice_typeM()[i]));
                    pstmt.setString(4, personHierarchyBean.getDescriptionM()[i]);
                    pstmt.setString(5, personHierarchyBean.getRemarkM()[i]);
                    
                    rowsAffected = pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: PersonHierarchyModel --insertRecord-- " + e);
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

    public int updateRecord(PersonHierarchyBean personHierarchyBean) {
        int rowsAffected = 0;
        String query = " UPDATE person_hierarchy "
                + " SET designation_hierarchy_id=?, designated_person_id=?, higher_designated_person_id=?, description=?, remark=? where person_hierarchy_id= ? ";

        String[] person_hierarchy_id = personHierarchyBean.getPerson_hierarchy_idM();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < person_hierarchy_id.length; i++) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setInt(1, getDesignationHierarchyId(personHierarchyBean.getOffice_typeM()[i], personHierarchyBean.getDesignated_personM()[i], personHierarchyBean.getHigher_designated_personM()[i]));
                    pstmt.setInt(2, getKeyPersonId(personHierarchyBean.getDesignated_personM()[i], personHierarchyBean.getOffice_typeM()[i]));
                    pstmt.setInt(3, getHigherKeyPersonId(personHierarchyBean.getHigher_designated_personM()[i], personHierarchyBean.getOffice_typeM()[i]));
                    pstmt.setString(4, personHierarchyBean.getDescriptionM()[i]);
                    pstmt.setString(5, personHierarchyBean.getRemarkM()[i]);
                    pstmt.setString(6, personHierarchyBean.getPerson_hierarchy_idM()[i]);
                    rowsAffected = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error: PersonHierarchyModel --updateRecord-- " + e);
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

    public int getDesignationHierarchyId(String office_type, String designated_person, String higher_designated_person) {
        int dh_id = 0;
        String query = "SELECT dh.designation_hierarchy_id FROM key_person kp1, key_person kp2, designation_hierarchy dh "//org_office_type oot, org_office of,
                + " WHERE dh.designation_id = kp1.designation_id AND dh.higher_designation_id = kp2.designation_id "//kp.org_office_id = of.org_office_id AND of.office_type_id = oot.office_type_id "
                //+ " AND IF('" + office_type + "'='', oot.office_type LIKE '%%', oot.office_type='" + office_type + "') "
                + " AND IF('" + designated_person + "'='', kp1.key_person_name LIKE '%%', kp1.key_person_name='" + designated_person + "') "
                + " AND IF('" + higher_designated_person + "'='', kp2.key_person_name LIKE '%%', kp2.key_person_name='" + higher_designated_person + "') ";
                //+ " GROUP BY kp.org_office_id";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                dh_id = rset.getInt("designation_hierarchy_id");
            }            
        } catch (Exception e) {
            System.out.println("Error:PersonHierarchyModel --getDesignationHierarchyId-- " + e);
        }
        return dh_id;
    }

    public int getKeyPersonId(String person_name, String office_type) {
        int kp_id = 0;
        String query = "SELECT kp.key_person_id FROM org_office_type oot, org_office of, key_person kp, designation_hierarchy dh "
                + " WHERE dh.designation_id = kp.designation_id AND kp.org_office_id = of.org_office_id "
                + " AND of.office_type_id = oot.office_type_id AND kp.key_person_name = '" + person_name + "' "
                + " AND IF('" + office_type + "'='', oot.office_type LIKE '%%', oot.office_type='" + office_type + "')";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                kp_id = rset.getInt("key_person_id");
            }            
        } catch (Exception e) {
            System.out.println("Error:PersonHierarchyModel --getKeyPersonId-- " + e);
        }
        return kp_id;
    }

    public int getHigherKeyPersonId(String person_name, String office_type) {
        int kp_id = 0;
        String query = "SELECT kp.key_person_id FROM org_office_type oot, org_office of, key_person kp, designation_hierarchy dh "
                + " WHERE dh.higher_designation_id = kp.designation_id AND kp.org_office_id = of.org_office_id "
                + " AND of.office_type_id = oot.office_type_id AND kp.key_person_name = '" + person_name + "' "
                + " AND IF('" + office_type + "'='', oot.office_type LIKE '%%', oot.office_type='" + office_type + "')";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                kp_id = rset.getInt("key_person_id");
            }
        } catch (Exception e) {
            System.out.println("Error:PersonHierarchyModel --getKeyPersonId-- " + e);
        }
        return kp_id;
    }

    /*public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
     *
     * SELECT kp.key_person_name FROM org_office_type oot, org_office of, key_person kp WHERE kp.org_office_id = of.org_office_id AND of.office_type_id = oot.office_type_id
 AND IF(''='', oot.office_type LIKE '%%', oot.office_type='');


SELECT kp.key_person_name FROM org_office_type oot, org_office of, key_person kp, designation_hierarchy dh WHERE kp.designation_id = dh.higher_designation_id AND dh.designation_id = kp.designation_id AND kp.org_office_id = of.org_office_id AND of.office_type_id = oot.office_type_id
 AND IF(''='', oot.office_type LIKE '%%', oot.office_type='');

SELECT dh.designation_hierarchy_id FROM org_office_type oot, org_office of, key_person kp, designation_hierarchy dh WHERE dh.designation_id = kp.designation_id AND kp.org_office_id = of.org_office_id AND of.office_type_id = oot.office_type_id
 AND IF(''='', oot.office_type LIKE '%%', oot.office_type='') GROUP BY kp.org_office_id;

SELECT kp.key_person_id FROM org_office_type oot, org_office of, key_person kp, designation_hierarchy dh WHERE dh.designation_id = kp.designation_id AND kp.org_office_id = of.org_office_id AND of.office_type_id = oot.office_type_id
 AND kp.key_person_name = 'Akash Patel'
 AND IF(''='', oot.office_type LIKE '%%', oot.office_type='')
    }*/

}
