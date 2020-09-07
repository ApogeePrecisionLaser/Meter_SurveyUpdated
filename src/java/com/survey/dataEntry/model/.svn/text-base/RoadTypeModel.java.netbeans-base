/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.survey.tableClasses.RoadTypeBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author JPSS
 */
public class RoadTypeModel {
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
            System.out.println("PoleDetailTypeModel setConnection() Error: " + e);
        }
    }
 public byte[] generateMapReport(String jrxmlFilePath, List<RoadTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
     //    HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in TrafficMapModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
public List<String> getRoadNameType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT road_name as road_name FROM road GROUP BY road_name ORDER BY road_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String road_name = rset.getString("road_name");

                if(road_name!=null){
                if (road_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(road_name);
                    count++;
                }
              }
               }
            if (count == 0) {
                list.add("No such Road name exists.");
            }
        } catch (Exception e) {
            System.out.println("getroadType ERROR inside roadModel - " + e);
        }
        return list;
    }
public List<String> getRoadUseType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT road_use FROM road_use GROUP BY road_use ORDER BY road_use ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String road_use = rset.getString("road_use");
                if (road_use.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(road_use);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Road use exists.");
            }
        } catch (Exception e) {
            System.out.println("getroadUseType ERROR inside roadTypeModel - " + e);
        }
        return list;
    }
