/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.survey.tableClasses.EnclosureTypeBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Navitus2
 */
public class EnclosureModel {

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
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("EnclosureTypeModel setConnection() Error: " + e);
        }
    }

     public int getNoOfRows(String searchenclosure){
          int noOfRows = 0;
           String query="SELECT count(*) FROM enclosure_type "
                    +" WHERE IF('" + searchenclosure + "' = '', enclosure_type LIKE '%%',enclosure_type =?) "
                + " ORDER BY enclosure_type ";

        try {
             PreparedStatement pstmt = connection.prepareStatement(query);
             pstmt.setString(1, searchenclosure);

            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("EnclosureTypeModel getNoOfRows() Error: " + e);
        }

          return noOfRows ;
     }

     public List<String> getEnclosureType(String q){
         List<String> list = new ArrayList<String>();
          String query="SELECT enclosure_type FROM enclosure_type "
            + " GROUP BY enclosure_type";
        try {
             PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String enclosure_type = rset.getString("enclosure_type");
                if (enclosure_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(enclosure_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getEnclosureType ERROR inside EnclosureTypeModel- " + e);
        }
         return list;
     }

     public int insertRecord(EnclosureTypeBean enclosuretype){
         int rowsAffected=0;
           String query = "INSERT INTO enclosure_type(enclosure_type, remark) "
                + "VALUES(?, ?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,enclosuretype.getEnclosure_type());
            pstmt.setString(2,enclosuretype.getRemark());

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("EnclosureTypeModel insertRecord() Error: " + e);
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


      public int updateRecord(EnclosureTypeBean enclosuretype){
          String query = " UPDATE enclosure_type SET  enclosure_type = ?, remark=? "
                 + "WHERE enclosure_type_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            pstmt.setString(1, enclosuretype.getEnclosure_type());
            pstmt.setString(2,enclosuretype.getRemark());
            pstmt.setInt(3,enclosuretype.getEnclosure_type_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("EnclosureTypeModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error......";
            msgBgColor = COLOR_ERROR;
        }
          return rowsAffected;
      }


      public List<EnclosureTypeBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchenclosure){
          List<EnclosureTypeBean> list=new ArrayList<EnclosureTypeBean>();
             String query="select * from enclosure_type "
               +" WHERE IF('" + searchenclosure + "' = '', enclosure_type LIKE '%%', enclosure_type =?) "+
                  " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
    try{

       PreparedStatement ps = connection.prepareStatement(query);

       ps.setString(1, searchenclosure);
         ResultSet rs =ps.executeQuery();
         while(rs.next()){
          EnclosureTypeBean enclosuretype=new EnclosureTypeBean();
          enclosuretype.setEnclosure_type_id(Integer.parseInt(rs.getString("enclosure_type_id")));
          enclosuretype.setEnclosure_type(rs.getString("enclosure_type"));
          enclosuretype.setRemark(rs.getString("remark"));

             list.add(enclosuretype);
          }
          }
            catch(Exception e2)
            {
                System.out.println("error in EnclosureTypeModel show data: " + e2);
            }
          return list;
      }

     public int deleteRecord(int enclosure_type_id){
        String query = " DELETE FROM enclosure_type WHERE enclosure_type_id = " + enclosure_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("EnclosureTypeModel deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
     }


     public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection EnclosureModel:" + e);
        }
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }




}


