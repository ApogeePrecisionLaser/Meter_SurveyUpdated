///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.survey.dataEntry.model;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
///**
// *
// * @author Administrator
// */
//public class PopulateTableModel {
//    
//    
// private Connection connection;
//    private String driverClass;
//    private String connectionString;
//    private String db_username;
//    private String db_password;
//    private String message;
//    private String msgBgColor;
//    private final String COLOR_OK = "yellow";
//    private final String COLOR_ERROR = "red";
// public void setConnection() {
//        try {
//            Class.forName(driverClass);
//           // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
//            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
//        } catch (Exception e) {
//            System.out.println("TrafficTypeModel setConnection() Error: " + e);
//        }
//    }
// 
//  public int insertRecord() {
//
////        String query = " ";
//        int rowsAffected = 0;
//        try {
//            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, mountingTypeBean.getMounting_type());
//            pstmt.setString(2, mountingTypeBean.getActive());
//            pstmt.setString(3, mountingTypeBean.getCreated_by());
//            pstmt.setString(4, mountingTypeBean.getRemark());
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("Error while inserting record...." + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record saved successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot save the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//
//    }
// 
//  
//   public void closeConnection() {
//        try {
//            connection.close();
//        } catch (Exception e) {
//            System.out.println("Error inside closeConnection MountingTypeModel:" + e);
//        }
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public String getMsgBgColor() {
//        return msgBgColor;
//    }
//
//    public Connection getConnection() {
//        return connection;
//    }
//
//    public void setConnection(Connection connection) {
//        this.connection = connection;
//    }
//
//    public String getConnectionString() {
//        return connectionString;
//    }
//
//    public void setConnectionString(String connectionString) {
//        this.connectionString = connectionString;
//    }
//
//    public String getDb_password() {
//        return db_password;
//    }
//
//    public void setDb_password(String db_password) {
//        this.db_password = db_password;
//    }
//
//    public String getDb_username() {
//        return db_username;
//    }
//
//    public void setDb_username(String db_username) {
//        this.db_username = db_username;
//    }
//
//    public String getDriverClass() {
//        return driverClass;
//    }
//
//    public void setDriverClass(String driverClass) {
//        this.driverClass = driverClass;
//    }
//}
//
