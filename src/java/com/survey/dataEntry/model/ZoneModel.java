/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;
import com.survey.tableClasses.ZoneTypeBean;
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
public class ZoneModel {
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
public byte[] generateMapReport(String jrxmlFilePath, List<ZoneTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
    //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in FuseTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
 public int insertRecord(ZoneTypeBean zoneTypeBean) {

        //String query = "INSERT INTO zone_m (zone_m, division_id_m, description) VALUES (?,?,?) ";
         String query = "INSERT INTO zone_m (zone_m, description) VALUES (?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, zoneTypeBean.getZone_m());
            //pstmt.setInt(2, zoneTypeBean.getDivision_id_m());
            pstmt.setString(2, zoneTypeBean.getDescription());
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

  public int updateRecord(ZoneTypeBean zoneTypeBean) {
        //String query = "UPDATE zone_m SET  zone_m=?, division_id_m=?, description=? WHERE zone_id_m=? ";
          String query = "UPDATE zone_m SET  zone_m=?, description=? WHERE zone_id_m=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, zoneTypeBean.getZone_m());
            //pstmt.setInt(2, zoneTypeBean.getDivision_id_m());
            pstmt.setString(2, zoneTypeBean.getDescription());
            pstmt.setInt(3, zoneTypeBean.getZone_id_m());
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
 public int getDivisonId(String division_name) {
        String query = " select division_id_m from division_m where division_name_m='"+division_name+"'";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
         noOfRows=rs.getInt("division_id_m");
         }
        } catch (Exception e) {
            System.out.println("Error inside getDivisonId division type model" + e);
        }

        return noOfRows;
    }
  public List<String> getZoneType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT zone_id_m, zone_m FROM zone_m GROUP BY zone_m ORDER BY zone_m ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_m = rset.getString("zone_m");
                if (zone_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_m);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside FuseTypeModel - " + e);
        }
        return list;
    }
public List<String> getDivisionType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT division_name_m FROM division_m GROUP BY division_name_m ORDER BY division_name_m ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_m = rset.getString("division_name_m");
                if (zone_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_m);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside FuseTypeModel - " + e);
        }
        return list;
    }

   public int getNoOfRows(String searchZoneType) {
//        String query = " SELECT Count(*) "
//                 + " FROM zone_m as z ,division_m  as d where z.division_id_m=d.division_id_m"
//                + " AND IF('" + searchZoneType + "' = '',zone_m  LIKE '%%',zone_m =?) "
//                + " ORDER BY zone_m ";
       String query = " SELECT Count(*) "
                 + " FROM zone_m as z "
                + " where if('" + searchZoneType + "' = '',zone_m  LIKE '%%',zone_m =?) "
                + " ORDER BY zone_m ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchZoneType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows fuse type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<ZoneTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchZoneType) {
        List<ZoneTypeBean> list = new ArrayList<ZoneTypeBean>();

//       String query = " SELECT z.zone_id_m, z.zone_m, z.description,d.division_name_m "
//                + " FROM zone_m as z ,division_m  as d where z.division_id_m=d.division_id_m"
//                + " AND IF('" + searchZoneType + "' = '',zone_m  LIKE '%%',zone_m =?) "
//                + " ORDER BY zone_m "
//                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        String query = " SELECT z.zone_id_m, z.zone_m, z.description "
                + " FROM zone_m as z "
                + " where if('" + searchZoneType + "' = '',zone_m  LIKE '%%',zone_m =?) "
                + " ORDER BY zone_m "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;


        try {
            System.out.println(query);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchZoneType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ZoneTypeBean zoneType = new ZoneTypeBean();
                zoneType.setZone_id_m(rset.getInt("zone_id_m"));
                zoneType.setZone_m(rset.getString("zone_m"));

                zoneType.setDescription(rset.getString("description"));
                //zoneType.setDivision_name_n(rset.getString("division_name_m"));
                list.add(zoneType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
   public List<ZoneTypeBean> showAllData(String searchZoneType) {
        List<ZoneTypeBean> listAll = new ArrayList<ZoneTypeBean>();

//       String query = " SELECT z.zone_id_m, z.zone_m, z.description,d.division_name_m "
//                + " FROM zone_m as z ,division_m  as d where z.division_id_m=d.division_id_m"
//                + " AND IF('" + searchZoneType + "' = '',zone_m  LIKE '%%',zone_m =?) "
//                + " ORDER BY zone_m ";
//                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        String query = " SELECT z.zone_id_m, z.zone_m, z.description "
                + " FROM zone_m as z "
                + " Where IF('" + searchZoneType + "' = '',zone_m  LIKE '%%',zone_m =?) "
                + " ORDER BY zone_m ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchZoneType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ZoneTypeBean zoneType = new ZoneTypeBean();
                zoneType.setZone_id_m(rset.getInt("zone_id_m"));
                zoneType.setZone_m(rset.getString("zone_m"));
                zoneType.setDescription(rset.getString("description"));
                //zoneType.setDivision_name_n(rset.getString("division_name_m"));
                listAll.add(zoneType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }

  public int deleteRecord(int zone_id_m) {
        String query = "DELETE FROM zone_m WHERE zone_id_m=" + zone_id_m;
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
