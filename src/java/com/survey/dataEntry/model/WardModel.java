package com.survey.dataEntry.model;


import com.survey.tableClasses.WardTypeBean;
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
 * @author Administrator
 */
public class WardModel {

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
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<WardTypeBean> listAll) {
        byte[] reportInbytes = null;
        Connection c;
        //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in WardModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public int insertRecord(WardTypeBean wardTypeBean) {

        String query = "INSERT INTO ward_m (ward_no_m, remark, zone_id_m,ward_name) VALUES (?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, wardTypeBean.getWard_no());
            pstmt.setString(2, wardTypeBean.getRemark());
            pstmt.setInt(3, wardTypeBean.getZone_id_m());
            pstmt.setString(4, wardTypeBean.getWard_name());
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

    public int updateRecord(WardTypeBean wardTypeBean) {
        String query = "UPDATE ward_m SET  ward_no_m=?, remark=?, zone_id_m=?,ward_name=? WHERE ward_id_m=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           pstmt.setString(1, wardTypeBean.getWard_no());
            pstmt.setString(2, wardTypeBean.getRemark());
            pstmt.setInt(3, wardTypeBean.getZone_id_m());

            pstmt.setString(4, wardTypeBean.getWard_name());
            pstmt.setInt(5,wardTypeBean.getWard_id());
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

    public List<String> getWardType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ward_id_m, ward_no_m FROM ward_m GROUP BY ward_no_m ORDER BY ward_no_m ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ward_type = rset.getString("ward_no_m");
                if (ward_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getWardType ERROR inside WardModel - " + e);
        }
        return list;
    }
      public int getZoneId(String zone_m) {
        String query = " select zone_id_m from zone_m where zone_m='"+zone_m+"'";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
         noOfRows=rs.getInt("zone_id_m");
         }
        } catch (Exception e) {
            System.out.println("Error inside getCircleId division type model" + e);
        }

        return noOfRows;
    }
 public List<String> getZoneName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT zone_m FROM zone_m GROUP BY zone_m ORDER BY zone_m ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_name_m = rset.getString("zone_m");
                if (zone_name_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_name_m);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside WardModel - " + e);
        }
        return list;
    }
       public List<String> getSearchZoneName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select zone_m "
                        +" from zone_m zm,ward_m wm "
                        +" where wm.zone_id_m=zm.zone_id_m "
                        +" and wm.active='Active' group by zm.zone_m ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_name_m = rset.getString("zone_m");
                if (zone_name_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_name_m);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside WardModel - " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchWardType,String searchZoneName) {
        String query = " SELECT Count(*) "
                + " FROM ward_m ,zone_m z "
                + " WHERE IF('" + searchWardType + "' = '', ward_no_m LIKE '%%',ward_no_m =?) "
                + " AND IF('" + searchZoneName + "' = '',z.zone_m  LIKE '%%',z.zone_m =?) "
                + " ORDER BY ward_no_m ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchWardType);
            stmt.setString(2, searchZoneName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows fuse type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<WardTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchWardType,String searchZoneName) {
        List<WardTypeBean> list = new ArrayList<WardTypeBean>();

        String query = " SELECT ward_id_m, ward_no_m, remark, ward_rev_no_m,z.zone_m,ward_name "
                + " FROM ward_m as w,zone_m as z  where active='Active' AND w.zone_id_m=z.zone_id_m "
                + " AND IF('" + searchWardType + "' = '',ward_no_m  LIKE '%%',ward_no_m =?) "
                + " AND IF('" + searchZoneName + "' = '',z.zone_m  LIKE '%%',z.zone_m =?) "
                + " ORDER BY ward_no_m "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchWardType);
            pstmt.setString(2, searchZoneName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WardTypeBean wardType = new WardTypeBean();
                wardType.setWard_id(rset.getInt("ward_id_m"));
                wardType.setWard_no(rset.getString("ward_no_m"));

                wardType.setRemark(rset.getString("remark"));
               // wardType.setWard_rev_no(rset.getInt("ward_rev_no_m"));
                wardType.setZone_m(rset.getString("zone_m"));
                wardType.setWard_name(rset.getString("ward_name"));
                list.add(wardType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<WardTypeBean> showAllData(String searchWardType) {
        List<WardTypeBean> listAll = new ArrayList<WardTypeBean>();

        String query = " SELECT ward_id_m, ward_no_m, remark, ward_rev_no_m,z.zone_m "
                + " FROM ward_m as w,zone_m as z  where active='Active' AND w.zone_id_m=z.zone_id_m "
                + " AND IF('" + searchWardType + "' = '',ward_no_m  LIKE '%%',ward_no_m =?) "
                + " ORDER BY ward_no_m ";
        //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchWardType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WardTypeBean wardType = new WardTypeBean();
                wardType.setWard_id(rset.getInt("ward_id_m"));
                wardType.setWard_no(rset.getString("ward_no_m"));

                wardType.setRemark(rset.getString("remark"));
                wardType.setWard_rev_no(rset.getInt("ward_rev_no_m"));
                wardType.setZone_m(rset.getString("zone_m"));
                listAll.add(wardType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }

    public int deleteRecord(int ward_id_m) {
        String query = "DELETE FROM ward_m WHERE ward_id_m=" + ward_id_m;
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
