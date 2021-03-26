/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.survey.tableClasses.LightTypeBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author JPSS
 */
public class PoleLightTypeModel {
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
            System.out.println("LightTypeModel setConnection() Error: " + e);
        }
    }
public byte[] generateMapReport(String jrxmlFilePath, List<LightTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
       //  HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in LightTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
  public List<String> getSourceType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT source_type FROM light_source_type GROUP BY source_type ORDER BY source_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String source_type = rset.getString("source_type");
                if (source_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(source_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Source Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getSourceType ERROR inside LigtTypeModel - " + e);
        }
        return list;
    }

  public List<String> getWattageType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT wattage_value FROM wattage GROUP BY wattage_value ORDER BY wattage_value ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String wattage_value = rset.getString("wattage_value");
                if (wattage_value.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(wattage_value);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such wattage Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getWattageType ERROR inside LigtTypeModel - " + e);
        }
        return list;
    }
        public int getWattageId(String wattage_value) {
        int wattage_id = 0;
        String query = " SELECT wattage_id FROM wattage WHERE wattage_value = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, wattage_value);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            wattage_id = rset.getInt("wattage_id");
        } catch (Exception e) {
            System.out.println("lightTypeModel getWattageId() Error: " + e);
        }
        return wattage_id;
    }
        public int getChildId(String wattage_value) {
        int wattage_id = 0;
        String query = " SELECT pole_light_type_id FROM pole_light_type WHERE light_name = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, wattage_value);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            wattage_id = rset.getInt("pole_light_type_id");
        } catch (Exception e) {
            System.out.println("lightTypeModel getWattageId() Error: " + e);
        }
        return wattage_id;
    }

    public int getSourceId(String source_type) {
        int source_type_id = 0;
        String query = " SELECT source_type_id FROM light_source_type WHERE source_type = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, source_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            source_type_id = rset.getInt("source_type_id");
        } catch (Exception e) {
            System.out.println("lightTypeModel getSourceId() Error: " + e);
        }
        return source_type_id;
    }

