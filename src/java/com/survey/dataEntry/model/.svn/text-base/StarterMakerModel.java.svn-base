/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;


import com.survey.tableClasses.StarterMakerBean;
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
public class StarterMakerModel {
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
            System.out.println("StarterMakerModel setConnection() Error: " + e);
        }
    }

     public int getNoOfRows(String searchstartermake){
          int noOfRows = 0;
            String query="SELECT count(*) FROM starter_make "
                    +" WHERE IF('" + searchstartermake + "' = '', starter_make LIKE '%%',starter_make =?) "
                + " ORDER BY starter_make ";

        try {
             PreparedStatement pstmt = connection.prepareStatement(query);
             pstmt.setString(1, searchstartermake);

            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("StarterMakerModel getNoOfRows() Error: " + e);
        }

          return noOfRows ;
     }

     public List<String> getStarterMake(String q){
         List<String> list = new ArrayList<String>();

           String query="SELECT starter_make FROM starter_make "
            + " GROUP BY starter_make";
        try {
             PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String starter_make = rset.getString("starter_make");
                if (starter_make.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(starter_make);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getStarterMake ERROR inside StarterMakerModel- " + e);
        }

         return list;

     }

     public int insertRecord(StarterMakerBean startermake){
         int rowsAffected=0;

            String query = "INSERT INTO starter_make(starter_make, remark) "
                + "VALUES(?, ?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,startermake.getStarter_make());
            pstmt.setString(2,startermake.getRemark());

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("StarterMakeModel insertRecord() Error: " + e);
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


      public int updateRecord(StarterMakerBean startermaker){
          String query = " UPDATE starter_make SET  starter_make = ?, remark=? "
                 + "WHERE starter_make_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            pstmt.setString(1, startermaker.getStarter_make());
            pstmt.setString(2,startermaker.getRemark());
            pstmt.setInt(3,startermaker.getStarter_make_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("StarterMakerModel updateRecord() Error: " + e);
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


      public List<StarterMakerBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchstartermake){
          List<StarterMakerBean> list=new ArrayList<StarterMakerBean>();
           String query="select * from starter_make "
               +" WHERE IF('" + searchstartermake + "' = '', starter_make LIKE '%%', starter_make =?) "+
                  " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
    try{

       PreparedStatement ps = connection.prepareStatement(query);

       ps.setString(1, searchstartermake);
         ResultSet rs =ps.executeQuery();
         while(rs.next()){
          StarterMakerBean starter=new StarterMakerBean();
          starter.setStarter_make_id(Integer.parseInt(rs.getString("starter_make_id")));
          starter.setStarter_make(rs.getString("starter_make"));
          starter.setRemark(rs.getString("remark"));
             list.add(starter);
          }
          }catch(Exception e2){
                System.out.println("error in StarterMakerModel show data: " + e2);
            }
          return list;
      }

     public int deleteRecord(int starter_make_id){
      String query = " DELETE FROM starter_make WHERE starter_make_id = " + starter_make_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("StarterMakeModel deleteRecord() Error: " + e);
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
            System.out.println("Error inside closeConnection StarterMakerModel:" + e);
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