public List<String> getRoadCategoryType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT category_name FROM road_category GROUP BY category_name ORDER BY category_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String category_name = rset.getString("category_name");
                if (category_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(category_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Road category exists.");
            }
        } catch (Exception e) {
            System.out.println("getroadcategoryType ERROR inside roadTypeModel - " + e);
        }
        return list;
    }
 public int getRoadUseId(String road_use) {
        int road_use_id = 0;
        String query = " SELECT road_use_id FROM road_use WHERE road_use = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, road_use);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            road_use_id = rset.getInt("road_use_id");
        } catch (Exception e) {
            System.out.println("road_useTypeModel getroadUseId() Error: " + e);
        }
        return road_use_id;
    }
 public int getRoadCategoryId(String category_name) {
        int category_id = 0;
        String query = " SELECT category_id FROM road_category WHERE category_name = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, category_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            category_id = rset.getInt("category_id");
        } catch (Exception e) {
            System.out.println("road_useTypeModel getCategoryId() Error: " + e);
        }
        return category_id;
    }
  private int getMaxId() {
        int road_id = 0;
        String query = "SELECT MAX(road_id) AS new_road_id FROM road ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                road_id = rset.getInt("new_road_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return road_id;
    }
 public int insertRecord(RoadTypeBean roadTypeBean) {

        String query = "INSERT INTO road (road_id, road_name, created_by, remark, start_landmark, end_landmark, category_id, road_use_id, central_light, approx_length ) "
                        + " VALUES (?,?,?,?,?,?,?,?,?,?) ";
        int road_id = getMaxId();
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, road_id + 1);
            pstmt.setString(2, roadTypeBean.getRoad_name());
            pstmt.setString(3, roadTypeBean.getCreated_by());
            pstmt.setString(4, roadTypeBean.getRemark());
            pstmt.setString(5, roadTypeBean.getStart_landmark());
            pstmt.setString(6, roadTypeBean.getEnd_landmark());
            pstmt.setInt(7, roadTypeBean.getCategory_id());
            pstmt.setInt(8, roadTypeBean.getRoad_use_id());
            pstmt.setString(9, roadTypeBean.getCentral_light());
            pstmt.setDouble(10, roadTypeBean.getApprox_length());
         //   pstmt.setString(10, roadTypeBean.getActive());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
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

 public int reviseRecord(RoadTypeBean roadTypeBean) {

        String query = "INSERT INTO road (road_id, road_name, remark, start_landmark, end_landmark, category_id, road_use_id, central_light,road_rev_no, approx_length) "
                        + " VALUES (?,?,?,?,?,?,?,?,?,?) ";
       String query2 = " UPDATE road SET active='N'"
                + " WHERE road_id = ? and road_rev_no = ? ";
        // int road_id = getMaxId();
         int road_id=  roadTypeBean.getRoad_id();
         int road_rev_no= getMaxRevId(road_id);
         int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);

            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, road_id);
            pstmt.setString(2, roadTypeBean.getRoad_name());
          //  pstmt.setString(3, roadTypeBean.getCreated_by());
            pstmt.setString(3, roadTypeBean.getRemark());
            pstmt.setString(4, roadTypeBean.getStart_landmark());
            pstmt.setString(5, roadTypeBean.getEnd_landmark());
            pstmt.setInt(6, roadTypeBean.getCategory_id());
            pstmt.setInt(7, roadTypeBean.getRoad_use_id());
            pstmt.setString(8, roadTypeBean.getCentral_light());
            pstmt.setInt(9, road_rev_no+1);
            pstmt.setDouble(10, roadTypeBean.getApprox_length());
            //   pstmt.setString(10, roadTypeBean.getActive());
            rowsAffected = pstmt.executeUpdate();

         if (rowsAffected > 0) {

                pstmt = connection.prepareStatement(query2);
                pstmt.setInt(1, roadTypeBean.getRoad_id());
                pstmt.setInt(2, roadTypeBean.getRoad_rev_no());
                rowsAffected = pstmt.executeUpdate();
            } else {
                connection.rollback();
            }

        if (rowsAffected > 0) {
                    connection.commit();
                    //  rowsAffected++;
                } else {
                    connection.rollback();
                }

        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(PoleDetailTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rowsAffected > 0) {
            message = "Record Revised successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

 public int updateRecord(RoadTypeBean roadTypeBean) {
      //  String query = "UPDATE road SET road_name=?, created_by=?, remark=?, start_landmark=?, end_landmark=?, category_id=?, road_use_id=? , central_light=?, active=?  WHERE road_id=? ";
      String query1 = "Update  road  set road_name=?, remark=?, start_landmark=?, end_landmark=?, category_id=?, road_use_id=?, central_light=?, approx_length=? "
                        + " WHERE road_id = ? and road_rev_no = ?";
    /*  String query2 = " UPDATE road SET active='Revised'"
                + " WHERE road_id = ? and road_rev_no = ? "; */

      int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
      
            pstmt.setString(1, roadTypeBean.getRoad_name());
          //  pstmt.setString(2, roadTypeBean.getCreated_by());
            pstmt.setString(2, roadTypeBean.getRemark());
            pstmt.setString(3, roadTypeBean.getStart_landmark());
            pstmt.setString(4, roadTypeBean.getEnd_landmark());
            pstmt.setInt(5, roadTypeBean.getCategory_id());
            pstmt.setInt(6, roadTypeBean.getRoad_use_id());
            pstmt.setString(7, roadTypeBean.getCentral_light());
            pstmt.setDouble(8, roadTypeBean.getApprox_length());
            pstmt.setInt(9, roadTypeBean.getRoad_id());
            pstmt.setInt(10, roadTypeBean.getRoad_rev_no());
            rowsAffected = pstmt.executeUpdate();

         /*   if (rowsAffected > 0) {

                pstmt = connection.prepareStatement(query2);
                pstmt.setInt(1, roadTypeBean.getRoad_id());
                pstmt.setInt(2, roadTypeBean.getRoad_rev_no());
                rowsAffected = pstmt.executeUpdate();
            } */
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
  private int getMaxRevId(int road_id) {
        int rod_rev_id = 0;
        String query = "SELECT road_rev_no  FROM road where road_id="+road_id+" and active='Y'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                rod_rev_id = rset.getInt("road_rev_no");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rod_rev_id;
    }
 public int getNoOfRows(String searchRoadNameType, String searchRoadUseType, String searchRoadCatType ) {
        String query = " SELECT Count(*) "
                + " FROM road r, road_use ru, road_category rct "
                + " WHERE r.road_use_id = ru.road_use_id "
                + " AND r.category_id = rct.category_id  and r.active='Y'"
                + " AND IF('" + searchRoadNameType + "' = '', r.road_name LIKE '%%', r.road_name =?) "
                + " AND IF('" + searchRoadUseType  + "' = '', ru.road_use LIKE '%%', ru.road_use =?) "
                + " AND IF('" + searchRoadCatType  + "' = '', rct.category_name LIKE '%%', rct.category_name =?) "
                + " ORDER BY r.road_name ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchRoadNameType);
            stmt.setString(2, searchRoadUseType);
            stmt.setString(3, searchRoadCatType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows Road model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }
 public List<RoadTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchRoadNameType, String searchRoadUseType, String searchRoadCatType) {
        List<RoadTypeBean> list = new ArrayList<RoadTypeBean>();

       String query = " SELECT r.road_id, r.road_name,DATE_FORMAT(r.created_date,'%d-%m-%Y') AS created_date, r.created_by, r.remark,r.start_landmark,r.end_landmark, rct.category_name,ru.road_use,r.central_light,r.active,r.road_rev_no,r.approx_length  "
                + " FROM road r, road_use ru, road_category rct "
                + " WHERE r.road_use_id = ru.road_use_id  "
                + " AND r.category_id = rct.category_id  and r.active='Y'"

                + " AND IF('" + searchRoadNameType + "' = '', r.road_name LIKE '%%', r.road_name =?) "
                + " AND IF('" + searchRoadUseType  + "' = '', ru.road_use LIKE '%%', ru.road_use =?) "
                + " AND IF('" + searchRoadCatType  + "' = '', rct.category_name LIKE '%%', rct.category_name =?) "
                + " ORDER BY r.road_name "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
       try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchRoadNameType);
            pstmt.setString(2, searchRoadUseType);
            pstmt.setString(3, searchRoadCatType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                RoadTypeBean roadType = new RoadTypeBean();
                roadType.setRoad_id(rset.getInt("road_id"));
                roadType.setRoad_rev_no(rset.getInt("road_rev_no"));
                roadType.setRoad_name(rset.getString("road_name"));
                roadType.setCreated_date(rset.getString("created_date"));
                roadType.setCreated_by(rset.getString("created_by"));
                roadType.setRemark(rset.getString("remark"));
                roadType.setStart_landmark(rset.getString("start_landmark"));
                roadType.setEnd_landmark(rset.getString("end_landmark"));
                roadType.setCategory_name(rset.getString("category_name"));
                roadType.setRoad_use(rset.getString("road_use"));
                roadType.setCentral_light(rset.getString("central_light"));
                roadType.setActive(rset.getString("active"));
                roadType.setApprox_length(rset.getDouble("approx_length"));
                list.add(roadType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
 public List<RoadTypeBean> showAllData(String searchRoadNameType, String searchRoadUseType, String searchRoadCatType) {
        List<RoadTypeBean> listAll = new ArrayList<RoadTypeBean>();

       String query = " SELECT r.road_id,r.road_rev_no, r.road_name,DATE_FORMAT(r.created_date,'%d-%m-%Y') AS created_date, r.created_by, r.remark,r.start_landmark,r.end_landmark, rct.category_name,ru.road_use,r.central_light,r.active,r.approx_length "
                + " FROM road r, road_use ru, road_category rct "
                + " WHERE r.road_use_id = ru.road_use_id  "
                + " AND r.category_id = rct.category_id   and r.active='Y'"
                + " AND IF('" + searchRoadNameType + "' = '', r.road_name LIKE '%%', r.road_name =?) "
                + " AND IF('" + searchRoadUseType  + "' = '', ru.road_use LIKE '%%', ru.road_use =?) "
                + " AND IF('" + searchRoadCatType  + "' = '', rct.category_name LIKE '%%', rct.category_name =?) "
                + " ORDER BY r.road_name ";
               // + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
       try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchRoadNameType);
            pstmt.setString(2, searchRoadUseType);
            pstmt.setString(3, searchRoadCatType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                RoadTypeBean roadType = new RoadTypeBean();
                roadType.setRoad_id(rset.getInt("road_id"));
                roadType.setRoad_rev_no(rset.getInt("road_rev_no"));
                roadType.setRoad_name(rset.getString("road_name"));
                roadType.setCreated_date(rset.getString("created_date"));
                roadType.setCreated_by(rset.getString("created_by"));
                roadType.setRemark(rset.getString("remark"));
                roadType.setStart_landmark(rset.getString("start_landmark"));
                roadType.setEnd_landmark(rset.getString("end_landmark"));
                roadType.setCategory_name(rset.getString("category_name"));
                roadType.setRoad_use(rset.getString("road_use"));
                roadType.setCentral_light(rset.getString("central_light"));
                roadType.setActive(rset.getString("active"));
                roadType.setApprox_length(rset.getDouble("approx_length"));
                listAll.add(roadType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }
    public int cancelRecord(int road_id, int road_rev_no) {
        String query = "UPDATE road SET active='N' WHERE road_id = "+ road_id
                + " and road_rev_no = "+ road_rev_no ;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record cancelled successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Error Record cannot be deleted.....";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }


/* public int deleteRecord(int road_type_id) {
        String query = "DELETE FROM road WHERE road_id=" + road_type_id;
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
    }*/
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