public int insertRecord(LightTypeBean lightTypeBean) {

        String query = "INSERT INTO pole_light_type (created_by, remark, wattage_id,light_name,isParent,is_child,child_id) VALUES (?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        int count_ward_no=0;
        try {

        //    count_ward_no=countSourceWatage(lightTypeBean.getWattage_id(),lightTypeBean.getSource_id()) ;

          
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, lightTypeBean.getCreated_by());
            pstmt.setString(2, lightTypeBean.getRemark());
            pstmt.setInt(3, lightTypeBean.getWattage_id());
            pstmt.setString(4, lightTypeBean.getSource_type());
            pstmt.setString(5, lightTypeBean.getIsParent());
            pstmt.setString(6, lightTypeBean.getIschild());
            pstmt.setInt(7, lightTypeBean.getChildid());
            rowsAffected = pstmt.executeUpdate();
         
            } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0 && count_ward_no==0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else if (count_ward_no > 0)
        {
             message = "Soucre Type "+lightTypeBean.getSource_type()+",Wattage Value "+lightTypeBean.getWattage_value()+ " already  exists in light type";
             msgBgColor = COLOR_ERROR;

        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
}
   public int countSourceWatage(int wattage_id,int source_id) {
        String query = " select  count(*) from light_type  where wattage_id=? and  source_id=? ";

        int noOfRows = 0;
        try {
           PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, wattage_id);
            stmt.setInt(2, source_id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside countWardNo Ward model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

public int updateRecord(LightTypeBean lightTypeBean) {
        String query = "UPDATE light_type SET created_by=?, remark=?, wattage_id=?, source_id=? WHERE light_type_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, lightTypeBean.getCreated_by());
            pstmt.setString(2, lightTypeBean.getRemark());
            pstmt.setInt(3, lightTypeBean.getWattage_id());
            pstmt.setInt(4, lightTypeBean.getSource_id());
            pstmt.setInt(5, lightTypeBean.getLight_type_id());
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
  public int getNoOfRows(String searchSourceType, String searchWattageType) {
        String query = " SELECT Count(*) "
                + " FROM light_type lt, wattage w, light_source_type lst "
                + " WHERE lt.wattage_id=w.wattage_id "
                + " AND lt.source_id=lst.source_type_id "
                + " AND IF('" + searchSourceType + "' = '', lst.source_type LIKE '%%', lst.source_type =?) "
                + " AND IF('" + searchWattageType + "' = '', w.wattage_value LIKE '%%', w.wattage_value =?) "
                + " ORDER BY lst.source_type ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchSourceType);
            stmt.setString(2, searchWattageType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows Light model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }
 public List<LightTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchSourceType, String searchWattageType) {
        List<LightTypeBean> list = new ArrayList<LightTypeBean>();

//       String query = " SELECT lt.light_type_id, DATE_FORMAT(lt.created_date,'%d-%m-%Y') AS created_date, lt.created_by, lt.remark, w.wattage_value, lst.source_type "
//                + " FROM light_type lt, wattage w, light_source_type lst "
//                + " WHERE lt.wattage_id=w.wattage_id  "
//                + " AND lt.source_id=lst.source_type_id "
//                + " AND IF('" + searchSourceType + "' = '', lst.source_type LIKE '%%', lst.source_type =? ) "
//                + " AND IF('" + searchWattageType + "' = '', w.wattage_value LIKE '%%', w.wattage_value =?) "
//                + " ORDER BY lst.source_type "
//                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
String query="SELECT plt.pole_light_type_id, plt.light_name, isParent, is_child,  plt.remark, plt.active,  \n" +
"plt.created_date,w.wattage_value FROM pole_light_type as plt ,wattage as w where plt.wattage_id=w.wattage_id "
         + " AND IF('" + searchSourceType + "' = '', plt.light_name LIKE '%%', plt.light_name =? ) "
      + " AND IF('" + searchWattageType + "' = '', w.wattage_value LIKE '%%', w.wattage_value =?) "
           + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
       try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchSourceType);
            pstmt.setString(2, searchWattageType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                LightTypeBean sourceType = new LightTypeBean();
                sourceType.setLight_type_id(rset.getInt("pole_light_type_id"));
                sourceType.setSource_type(rset.getString("light_name"));
                sourceType.setWattage_value(rset.getString("wattage_value"));
             
                sourceType.setCreated_date(rset.getString("created_date"));
                sourceType.setRemark(rset.getString("remark"));
                list.add(sourceType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

 public List<LightTypeBean> showAllData(String searchSourceType, String searchWattageType) {
        List<LightTypeBean> listAll = new ArrayList<LightTypeBean>();

       String query = " SELECT lt.light_type_id, DATE_FORMAT(lt.created_date,'%d-%m-%Y') AS created_date, lt.created_by, lt.remark, w.wattage_value, lst.source_type "
                + " FROM light_type lt, wattage w, light_source_type lst "
                + " WHERE lt.wattage_id=w.wattage_id  "
                + " AND lt.source_id=lst.source_type_id "
                + " AND IF('" + searchSourceType + "' = '', lst.source_type LIKE '%%', lst.source_type =? ) "
                + " AND IF('" + searchWattageType + "' = '', w.wattage_value LIKE '%%', w.wattage_value =?) "
                + " ORDER BY lst.source_type ";
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

       try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchSourceType);
            pstmt.setString(2, searchWattageType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                LightTypeBean sourceType = new LightTypeBean();
                sourceType.setLight_type_id(rset.getInt("light_type_id"));
                sourceType.setSource_type(rset.getString("source_type"));
                sourceType.setWattage_value(rset.getString("wattage_value"));
                sourceType.setCreated_by(rset.getString("created_by"));
                sourceType.setCreated_date(rset.getString("created_date"));
                sourceType.setRemark(rset.getString("remark"));
                listAll.add(sourceType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }
 public int deleteRecord(int light_type_id) {
        String query = "DELETE FROM light_type WHERE light_type_id=" + light_type_id;
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
            System.out.println("Error inside closeConnection TrafficTypeModel:" + e);
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
