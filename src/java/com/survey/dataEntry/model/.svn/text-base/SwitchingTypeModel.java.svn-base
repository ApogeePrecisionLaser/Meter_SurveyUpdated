/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.survey.tableClasses.SwitchingTypeBean;
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
public class SwitchingTypeModel {


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
            System.out.println("SwitchingTypeModel setConnection() Error: " + e);
        }
    }

     public int getNoOfRows(String searchswitchtype){
          int noOfRows = 0;
          String query="SELECT count(*) FROM switch_type "
                    +" WHERE IF('" + searchswitchtype + "' = '', switch_type LIKE '%%',switch_type =?) "
                + " ORDER BY switch_type ";

        try {
             PreparedStatement pstmt = connection.prepareStatement(query);
             pstmt.setString(1, searchswitchtype);

            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("SwitchingTypeModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
     }

     public List<String> getSwitchType(String q){
         List<String> list = new ArrayList<String>();

           String query="SELECT switch_type FROM switch_type "
            + " GROUP BY switch_type";
        try {
             PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String switch_type = rset.getString("switch_type");
                if (switch_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(switch_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getSwitchingType ERROR inside SwitchingTypeModel- " + e);
        }

         return list;

     }

     public int insertRecord(SwitchingTypeBean switchingtype){
         int rowsAffected=0;

            String query = "INSERT INTO switch_type(switch_type, remark) "
                + "VALUES(?, ?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,switchingtype.getSwitch_type());
            pstmt.setString(2,switchingtype.getRemark());

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("SwitchingTypeModel insertRecord() Error: " + e);
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


      public int updateRecord(SwitchingTypeBean switchingtype){
         String query = " UPDATE switch_type SET  switch_type = ?, remark=? "
                 + "WHERE switch_type_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            pstmt.setString(1, switchingtype.getSwitch_type());
            pstmt.setString(2,switchingtype.getRemark());
            pstmt.setInt(3,switchingtype.getSwitch_type_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("SwitchingTypeModel updateRecord() Error: " + e);
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


      public List<SwitchingTypeBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchswitchtype){
          List<SwitchingTypeBean> list=new ArrayList<SwitchingTypeBean>();
             String query="select * from switch_type "
               +" WHERE IF('" + searchswitchtype + "' = '', switch_type LIKE '%%', switch_type =?) "+
                  " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
    try{

       PreparedStatement ps = connection.prepareStatement(query);

       ps.setString(1, searchswitchtype);
         ResultSet rs =ps.executeQuery();
         while(rs.next()){
          SwitchingTypeBean switching=new SwitchingTypeBean();
          switching.setSwitch_type_id(Integer.parseInt(rs.getString("switch_type_id")));
          switching.setSwitch_type(rs.getString("switch_type"));
          switching.setRemark(rs.getString("remark"));

             list.add(switching);
          }
          }
            catch(Exception e2)
            {
                System.out.println("error in SwitchingTypeModel show data: " + e2);
            }

          return list;
      }

     public int deleteRecord(int switch_type_id){
       String query = " DELETE FROM switch_type WHERE switch_type_id = " + switch_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("SwitchingTypeModel deleteRecord() Error: " + e);
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
            System.out.println("Error inside closeConnection SwitchingTypeModel:" + e);
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




